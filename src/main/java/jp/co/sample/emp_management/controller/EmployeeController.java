package jp.co.sample.emp_management.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.form.InsertEmployeeForm;
import jp.co.sample.emp_management.form.UpdateEmployeeForm;
import jp.co.sample.emp_management.service.EmployeeService;

/**
 * 従業員情報を操作するコントローラー.
 * 
 * @author igamasayuki
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	/**
	 * 使用するフォームオブジェクトをリクエストスコープに格納する.
	 * 
	 * @return フォーム
	 */
	@ModelAttribute
	public UpdateEmployeeForm setUpForm() {
		return new UpdateEmployeeForm();
	}

	@ModelAttribute
	public InsertEmployeeForm setUpInsertEmployeeForm() {
		return new InsertEmployeeForm();
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員一覧を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員一覧画面を出力します.
	 * 
	 * @param model モデル
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/showList")
	public String showList(Model model) {
		List<Employee> employeeList = employeeService.showList();
		model.addAttribute("employeeList", employeeList);
		
		return "employee/list";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細画面を出力します.
	 * 
	 * @param id    リクエストパラメータで送られてくる従業員ID
	 * @param model モデル
	 * @return 従業員詳細画面
	 */
	@RequestMapping("/showDetail")
	public String showDetail(String id, Model model) {
		Employee employee = employeeService.showDetail(Integer.parseInt(id));
		model.addAttribute("employee", employee);
		return "employee/detail";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員登録画面を表示する
	/////////////////////////////////////////////////////
	/**
	 * 従業員登録画面を出力します.
	 * 
	 * @param model モデル
	 * @return 従業員登録画面
	 */
	@RequestMapping("/showRegister")
	public String showRegister(Model model) {

		return "employee/insert";
	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員を登録する
	/////////////////////////////////////////////////////
	/**
	 * 従業員を登録
	 * 
	 * @param model モデル
	 * @return 従業員一覧画面
	 */
	@RequestMapping("/register")
	public synchronized String register(InsertEmployeeForm form, MultipartFile image, Model model) {
		Employee employee = new Employee();
		
		BeanUtils.copyProperties(form, employee);
		// img
		employee.setImage(image.getOriginalFilename());
		// id
		employee.setId(employeeService.findId()+1);
		// hire_date
		String year = form.getHireYear();
		String month = form.getHireMonth();
		String day = form.getHireDay();
		try {
            String strDate = year + "/" + month + "/" + day;
            SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = sdFormat.parse(strDate);
            employee.setHireDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

		// 画像処理
		String fileName = image.getOriginalFilename();
		
		employeeService.insert(employee);
		try {
			// 保存先を定義
			String uploadPath = "src/main/resources/static/img/";
			byte[] bytes = image.getBytes();

			// 指定ファイルへ読み込みファイルを書き込み
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(uploadPath + fileName)));
			stream.write(bytes);
			stream.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "employee/express";

	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員名で曖昧検索を行う
	/////////////////////////////////////////////////////
	@RequestMapping("/find")
	public String find(String name, Model model) {

		// 入力欄が空の場合は全件表示
		if (Objects.isNull(name) || name.equals("")) {
			return showList(model);
		}

		List<Employee> employeeList = employeeService.fuzzySearch(name);

		if (!employeeList.isEmpty()) { // 検索がヒットした場合
			model.addAttribute("employeeList", employeeList);
			return "employee/list";
		} else {// 検索結果が空の場合
			String msg = "1件もありませんでした";
			employeeList = employeeService.showList();
			model.addAttribute("employeeList", employeeList);
			model.addAttribute("fuzzySearchMessage", msg);
			return "employee/list";
		}

	}

	/////////////////////////////////////////////////////
	// ユースケース：従業員詳細を更新する
	/////////////////////////////////////////////////////
	/**
	 * 従業員詳細(ここでは扶養人数のみ)を更新します.
	 * 
	 * @param form 従業員情報用フォーム
	 * @return 従業員一覧画面へリダクレクト
	 */
	@RequestMapping("/update")
	public String update(@Validated UpdateEmployeeForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return showDetail(form.getId(), model);
		}
		Employee employee = new Employee();
		employee.setId(form.getIntId());
		employee.setDependentsCount(form.getIntDependentsCount());
		return "redirect:/employee/showList";
	}
}

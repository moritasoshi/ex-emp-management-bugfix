package jp.co.sample.emp_management.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.repository.EmployeeRepository;

/**
 * 従業員情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * 従業員情報を全件取得します.
	 * 
	 * @return 従業員情報一覧
	 */
	public List<Employee> showList() {
		List<Employee> employeeList = employeeRepository.findAll();
		return employeeList;
	}
	
	public Integer findId() {
		return employeeRepository.findId();
	}

	/**
	 * 従業員情報を取得します.
	 * 
	 * @param id ID
	 * @return 従業員情報
	 * @throws 検索されない場合は例外が発生します
	 */
	public Employee showDetail(Integer id) {
		Employee employee = employeeRepository.load(id);
		return employee;
	}

	/**
	 * 曖昧検索を行います
	 * 
	 * @param name 引数がnullの場合は例外が発生します
	 * @return
	 */
	public List<Employee> fuzzySearch(String name) throws DataAccessException {
		List<Employee> employeeList = employeeRepository.findByName(name);
		return employeeList;
	}
	
	/**
	 * 従業員情報を登録します.
	 * 
	 * @param employee 更新した従業員情報
	 */
	public void insert(Employee employee) {
		employeeRepository.insert(employee);
	}

}

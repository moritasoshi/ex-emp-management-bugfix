package jp.co.sample.emp_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/common")
public class CommonController {

	/**
	 * エラー画面に遷移する.
	 */
	@RequestMapping("/maintenance")
	public String maintenance() {
		return "common/error"; // errorディレクトリないに404.htmlや500.htmlを作成
	}
}
package jp.co.sample.emp_management.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.sample.emp_management.domain.Employee;
import jp.co.sample.emp_management.service.EmployeeService;

@RestController
@RequestMapping("/ajax")
public class AjaxController {

  @Autowired
  private EmployeeService employeeService;

  @RequestMapping("/find")
  public List<String> find(String name) {
    List<Employee> eList = employeeService.fuzzySearch(name);
    List<String> nameList = eList.stream().map(employee -> employee.getName()).collect(Collectors.toList());
    return nameList;
  }
}
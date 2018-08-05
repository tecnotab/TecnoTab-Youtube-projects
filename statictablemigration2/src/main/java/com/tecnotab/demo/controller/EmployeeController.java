package com.tecnotab.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecnotab.demo.destrepository.EmployeeDestRepository;
import com.tecnotab.demo.entity.Employee;
import com.tecnotab.demo.service.EmployeeService;

@RestController
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	EmployeeDestRepository employeeDestRepository;
	
	
	@RequestMapping("/allEmployees")
	public List<Employee> getEmployees(){
		return employeeService.getAllEmployees();
	}
	

@RequestMapping("/saveEmployees")
	public void saveAllEmp(){	
		System.out.println("inside saveAllEmp method..........");
		List<Employee> employee = employeeService.getAllEmployees();
		employeeDestRepository.save(employee);
		
	}
		
} 



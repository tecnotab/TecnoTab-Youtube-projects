package com.tecnotab.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecnotab.demo.entity.Employee;
import com.tecnotab.demo.repository.EmployeeRepository;

@Service
@Transactional("ds1TransactionManager")
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	public List<Employee> getAllEmployees(){
		List<Employee> employee = employeeRepository.findAll();
		return employee;
	}
	
	
}

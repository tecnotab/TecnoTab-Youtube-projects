package com.tecnotab.demo.destrepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tecnotab.demo.entity.Employee;

@Repository
public interface EmployeeDestRepository extends JpaRepository<Employee, Integer> {

}

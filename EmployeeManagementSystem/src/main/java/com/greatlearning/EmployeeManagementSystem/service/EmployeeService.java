package com.greatlearning.EmployeeManagementSystem.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.greatlearning.EmployeeManagementSystem.entity.Employee;

public interface EmployeeService {
	
	List<Employee> getAllEmployees();
	void addNewEmployee(Employee employee);
	void updateEmployee(Employee employee);
	void deleteEmployeeById(int id);
	Employee getEmployeeById(int id);
	Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
}
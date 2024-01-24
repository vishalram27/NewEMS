package com.greatlearning.EmployeeManagementSystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.greatlearning.EmployeeManagementSystem.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Integer>{

}

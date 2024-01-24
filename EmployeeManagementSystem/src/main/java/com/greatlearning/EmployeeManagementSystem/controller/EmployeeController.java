package com.greatlearning.EmployeeManagementSystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.greatlearning.EmployeeManagementSystem.entity.Employee;
import com.greatlearning.EmployeeManagementSystem.service.EmployeeService;

@Controller
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;

	@RequestMapping("/employees")
	public String viewHomePage(Model model) {
		
		return findPaginated(1, "id", "asc", model);
	}

	@GetMapping("/employees/new")
	public String newEmployee(Model model) {
		model.addAttribute("employee", new Employee());
		return "create_employee";
	}

	@PostMapping("/employees")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		employeeService.updateEmployee(employee);
		return "redirect:/employees";
	}

	@GetMapping("/employees/edit/{id}")
	public String editEmployeeForm(@PathVariable int id, Model model) {
		Employee employee = employeeService.getEmployeeById(id);
		model.addAttribute("employee", employee);
		return "edit_employee";
	}

	@PostMapping("/employees/{id}")
	public String updateEmployeeForm(@PathVariable int id, @ModelAttribute("employee") Employee updatedEmployee,
			Model model) {
		Employee existingEmployee = employeeService.getEmployeeById(id);
		existingEmployee.setId(id);
		existingEmployee.setFirstName(updatedEmployee.getFirstName());
		existingEmployee.setLastName(updatedEmployee.getLastName());
		existingEmployee.setEmail(updatedEmployee.getEmail());

		employeeService.updateEmployee(existingEmployee);
		return "redirect:/employees";
	}

	@GetMapping("/employees/{id}")
	public String deleteEmployee(@PathVariable int id) {
		employeeService.deleteEmployeeById(id);
		return "redirect:/employees";

	}
	
	@GetMapping("/employees/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo, @RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir, Model model) {

		int pageSize = 10;

		Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Employee> listEmployees = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());

		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

		model.addAttribute("listEmployees", listEmployees);
		return "employees";
	}
}
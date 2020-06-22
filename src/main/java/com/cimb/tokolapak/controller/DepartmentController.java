package com.cimb.tokolapak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.tokolapak.entity.Department;
import com.cimb.tokolapak.service.DepartmentService;

@RestController
@RequestMapping("/departments")
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService;
	
	@GetMapping
	public Iterable<Department> getDepartments(){ //for loop
		return departmentService.getAllDepartments();
	}
	
	@PostMapping
	public Department addDepartment(@RequestBody Department department) {
		return departmentService.addDepartment(department);
	}
	
//	@GetMapping {projectId}/employees
	
	@DeleteMapping("{departmentId}")
	public void deleteDepartment(@PathVariable int departmentId) {
		return;
		
	}

}

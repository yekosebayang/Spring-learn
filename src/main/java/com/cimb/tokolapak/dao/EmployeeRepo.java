package com.cimb.tokolapak.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cimb.tokolapak.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer>{
		
	}


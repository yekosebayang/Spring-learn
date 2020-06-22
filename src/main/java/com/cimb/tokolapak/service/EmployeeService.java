package com.cimb.tokolapak.service;

import com.cimb.tokolapak.entity.Employee;
import com.cimb.tokolapak.entity.EmployeeAddress;
//import com.cimb.tokolapak.entity.Employee;

public interface EmployeeService {
	public void deleteEmployeeAddress(EmployeeAddress employeeAddress);

	public Employee addEmployeeAddress(EmployeeAddress employeeAddress, int employeeid);

	public Employee addEmployeeToDepartment(int departmentId ,int employeeId);
}

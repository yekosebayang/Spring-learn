package com.cimb.tokolapak.service.impl;

//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cimb.tokolapak.dao.EmployeeRepo;
import com.cimb.tokolapak.dao.DepartmentRepo;
import com.cimb.tokolapak.dao.EmployeeAddressRepo;
import com.cimb.tokolapak.entity.Department;
import com.cimb.tokolapak.entity.Employee;
import com.cimb.tokolapak.entity.EmployeeAddress;
//import com.cimb.tokolapak.entity.Product;
import com.cimb.tokolapak.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private EmployeeAddressRepo employeeAddressRepo;
	
	@Autowired
	private DepartmentRepo departmentRepo;

	@Override
	public void deleteEmployeeAddress(EmployeeAddress employeeAddress) {
		employeeAddress.getEmployee().setEmployeeAddress(null);// untuk memisahkan hubungan dari employee ke eaddress
		employeeAddress.setEmployee(null);// memutuskan hubungan dari Eaddress ke employee

		employeeAddressRepo.delete(employeeAddress);
	}// nah kita kan udah bisa get data per id, sekarang setelah di get by id, kita coba get address nya doang.
	
	@Override
	public Employee addEmployeeAddress(EmployeeAddress employeeAddress, int employeeid){	
		Employee findEmployee = employeeRepo.findById(employeeid).get();
		if (findEmployee == null)
			throw new RuntimeException("data Employeenya yang ingin diberi 'Address' belum ada");
		findEmployee.setEmployeeAddress(employeeAddress);
		
		return employeeRepo.save(findEmployee);
	}
	
	@Override
	public Employee addEmployeeToDepartment(int departmentId ,int employeeId) {
		Employee findEmployee = employeeRepo.findById(employeeId).get(); //notice the get() to get rid of the option
		if (findEmployee.toString() == "Optional.empty") {
			throw new RuntimeException("Employee ID belum terdaftar");
		}
		
		Department findDepartment = departmentRepo.findById(departmentId).get();
		if (findDepartment.toString() == "Optional.empty") {
			throw new RuntimeException("Department ID belum terdaftar");
		}
		findEmployee.setDepartment(findDepartment);
		return employeeRepo.save(findEmployee);
	}
}

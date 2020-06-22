package com.cimb.tokolapak.controller;

import java.util.Optional;

//import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.tokolapak.dao.DepartmentRepo;
//import com.cimb.tokolapak.dao.DepartmentRepo;
import com.cimb.tokolapak.dao.EmployeeAddressRepo;
import com.cimb.tokolapak.dao.EmployeeRepo;
import com.cimb.tokolapak.dao.ProjectRepo;
import com.cimb.tokolapak.entity.Department;
//import com.cimb.tokolapak.entity.Department;
import com.cimb.tokolapak.entity.Employee;
import com.cimb.tokolapak.entity.EmployeeAddress;
//import com.cimb.tokolapak.entity.Product;
import com.cimb.tokolapak.entity.Project;
import com.cimb.tokolapak.service.EmployeeService;
import com.cimb.tokolapak.util.EmailUtil;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeAddressRepo employeeAddressRepo;
	
	@Autowired
	private ProjectRepo projectRepo;
	
	@Autowired
	private DepartmentRepo departmentRepo;
	
	@Autowired
	private EmailUtil emailUtil;
	
	@PostMapping
	public Employee addEmployee(@RequestBody Employee employee) {
		return employeeRepo.save(employee);
	}
	
	@GetMapping
	public Iterable<Employee> getEmployee(){
		return employeeRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Employee> getEmployeeById(@PathVariable int id) {
		return employeeRepo.findById(id);
	}
	
	@GetMapping("/address/{id}")
	public Optional<EmployeeAddress> getEmployeeAddressById(@PathVariable int id) {
		return employeeAddressRepo.findById(id);
	}
	
	@PutMapping("/{employeeId}/address")
	public Employee addEmployeeAddress(@RequestBody EmployeeAddress employeeAddress, @PathVariable int employeeid){	

//		Employee findEmployee = employeeRepo.findById(employeeid).get();
//		if (findEmployee == null)
//			throw new RuntimeException("data Employeenya yang ingin diberi 'Address' belum ada");
//		findEmployee.setEmployeeAddress(employeeAddress);
//		
//		return employeeRepo.save(findEmployee);
		return employeeService.addEmployeeAddress(employeeAddress, employeeid);

	}
	
	
	@DeleteMapping("/address/{id}")
	public void deleteEmployeeAddressById(@PathVariable int id) {
		Optional<EmployeeAddress> employeeAddress = employeeAddressRepo.findById(id);		
		employeeService.deleteEmployeeAddress(employeeAddress.get());
	}
	
	@PostMapping("/{employeeId}/departments/{departmentId}") // dengan asumsi employee dan department sudah ada
	public Employee addEmployeeToDepartment(@PathVariable int departmentId , @PathVariable int employeeId) {
		return employeeService.addEmployeeToDepartment(departmentId, employeeId);
	}
	
	@PostMapping("{employeeId}/projects/{projectId}")
	public Employee addProjectToEmployee(@PathVariable int employeeId, @PathVariable int projectId) {
		Employee findEmployee = employeeRepo.findById(employeeId).get();
		
		Project findProject = projectRepo.findById(projectId).get();
		
		findEmployee.getProjects().add(findProject); 
		
		return employeeRepo.save(findEmployee);
	}
	
	@PostMapping("/department/{departmentId}")
	public Employee addEmployee(@RequestBody Employee employee, @PathVariable int departmentId) {
		Department findDepartment = departmentRepo.findById(departmentId).get();
		
		if (findDepartment == null)
			throw new RuntimeException("Department not found");
		
		employee.setDepartment(findDepartment);
						
		emailUtil.sendEmail(employee.getEmail(), "Registrasi Karyawan", "<h1>Selamat!</h1>\n Anda telah bergabung bersama kami!\n Klik <a href=\"http://localhost:8080/bla\">link</a> ini untuk verifikasi email anda ");
		
		return employeeRepo.save(employee);
	}
}

package com.cimb.tokolapak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.tokolapak.dao.UserRepo;
import com.cimb.tokolapak.entity.User;
import com.cimb.tokolapak.util.EmailUtil;


@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired //karena datang dari kelas lain
	private EmailUtil emailUtil;
	
	private PasswordEncoder pwEncoder = new BCryptPasswordEncoder();
	
	@PostMapping
	public User registerUser(@RequestBody User user) {
//		User findUser = userRepo.findByUsername(user.getUsername());
//		
//		if (findUser.toString() != "Optional.empty") {
//			throw new RuntimeException("Username exists!");
//		}
		
		// new string, encodedpassword = metode pwEncoder.encode(ambil dari user.getPassword()
		String encodedPassword = pwEncoder.encode(user.getPassword());
		// baru disini passwordnya di set new string diatas, yaitu encoded password
		user.setPassword(encodedPassword);
		
		User savedUser = userRepo.save(user);
		savedUser.setPassword(null); // biar ga ngrim password kak,		
		
		return savedUser;
	}
	
	// Cara 1 menggunakan POST method
	@PostMapping("/login")
	public User loginUser(@RequestBody User user) {
		User findUser = userRepo.findByUsername(user.getUsername()).get();
		
		if (pwEncoder.matches(user.getPassword(), findUser.getPassword())) {
			findUser.setPassword(null);
			return findUser;
		}
		throw new RuntimeException("Wrong Password!");
//		return null;
	}
	
	// localhost:8080/users/login?username=seto&password=password123
	@GetMapping("/login")
	public User getLoginUser(@RequestParam String username, @RequestParam String password) {
		User findUser = userRepo.findByUsername(username).get();
		
			if (pwEncoder.matches(password, findUser.getPassword())) {	
				findUser.setPassword(null);
				return findUser;
			} 
			
		throw new RuntimeException("Wrong password!");
		}
	
	@PostMapping("/verifikasi")
	public User registerUserVerif(@RequestBody User user) {
		String encodedPassword = pwEncoder.encode(user.getPassword());
	 
		user.setPassword(encodedPassword);
		
		User savedUser = userRepo.save(user);
		savedUser.setPassword(null); // biar ga ngrim password kak,
		
		// set false, 
		user.setVerifikasi(false);
		emailUtil.sendEmail("rukomailer@gmail.com", "Registrasi Karyawan","<h1>Hallo, "+ user.getUsername() +"</h1>Klik <a href=\"http://localhost:8081/users/editVerif/"+user.getId()+"\">link</a> ini untuk verifikasi email anda");
		
		return savedUser;
	}
	
	@GetMapping("/editVerif/{userId}")
	public User editVerified(@PathVariable int userId) {
		User findUser = userRepo.findById(userId).get();

		findUser.setVerifikasi(true);
		return userRepo.save(findUser);
	}
}

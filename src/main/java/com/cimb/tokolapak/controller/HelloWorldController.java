package com.cimb.tokolapak.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloWorldController {
	@GetMapping("/hello")
	public String helloWorld() {
		return "Hellooooo";
	}
	
	@GetMapping("/hello/{name}")
	public String helloName(@PathVariable() String name) {
		return "Hello " + name + " wish you had a great venture on life";
	}
	
	@GetMapping("/angka/{nomer}")
	public String helloName(@PathVariable() int nomer) {
		return "'" + nomer +"' sesuai kan nomor sama angka nya? hehe";
	}
}

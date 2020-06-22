package com.cimb.tokolapak.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import com.cimb.tokolapak.dao.ProductRepo;
import com.cimb.tokolapak.entity.Product;
import com.cimb.tokolapak.service.ProductService;
@RestController
@CrossOrigin
public class ProductController {
	
	// Controller -> Service -> DAO / Repo -> DB
	
//	Axios.post(API_URL, {
//		productName: "",
//		price: 25000
//	})
	
	// localhost:8080
	// const API_URL = localhost:8080
	// Axios.post(API_URL + "/products")
	
	// localhost:8080/products
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public Iterable<Product> getProducts() {
		return productService.getProducts();
	}
	
	@GetMapping("/products/{id}")
	public Optional<Product> getProductById(@PathVariable int id) {
		return productService.getProductById(id);
	}
	
	@PostMapping("/products")
	public Product addProduct(@RequestBody Product product) {
		return productService.addProduct(product);
	}
	
	@PutMapping("/products")
	public Product updateProductById(@RequestBody Product product) {
		return productService.updateProduct(product);
	}
	
	
	@DeleteMapping("/products/{id}")
	public void deleteProductById(@PathVariable int id) {
		this.productService.deleteProduct(id);
	} // make sure yang di delete ada.
	
	@GetMapping("/productName/{productName}")
	public Product getProductByProductName(@PathVariable String productName) {
		return productRepo.findByProductName(productName);
	}
	
	@GetMapping("/products/custom")
	public Iterable<Product> customQueryGet(@RequestParam double maxPrice, @RequestParam String namaProduk){
		return productRepo.findProductByMaxPrice(maxPrice, namaProduk);
	}
}
// perantara front dan back end
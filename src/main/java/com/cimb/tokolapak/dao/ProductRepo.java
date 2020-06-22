package com.cimb.tokolapak.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.cimb.tokolapak.entity.Product;


public interface ProductRepo extends CrudRepository <Product, Integer> {
	public Product findByProductName(String productName);
	
//	@Query(value = "SELECT * FROM Product WHERE price < 10000", nativeQuery = true) // contoh custom-query
//	public  Iterable<Product> findProductByMinPrice();//method yang query-nya harus dibuat manual
//	
//	@Query(value = "SELECT * FROM Product WHERE price < ?1", nativeQuery = true) // contoh custom-query
//	public  Iterable<Product> findProductByMinPrice(double minPrice);//method yang query-nya harus dibuat manual
//	
//	@Query(value = "SELECT * FROM Product WHERE price < ?1 and product_name = ?2", nativeQuery = true) // contoh custom-query
//	public  Iterable<Product> findProductByMinPrice(double minPrice, String productName);//method yang query-nya harus dibuat manual
//	// ?1 dan ?2 urutan untuk pengisian dari parameter yang dibawah secara berurutan
//	
//	@Query(value = "SELECT * FROM Product WHERE price < :maxPrice and product_name like %:productName%", nativeQuery = true) // contoh custom-query
//	public  Iterable<Product> findProductByMaxPrice(@Param("MaxPrice") double maxPrice, @Param("productName") String namaProduk);
	
// Indexed parameters
	@Query(value = "SELECT * FROM Product WHERE price > ?1 and product_name = ?2", nativeQuery = true)
	public Iterable<Product> findProductByMinPrice(double minPrice, String productName);
	
// Named parameters
	@Query(value = "SELECT * FROM Product WHERE price < :maxPrice and product_name like %:productName%", nativeQuery = true)
	public Iterable<Product> findProductByMaxPrice(@Param("maxPrice") double maxPrice, @Param("productName") String namaProduk);
}

// yg bertanggung jawab untuk berhubungan denga database kita
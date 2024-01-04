package com.kh.springchap2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.springchap2.model.Product;
import com.kh.springchap2.service.ProductService;

//@Controller //html 파일을 바로 바라보는 것
@RestController //api로 전달받을 수 있게 해주는 컨트롤러
@RequestMapping("/api/product")
@CrossOrigin(origins="http://localhost:3000", allowCredentials="true", allowedHeaders="*")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/item")
	public ResponseEntity<List<Product>> getAllProduct() {
		List<Product> products = productService.getAllProduct();
		return ResponseEntity.ok(products);
	}
}

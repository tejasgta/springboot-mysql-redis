package com.example.controller;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Product;
import com.example.payload.ProductRequest;
import com.example.service.ProductService;

@RestController
@RequestMapping("/product")
public class MainController {

	@Autowired
	private ProductService service;
	
	@PostMapping
	public Product save(@RequestBody ProductRequest productRequest) {
		return service.save(productRequest);
	}
	
	@GetMapping
	public List<Product> getAllProducts(){
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity findByProductId(@PathVariable Long id) {
		Optional<Product> p=service.findProductById(id);
		if(p.isPresent())
			return new ResponseEntity<Product>(p.get(),HttpStatus.OK);
		else
			return new ResponseEntity<String>("Product with Id: " + id +" does not exist!!!",HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/{id}")
	public String remove(@PathVariable Long id) {
		String res = "Product does not exist!!!";
		Optional<Product> p=service.findProductById(id);
		if(p.isPresent()) {
			service.deleteProduct(id);
			res = "Product Deleted";
		}
		return res;
	}
}

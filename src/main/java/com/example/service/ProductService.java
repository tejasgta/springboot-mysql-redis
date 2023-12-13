package com.example.service;

import java.util.List;
import java.util.Optional;

import com.example.entity.Product;
import com.example.payload.ProductRequest;

public interface ProductService {

	Product save(ProductRequest product);

	List<Product> findAll();

	Optional<Product> findProductById(Long id);

	void deleteProduct(Long id);

}

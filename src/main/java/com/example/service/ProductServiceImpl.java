package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.entity.Product;
import com.example.payload.ProductRequest;
import com.example.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository repo;

	@Override
	public Product save(ProductRequest request) {
		Product p = new Product(request.getName(), request.getQty(), request.getPrice());
		return repo.save(p);
	}

	@Override
	public List<Product> findAll() {
		return repo.findAll();
	}

	@Override
	@Cacheable(cacheNames = "Product", key = "#id")
	public Optional<Product> findProductById(Long id) {
		return repo.findById(id);
	}

	@Override
	@CacheEvict(cacheNames = "Product", key = "#id")
	public void deleteProduct(Long id) {
		repo.deleteById(id);
	}

}

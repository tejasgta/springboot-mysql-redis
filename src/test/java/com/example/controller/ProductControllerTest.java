package com.example.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.entity.Product;
import com.example.payload.ProductRequest;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ProductController.class)
@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
	
	@MockBean
	private ProductRepository repo;
	
	@MockBean
	private ProductService service;
	
	@InjectMocks
	private ProductController controller;
	
	@Autowired
	private MockMvc mockMvc;
	
	private List<Product> products;
	
	@BeforeEach 
	 public void setUp()
	 {
	     products = Stream.of(new Product("TV",1,20000),new Product("PC",10,200000),
				 new Product("Mobile",5,10000)).collect(Collectors.toList());
	 }

	@Test
	public void saveProductTest() throws Exception{
		ProductRequest pr = new ProductRequest(products.get(0));
		when(service.saveProduct(pr)).thenReturn(products.get(0));
		mockMvc.perform(post("/products").
				contentType(MediaType.APPLICATION_JSON).
				content(new ObjectMapper().writeValueAsString(pr))).
				andExpect(status().isOk());
	}
	
	@Test
	public void getAllProductsTest() throws Exception{
		when(service.getProducts()).thenReturn(products);
		mockMvc.perform(get("/products"))
			.andExpectAll(
					status().isOk(),
					content().contentType(MediaType.APPLICATION_JSON),
					jsonPath("$[0].name").value("TV"));
	}
	
	@Test
	public void getProductByIdTest() throws Exception{
		when(service.findProductById(anyLong())).thenReturn(Optional.of(products.get(1)));
		mockMvc.perform(get("/products/{id}",1L))
			.andExpectAll(
					status().isOk(),
					content().contentType(MediaType.APPLICATION_JSON),
					jsonPath("$.name").value("PC"));
	}
	
	@Test
	public void deleteProductTest() throws Exception{
		doNothing().when(service).deleteProduct(anyLong());
		when(service.findProductById(anyLong())).thenReturn(Optional.of(products.get(1)));
		mockMvc.perform(delete("/products/{id}",1L))
			.andExpectAll(
					status().isOk(),
					content().string("Product Deleted"));
		verify(service).findProductById(anyLong());
	}

}

package com.example.controller;


import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Product;
import com.example.payload.ProductRequest;
import com.example.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Product", description = "Product management APIs")
@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService service;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Operation(summary = "Save Products",
		      description = "Create a Product. The response is a Product object with id, name, quantity and price.",
		      tags = { "products", "post" })
	  @ApiResponses({
	      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Product.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
	      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@PostMapping
	public Product save(@RequestBody ProductRequest productRequest) {
		return service.saveProduct(productRequest);
	}
	
	@Operation(summary = "Retrieve list of all Products",
		      description = "Get a list of Product objects. The response is an array of Product objects with id, name, quantity and price.",
		      tags = { "products", "get" })
	@GetMapping
	public List<Product> getAllProducts(){
		logger.info("Get all products request received");
		return service.getProducts();
	}
	
	@Operation(summary = "Retrieve a Product by Id",
		      description = "Get a Product object by specifying its id. The response is Product object with id, name, quantity and price.",
		      tags = { "products", "get" })
	  @ApiResponses({
	      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = ResponseEntity.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "400", description = "Product not Found") })
	@GetMapping("/{id}")
	public ResponseEntity findByProductId(@PathVariable Long id) {
		Optional<Product> p=service.findProductById(id);
		if(p.isPresent())
			return new ResponseEntity<Product>(p.get(),HttpStatus.OK);
		else
			return new ResponseEntity<String>("Product with Id: " + id +" does not exist!!!",HttpStatus.BAD_REQUEST);
	}
	
	@Operation(summary = "Delete a Product by Id",
		      description = "Delete a Product object by specifying its id. The response is String Response.",
		      tags = { "products", "delete" })
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
	
	@Operation(summary = "Retrieve Product based on Name and Quantity",
		      description = "The response is a Product object with id, name, quantity and price.",
		      tags = { "products", "get" })
	@GetMapping("/filter")
	public ResponseEntity getProductByNameAndQty(@RequestParam String name,@RequestParam int qty){
		logger.info("Get product by name and quantity request received");
		Product p = service.getProductByNameAndQty(name, qty);
		if(p != null)
			return new ResponseEntity<Product>(p,HttpStatus.OK);
		else
			return new ResponseEntity<String>("Product with Name: " + name +" and Qty: " +qty + " does not exist!!!",HttpStatus.BAD_REQUEST);
	}
}

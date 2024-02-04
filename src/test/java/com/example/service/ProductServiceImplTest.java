package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.entity.Product;
import com.example.payload.ProductRequest;
import com.example.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

	@Mock
	private ProductRepository repo;

	@InjectMocks
	private ProductServiceImpl service;

	private List<Product> products;

	@BeforeEach
	public void setUp() {
		products = Stream
				.of(new Product("TV", 1, 20000), new Product("PC", 10, 200000), new Product("Mobile", 5, 10000))
				.collect(Collectors.toList());
	}

	@Test 
	 public void getProductsTest()
	 {
		 when(repo.findAll()).thenReturn(products);
		 assertEquals(3, service.getProducts().size());
	 }

	@Test
	 public void saveProductTest() {
		 when(repo.save(any(Product.class))).thenReturn(products.get(0));
		 assertEquals(products.get(0), service.saveProduct(new ProductRequest(products.get(0))));
	 }

	@Test
	 public void findProductByIdTest() {
		 when(repo.findById(1L)).thenReturn(Optional.of(products.get(0)));
		 assertEquals(Optional.of(products.get(0)), service.findProductById(1L));
	 }

	@Test
	public void deleteProductTest() {
//		Mockito.doNothing().when(repo).delete(any(Product.class));
		service.deleteProduct(1L);
		verify(repo).deleteById(1L);
	}

}

package com.temmytechie;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.temmytechie.entity.Product;
import com.temmytechie.repository.ProductDao;

@SpringBootApplication
@RestController
@RequestMapping("/product")
@EnableCaching
public class RedisApplication {
	
	@Autowired
	private ProductDao dao;
	
	@PostMapping
	public Product save(@RequestBody Product product)
	{
	
		return dao.save(product);
	}
	
	@GetMapping
	public List<Product>getAllProducts()
	{
		
		return dao.findAll();
		
	} 
	
	/**
	 * 
	 * @param id
	 * @return
	 * only get from cache unless result.price > 200 
	 */
	@GetMapping("/{id}")
	@Cacheable(key="#id",value = "product",unless = "#result.price > 500" )
	public Product findProduct(@PathVariable int id) {
		
		return dao.findProductById(id);
		}
	
	@DeleteMapping("/{id}")
	public String remove(@PathVariable int id)
	{
		return dao.deleteProduct(id);
	}

	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}

}

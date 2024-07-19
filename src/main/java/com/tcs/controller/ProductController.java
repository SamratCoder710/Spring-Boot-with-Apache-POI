package com.tcs.controller;

import com.tcs.entity.ProductEntity;
import com.tcs.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping(value = { "/products" })
public class ProductController {

	@Autowired(required = true)
	IProductService service;

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductEntity> getProductById(@PathVariable("id") int id) {
		System.out.println("Fetching Product with id " + id);
		ProductEntity entity = service.findProductById(id);
		if (entity == null) {
			return new ResponseEntity<ProductEntity>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ProductEntity>(entity, HttpStatus.OK);
	}

	@GetMapping(value = "/get", headers = "Accept=application/json")
	public List<ProductEntity> getAllProducts() {
		List<ProductEntity> tasks = service.getProducts();
		return tasks;

	}

	@PostMapping("/add")
	public ResponseEntity<ProductEntity> addProductToExcel(@RequestBody ProductEntity product){
		try {
			ProductEntity addedProduct = service.writeEntryToExcel(product);
			return new ResponseEntity<>(addedProduct,HttpStatus.CREATED);
		}catch(Exception e){
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}

	}
}

package com.tcs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.entity.ProductEntity;
import com.tcs.service.IProductService;
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
}

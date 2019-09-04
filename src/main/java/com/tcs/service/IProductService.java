package com.tcs.service;

import java.util.List;

import com.tcs.entity.ProductEntity;

public interface IProductService {
	 public List<ProductEntity> getProducts();
	 public ProductEntity findProductById(int id);
}

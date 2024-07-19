package com.tcs.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.tcs.entity.ProductEntity;

public interface IProductService {
	 public List<ProductEntity> getProducts();
	 public ProductEntity findProductById(int id);

	ProductEntity writeEntryToExcel(ProductEntity product) throws IOException;
}

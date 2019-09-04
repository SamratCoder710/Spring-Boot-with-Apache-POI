package com.tcs.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.tcs.entity.ProductEntity;

@Service
public class ProductServiceImpl implements IProductService {

	private static List<ProductEntity> productList;

	public ProductServiceImpl() {
		productList = new ArrayList<ProductEntity>();
		productList = readProductFromExcel();
	}

	private List<ProductEntity> readProductFromExcel() {
		List<ProductEntity> list = new ArrayList<ProductEntity>();
		try {
			File file = ResourceUtils.getFile("classpath:excel/product.xlsx");
			FileInputStream fis = null;
			fis = new FileInputStream(file.getAbsolutePath());
			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(fis);
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) {
					ProductEntity product = new ProductEntity();
					Row row = rowIterator.next();
					Iterator<Cell> cellIterator = row.cellIterator();
					// Iterating over each cell (column wise) in a particular row.
					while (cellIterator.hasNext()) {

						Cell cell = cellIterator.next();
						if (cell.getColumnIndex() == 0) {
							product.setId((int) cell.getNumericCellValue());
						} else if (cell.getColumnIndex() == 1) {
							product.setItem_name(cell.getStringCellValue());
						} else if (cell.getColumnIndex() == 2) {
							product.setBrand_name(cell.getStringCellValue());
						} else if (cell.getColumnIndex() == 3) {
							product.setPrice((int) cell.getNumericCellValue());
						} else if (cell.getColumnIndex() == 4) {
							product.setRating((int) cell.getNumericCellValue());
						}
					}
					list.add(product);
				}
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<ProductEntity> getProducts() {
		return productList;
	}

	@Override
	public ProductEntity findProductById(int id) {
		ProductEntity entity = null;
		for (ProductEntity productEntity : productList) {
			if (id == productEntity.getId()) {
				entity = productEntity;
				break;
			}
		}
		return entity;
	}

}

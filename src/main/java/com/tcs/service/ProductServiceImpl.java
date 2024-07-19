package com.tcs.service;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tcs.config.AppConfig;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.tcs.entity.ProductEntity;

@Service
public class ProductServiceImpl implements IProductService {

    private static List<ProductEntity> productList;

    private final AppConfig appConfig;

    public ProductServiceImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
        productList = new ArrayList<ProductEntity>();
        productList = readProductFromExcel();
    }

    private List<ProductEntity> readProductFromExcel() {
        List<ProductEntity> list = new ArrayList<ProductEntity>();
        try {
            FileInputStream fis = null;
            fis  = new FileInputStream(appConfig.getFilepath());

            Workbook workbook = new XSSFWorkbook(fis);
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                for (Row cells : sheet) {
                    ProductEntity product = new ProductEntity();
                    Row row = cells;
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
        System.out.println(list);
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

    @Override
    public ProductEntity writeEntryToExcel(ProductEntity product) throws IOException {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        Workbook workbook = null;
        try {
            fileInputStream  = new FileInputStream(appConfig.getFilepath());
            workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet1 = workbook.getSheetAt(0);

            int newIndex = sheet1.getLastRowNum() + 1;

            Row row = sheet1.createRow(newIndex);

            Cell cell0 = row.createCell(0);
            cell0.setCellValue(newIndex+1);
            Cell cell1 = row.createCell(1);
            cell1.setCellValue(product.getItem_name());
            Cell cell2 = row.createCell(2);
            cell2.setCellValue(product.getBrand_name());

            Cell cell3 = row.createCell(3);
            cell3.setCellValue(product.getPrice());
            Cell cell4 = row.createCell(4);
            cell4.setCellValue(product.getRating());
            product.setId(newIndex+1);
			productList.add(product);

            fileInputStream.close();

            fileOutputStream = new FileOutputStream(appConfig.getFilepath());
            workbook.write(fileOutputStream);

            return product;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Ensure resources are closed
            try {
                if (workbook != null) {
                    workbook.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }



}

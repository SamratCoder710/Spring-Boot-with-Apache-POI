package com.tcs.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductEntity {

	private String item_name,brand_name;
	private int id,price,rating;
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public ProductEntity(String item_name, String brand_name, int id, int price, int rating) {
		super();
		this.item_name = item_name;
		this.brand_name = brand_name;
		this.id = id;
		this.price = price;
		this.rating = rating;
	}
	public ProductEntity() {
		super();
	}
	@Override
	public String toString() {
		return "ProductEntity [item_name=" + item_name + ", brand_name=" + brand_name + ", id=" + id + ", price="
				+ price + ", rating=" + rating +"]";
	}

	
	
}

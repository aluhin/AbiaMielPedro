package com.abiamiel.model;

import java.util.HashSet;
import java.util.Set;

public class Product {
	
	private int id;
	private String name;
	private String description;
	private float price;
	private Set<OrderProduct> ordersProducts = new HashSet<OrderProduct>();
	
	public Product() {
	}

	public Product(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Set<OrderProduct> getOrdersProducts() {
		return ordersProducts;
	}
	public void setOrdersProducts(Set<OrderProduct> ordersProducts) {
		this.ordersProducts = ordersProducts;
	}
}

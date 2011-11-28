package com.abiamiel.model;

public class OrderProduct {
	
	private int id;
	private int quantity;
	private MyOrder order;
	private Product product;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public MyOrder getOrder() {
		return order;
	}
	public void setOrder(MyOrder order) {
		this.order = order;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
}

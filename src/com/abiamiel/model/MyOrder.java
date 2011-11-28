package com.abiamiel.model;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MyOrder {
	
	private int id;
	private String title;
	private Date orderedAt;
	private String comments;
	private Customer customer;
	private Set<OrderProduct> ordersProducts = new HashSet<OrderProduct>();

	public MyOrder() {
	}

	public MyOrder(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getOrderedAt() {
		return orderedAt;
	}
	public void setOrderedAt(Date orderedAt) {
		this.orderedAt = orderedAt;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Set<OrderProduct> getOrdersProducts() {
		return ordersProducts;
	}
	public void setOrdersProducts(Set<OrderProduct> ordersProducts) {
		this.ordersProducts = ordersProducts;
	}
	public String getFormatedOrderedAt() {
		return DateFormat.getDateTimeInstance().format(orderedAt);
	}
}

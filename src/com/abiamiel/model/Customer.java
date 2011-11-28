package com.abiamiel.model;

import java.util.HashSet;
import java.util.Set;

public class Customer {
	
	private int id;
	private String nick;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private String address;
	private String telephone;
	private boolean active;
	private boolean admin;
	
	private Set<MyOrder> orders = new HashSet<MyOrder>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public Set<MyOrder> getOrders() {
		return orders;
	}
	public void setOrders(Set<MyOrder> orders) {
		this.orders = orders;
	}
	public String toString() {
		return firstName + " " + lastName;
	}
}

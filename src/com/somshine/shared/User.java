package com.somshine.shared;

import java.io.Serializable;

public class User implements Serializable {
	private Long id;
	private int shoppeId;
	private String firstName;
	private String lastName;
	private String address;
	
	public User() {}

	public User(Long id, int shoppeId, String firstName, String lastName, String address) {
		super();
		this.id = id;
		this.shoppeId = shoppeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getShoppeId() {
		return shoppeId;
	}

	public void setShoppeId(int shoppeId) {
		this.shoppeId = shoppeId;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", shoppeId=" + shoppeId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", address=" + address + "]";
	}
	
}


package com.ttk.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "\"order\"")
public class Order {

	@Id
	@Column(name = "order_id")
	private int orderId;

	@Column(name = "buyer_name")
	private String buyerName;

	@Column(name = "buyer_email")
	private String buyerEmail;

	@Column(name = "order_date")
	private Date orderDate;

	@Column(name = "order_total_value")
	private BigDecimal orderTotalValue;

	@Column(name = "address")
	private String address;

	@Column(name = "postcode")
	private int postcode;

	@OneToMany(mappedBy = "order")
	@LazyCollection(LazyCollectionOption.FALSE)
	private Set<OrderItem> items;

	public Order() {
	}

	public Order(int orderId, String buyerName, String buyerEmail, Date orderDate, BigDecimal orderTotalValue,
			String address, int postcode) {
		this.orderId = orderId;
		this.buyerName = buyerName;
		this.buyerEmail = buyerEmail;
		this.orderDate = orderDate;
		this.orderTotalValue = orderTotalValue;
		this.address = address;
		this.postcode = postcode;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerEmail() {
		return buyerEmail;
	}

	public void setBuyerEmail(String buyerEmail) {
		this.buyerEmail = buyerEmail;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public BigDecimal getOrderTotalValue() {
		return orderTotalValue;
	}

	public void setOrderTotalValue(BigDecimal orderTotalValue) {
		this.orderTotalValue = orderTotalValue;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPostcode() {
		return postcode;
	}

	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}

	public Set<OrderItem> getItems() {
		return items;
	}

	public void setItems(Set<OrderItem> items) {
		this.items = items;
	}

}

package com.ttk.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_item")
public class OrderItem {

	@Id
	@Column(name = "order_item_id")
	private int orderItemId;

	@Column(name = "sale_price")
	private BigDecimal salePrice;

	@Column(name = "shipping_price")
	private BigDecimal shippingPrice;

	@Column(name = "total_item_price")
	private BigDecimal totalItemPrice;

	@Column(name = "sku")
	private String sku;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	// @Type(type = "org.hibernate.type.EnumType",
	// parameters = {
	// @Parameter(name = "enumClass", value = "com.ttk.model.PGEnumStatusType"),
	// @Parameter(name = "type", value = "12"),
	// @Parameter(name = "useNamed", value = "true")
	// })
	private Status status;

	@ManyToOne
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	public OrderItem() {
	}

	public OrderItem(int orderItemId, Order order, BigDecimal salePrice, BigDecimal shippingPrice,
			BigDecimal totalItemPrice, String sku, Status status) {
		this.orderItemId = orderItemId;
		this.order = order;
		this.salePrice = salePrice;
		this.shippingPrice = shippingPrice;
		this.totalItemPrice = totalItemPrice;
		this.sku = sku;
		this.status = status;
	}

	public int getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(int orderItemId) {
		this.orderItemId = orderItemId;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public BigDecimal getShippingPrice() {
		return shippingPrice;
	}

	public void setShippingPrice(BigDecimal shippingPrice) {
		this.shippingPrice = shippingPrice;
	}

	public BigDecimal getTotalItemPrice() {
		return totalItemPrice;
	}

	public void setTotalItemPrice(BigDecimal totalItemPrice) {
		this.totalItemPrice = totalItemPrice;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}

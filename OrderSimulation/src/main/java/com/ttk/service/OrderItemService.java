package com.ttk.service;

import java.util.List;

import com.ttk.model.OrderItem;

public interface OrderItemService {

	void addOrderItems(List<OrderItem> items) throws Exception;
	
}

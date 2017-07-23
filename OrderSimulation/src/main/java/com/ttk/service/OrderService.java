package com.ttk.service;

import java.util.List;

import com.ttk.model.Order;

public interface OrderService {

	void addOrders(List<Order> items) throws Exception;

}

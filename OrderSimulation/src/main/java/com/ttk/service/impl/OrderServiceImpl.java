package com.ttk.service.impl;

import java.util.List;

import com.ttk.model.Order;
import com.ttk.service.OrderService;
import com.ttl.dao.impl.OrderDaoImpl;

public class OrderServiceImpl implements OrderService {

	@Override
	public void addOrders(List<Order> items) throws Exception {
		OrderDaoImpl orderDao = new OrderDaoImpl();
		for (Order order : items) {

			orderDao.addItem(order);
		}
	}

}

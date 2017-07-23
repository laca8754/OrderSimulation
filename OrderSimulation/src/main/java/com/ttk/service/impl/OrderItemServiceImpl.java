package com.ttk.service.impl;

import java.util.List;

import com.ttk.model.OrderItem;
import com.ttk.service.OrderItemService;
import com.ttl.dao.impl.OrderItemDaoImpl;

public class OrderItemServiceImpl implements OrderItemService {

	@Override
	public void addOrderItems(List<OrderItem> items) throws Exception {
		OrderItemDaoImpl orderItemDao = new OrderItemDaoImpl();
		for (OrderItem orderItem : items) {
			orderItemDao.addItem(orderItem);
		}
	}

}

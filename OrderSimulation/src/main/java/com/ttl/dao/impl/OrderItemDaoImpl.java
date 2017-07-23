package com.ttl.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;

import com.ttk.abstractdao.AbstractDaoImpl;
import com.ttk.dao.OrderItemDao;
import com.ttk.hibernate.HibernateUtil;
import com.ttk.model.OrderItem;

public class OrderItemDaoImpl extends AbstractDaoImpl<OrderItem> implements OrderItemDao {

	
	/**
	 * overwrite the original addItem method
	 */
	@Override
	public void addItem(OrderItem t) throws Exception {
		sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createSQLQuery("INSERT INTO order_item(order_item_id,order_id,"
				+ "sale_price,shipping_price,total_item_price,sku,status) "
				+ "VALUES (:order_item_id,:order_id,:sale_price,:shipping_price,:total_item_price,:sku,CAST(:status as Status));");
		query.setParameter("order_item_id", t.getOrderItemId());
		query.setParameter("order_id", t.getOrder().getOrderId());
		query.setParameter("sale_price", t.getSalePrice());
		query.setParameter("shipping_price", t.getShippingPrice());
		query.setParameter("total_item_price", t.getTotalItemPrice());
		query.setParameter("sku", t.getSku());
		query.setParameter("status", t.getStatus().toString());
		query.executeUpdate();
		session.getTransaction().commit();

	}

}

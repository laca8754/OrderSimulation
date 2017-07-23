package com.ttk.abstractdao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ttk.hibernate.HibernateUtil;

public class AbstractDaoImpl<T> implements AbstractDao<T> {

	protected static SessionFactory sessionFactory;

	private Class<T> entityClass;

	protected AbstractDaoImpl() {

		entityClass = ((Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		sessionFactory = HibernateUtil.getSessionFactory();
	}

	@Override
	public List<T> getAllItem() throws Exception {
		Query q = sessionFactory.openSession().createQuery("FROM " + entityClass.getName());
		return q.list();
	}

	@Override
	public T getItemById(int id) throws Exception {
		Query q = sessionFactory.openSession().createQuery("FROM " + entityClass.getName() + " WHERE id = :id");
		q.setLong("id", id);
		return (T) q.uniqueResult();
	}

	@Override
	public void addItem(T t) throws Exception {
		Session session = sessionFactory.openSession();

		session.beginTransaction();
		session.save(t);
		session.getTransaction().commit();

	}

	@Override
	public void updateItem(T t) throws Exception {
		Session session = sessionFactory.openSession();

		session.beginTransaction();
		session.merge(t);
		session.getTransaction().commit();

	}

}

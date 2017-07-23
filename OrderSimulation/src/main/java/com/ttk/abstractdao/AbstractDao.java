package com.ttk.abstractdao;

import java.util.List;

public interface AbstractDao<T> {
	List<T> getAllItem() throws Exception;

	T getItemById(int id) throws Exception;

	void addItem(T t) throws Exception;

	void updateItem(T t) throws Exception;
}

package com.photoshop.repo;

import java.util.List;

public interface DaoContract<T,I> {

	List<T> findAll();
	T findById(I i);
	int create(T t);
	int update(T t);
	int delete(I i);
}

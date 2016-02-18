package com.company.eleave.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao<PK extends Serializable, T> {

	private final Class<T> persistentClass;

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public AbstractDao() {
		this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[1];
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public T getById(Long id) {
		return (T) getSession().get(persistentClass, id);
	}

	public T persist(T entity) {
		getSession().persist(entity);
		
		return entity;
	}

	public void update(T entity) {
		getSession().update(entity);
	}

	@SuppressWarnings("unchecked")
	public void delete(Long id) {
		T byId = getById(id);
		if (byId != null) {
			getSession().delete(byId);
		}
	}

	protected Criteria createEntityCriteria() {
		return getSession().createCriteria(persistentClass);
	}

}

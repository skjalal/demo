package com.utils;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.hibernate.HibernateSessionFactory;

public class HibernateUtils<T> {
	private static Logger log = Logger.getLogger(HibernateUtils.class);
	private T entity;
	private Class<?> cls;
	private Session session;
	private Criteria criteria;
	private Transaction transaction;

	public HibernateUtils(T entity) {
		if (entity == null) {
			throw new NullPointerException();
		}
		this.entity = entity;
		cls = entity.getClass();
		session = HibernateSessionFactory.getSession();
		criteria = session.createCriteria(cls);
	}

	@SuppressWarnings("unchecked")
	public List<T> getEnitityData(Map<String, Object> criterias) {
		log.debug("Enter into Load Data using Criteria...!");
		List<T> entityData = null;
		try {
			for (Map.Entry<String, Object> condition : criterias.entrySet()) {
				criteria.add(Restrictions.eq(condition.getKey(), condition.getValue()));
			}
			entityData = criteria.list();
		} catch (Exception e) {
			log.debug("Error Wihle Fetching Data...!");
			transaction.rollback();
			log.error(e);
		}
		return entityData;
	}

	@SuppressWarnings("unchecked")
	public List<T> getEnitityData(String sql) {
		log.debug("Enter into Load Data using HQL..!");
		List<T> entityData = null;
		try {
			entityData = session.createQuery(sql).list();
		} catch (Exception e) {
			log.debug("Error Wihle Fetching Data...!");
			transaction.rollback();
			log.error(e);
		}
		return entityData;
	}

	@SuppressWarnings("unchecked")
	public T getEnitityById(Integer primaryKey) {
		log.debug("Enter into Getting Table Data based on Primary Key...!");
		try {
			entity = (T) session.get(cls, primaryKey);
		} catch (Exception e) {
			log.debug("Error While Fetching Data...!");
			log.error(e);
		}
		return entity;
	}

	public void save() {
		log.debug("Enetr into Save Data...!");
		try {
			transaction = session.beginTransaction();
			session.save(entity);
			transaction.commit();
		} catch (Exception e) {
			log.debug("Error While Executing Hibernate Insert Query...!");
			transaction.rollback();
			log.error(e);
		}
	}

	public void sqlDMLQuerys(String[] sql) {
		log.debug("Enetr into Update Data...!");
		Integer[] count = new Integer[sql.length];
		Integer index = 0;
		try {
			transaction = session.beginTransaction();
			for(String query : sql) {
				count[index++] = session.createSQLQuery(query).executeUpdate();
			}
			transaction.commit();
		} catch (Exception e) {
			log.debug("Error While Executing Hibernate Update Query...!");
			transaction.rollback();
			log.error(e);
		}
	}

	public void delete(Integer id) {
		log.debug("Enetr into Delete Data...!");
		try {
			transaction = session.beginTransaction();
			entity = getEnitityById(id);
			session.delete(entity);
			transaction.commit();
		} catch (Exception e) {
			log.debug("Error While Executing Hibernate Delete Query...!");
			transaction.rollback();
			log.error(e);
		}
	}

	public void closeHibernateSession() {
		try {
			log.debug("Enter into Close Hibernate Session...!");
			HibernateSessionFactory.closeSession();
			HibernateSessionFactory.closeSessionFactory();
		} catch (Exception e) {
			log.debug(e.getMessage());
			log.error(e);
		}
	}
}
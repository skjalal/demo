package com.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
	private static Logger log = Logger.getLogger(HibernateSessionFactory.class);
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<>();
	private static SessionFactory sessionFactory;

	private HibernateSessionFactory() {
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Session getSession() throws HibernateException {
		log.info("Enter into getSession...!");
		Session session = threadLocal.get();
		if (session == null || session.isOpen()) {
			if (sessionFactory == null) {
				synchronized (HibernateSessionFactory.class) {
					if (sessionFactory == null) {
						rebuildSessionFactory();
					}
				}
			}
			session = (sessionFactory != null) ? sessionFactory.openSession() : null;
			threadLocal.set(session);
		}
		return session;
	}

	public static void rebuildSessionFactory() {
		try {
			sessionFactory = new Configuration().configure().buildSessionFactory();
		} catch (Exception e) {
			log.debug("Error While Creating SessionFactory instance...!");
			log.error(e.getMessage().split(":")[0], e);
		}
	}

	public static void closeSessionFactory() {
		log.info("Enter into Close Hibernate Session Factory...!");
		if (!sessionFactory.isClosed()) {
			sessionFactory.close();
		}
	}

	public static void closeSession() throws HibernateException {
		log.info("Enter into Close Existing Session...!");
		Session session = threadLocal.get();
		threadLocal.set(null);
		if (session != null) {
			session.close();
		}
	}
}
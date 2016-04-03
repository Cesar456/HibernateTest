package com.cesar.test.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import junit.framework.TestCase;

public class Test1 extends TestCase {

	private static Configuration cfg;
	private static SessionFactory sessionFactory;
	private static ThreadLocal<Session> threadLocal;

	/**
	 * 1. threadLocal保证线程安全 2. 加载配置文件 3. 实例化sessionFactory
	 */
	static {
		threadLocal = new ThreadLocal<>();
		cfg = new Configuration().configure();
		sessionFactory = cfg.buildSessionFactory();
	}

	public void test() {
		Session session = getSession();

	}

	private void get(Session session) {

	}

	private Session getSession() {
		Session session = threadLocal.get();
		if (session == null || !session.isOpen()) {
			session = sessionFactory.openSession();
			threadLocal.set(session);
		}
		return session;
	}
}

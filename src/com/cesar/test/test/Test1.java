package com.cesar.test.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.cesar.test.bean.Person;

import junit.framework.TestCase;

/**
 * 该类主要是对hibernate操作的一些基本方法的学习和使用
 * 
 * @author Cesar
 * 
 */
public class Test1 extends TestCase {

	private static Configuration cfg;
	private static SessionFactory sessionFactory;
	private static ThreadLocal<Session> threadLocal;
	/**
	 * 1. threadLocal保证session的线程安全 2. 加载配置文件 3. 实例化sessionFactory
	 */
	static {
		threadLocal = new ThreadLocal<>();
		cfg = new Configuration().configure();
		sessionFactory = cfg.buildSessionFactory();
	}

	public void test() {
		Session session = getSession();
		get(session);
		// load(session);
		// dele(session);
		// update(session);
		// save(session);

	}

	/**
	 * get方法访问数据库适用于开发人员不确定数据库是否有该数据 如果没有该数据返回null 因为get方法是立刻查询数据库
	 * 
	 * @param session
	 */
	private void get(Session session) {
		// 第一个参数是类型，第二个是主键
		Person test = (Person) session.get(Person.class, 5);
		System.out.println(test.toString());
	}

	/**
	 * load 方法对象的代理，只有对象在使用时，才会去查询对象 同时，load还可以加载到指定的类的对象上
	 * 
	 * @return
	 */
	private void load(Session session) {

		// 方法1
		Person test = (Person) session.load(Person.class, new Integer(1));
		System.out.println(test.toString());

		// 方法2
		Person test2 = new Person();
		session.load(test2, new Integer(2));
		System.out.println(test2.toString());
	}

	/**
	 * 利用delete方法删除数据 只有对象在持久化状态时才可以使用
	 * 
	 * @param session
	 */
	private void dele(Session session) {
		Person test = (Person) session.get(Person.class, new Integer(1));
		session.delete(test);
		session.flush(); // flush 立刻执行session中的操作
		System.out.println("数据已经删除");
	}

	/**
	 * update方法修改数据库中数据 这里展示了数据的几种状态，很重要 flush指session执行一些必要sql来把内存中的对象
	 * 同步到数据库，在如下三种情况下数据会flush 1. 某些查询之前 2. 事物提交时 3. 程序中直接调用flush方法时
	 * 
	 * @return
	 */
	private void update(Session session) {
		Person test = (Person) session.get(Person.class, 1);
		test.setAge(23); // 这里在对象中直接修改数据
		session.flush(); // flush之后会直接修改该数据
		System.out.println("数据已经修改");
	}

	private void save(Session session) {
		Person test = new Person();
		test.setAge(21);
		test.setName("kak");

		session.beginTransaction();
		session.save(test);
		session.getTransaction().commit();
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

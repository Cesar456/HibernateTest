package com.cesar.test.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Query;
import org.hibernate.Session;

import com.cesar.test.bean.Person;
import com.cesar.test.dao.HibernateSessionFactory;

/**
 * 该测试类主要是用于对于HQL语句的学习
 * 
 * @author Cesar
 */
public class TestHQL extends TestCase {

	private static Session session = HibernateSessionFactory.getSession();
	private List<Person> persons = new ArrayList<>();

	public void test1() {
		tGroup();
	}

	/**
	 * 使用hql语句进行查询 在hibernate3.0后也可以使用hql进行update和delete 但是官方并不推荐使用
	 */
	private void query() {
		// 排序同样用order by
		String hql = "from Person per order by per.age";
		Query q = session.createQuery(hql);
		persons = q.list();
		for (Person person : persons) {
			System.out.println(person.toString());
		}
	}

	/**
	 * 实现hql的参数绑定机制
	 */
	private void queryByPar() {
		// 通过占位符?
		String hql1 = "from Person per where per.age=?";
		// 通过:parameter 代替具体接口
		String hql2 = "from Person per where per.age=:age";

		Query q1 = session.createQuery(hql1);
		Query q2 = session.createQuery(hql2);

		q1.setParameter(0, 23);
		q2.setParameter("age", 23);

		persons = q1.list();
		for (Person person : persons) {
			System.out.println(person.toString());
		}

		persons = q2.list();
		for (Person person : persons) {
			System.out.println(person.toString());
		}
	}

	/**
	 * 聚合函数的使用
	 * 
	 * @author Cesar
	 */
	private void tfuction() {
		String hql = "select avg(per.age) from Person per";
		Query q = session.createQuery(hql);
		System.out.println(q.list());
	}

	/**
	 * group by 函数 会返回一个object数组的list
	 */
	private void tGroup() {
		String hql = "select per.age, count(per.age) from Person per group by per.age";
		Query query = session.createQuery(hql);
		List<Object[]> s = query.list();
		for (Object[] a : s) {
			System.out.println(a[0] + " " + a[1]);
		}
	}
	// TODO
	// 1. 联合查询 使用类似于sql的join函数
	// 2. 子查询 
	// 详见Java web入门到精通413页

}

package com.cesar.test.dao;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cesar.test.bean.Person;

/**
 * A data access object (DAO) providing persistence and search support for
 * HibernateTest entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.cesar.test.bean.Person
 * @author MyEclipse Persistence Tools
 */
public class HibernateTestDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(HibernateTestDAO.class);
	// property constants
	public static final String NAME = "name";
	public static final String AGE = "age";

	public void save(Person transientInstance) {
		log.debug("saving HibernateTest instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Person persistentInstance) {
		log.debug("deleting HibernateTest instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Person findById(java.lang.Integer id) {
		log.debug("getting HibernateTest instance with id: " + id);
		try {
			Person instance = (Person) getSession().get(
					"com.cesar.per.bean.HibernateTest", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Person instance) {
		log.debug("finding HibernateTest instance by example");
		try {
			List results = getSession()
					.createCriteria("com.cesar.per.bean.HibernateTest")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding HibernateTest instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from HibernateTest as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByAge(Object age) {
		return findByProperty(AGE, age);
	}

	public List findAll() {
		log.debug("finding all HibernateTest instances");
		try {
			String queryString = "from HibernateTest";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Person merge(Person detachedInstance) {
		log.debug("merging HibernateTest instance");
		try {
			Person result = (Person) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Person instance) {
		log.debug("attaching dirty HibernateTest instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Person instance) {
		log.debug("attaching clean HibernateTest instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}
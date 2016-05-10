package com.xabaohui.modules.storage.dao.impl;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xabaohui.modules.storage.dao.StorageIoTaskDao;
import com.xabaohui.modules.storage.entiry.StorageIoTask;

/**
 * A data access object (DAO) providing persistence and search support for
 * StorageIoTask entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xabaohui.modules.storage.entiry.StorageIoTask
 * @author MyEclipse Persistence Tools
 */
public class StorageIoTaskDaoImpl extends HibernateDaoSupport implements
		StorageIoTaskDao {
	private static final Logger log = LoggerFactory
			.getLogger(StorageIoTaskDaoImpl.class);
	// property constants
	public static final String USER_ID = "userId";
	public static final String BIZ_TYPE = "bizType";
	public static final String AMOUNT = "amount";
	public static final String STATUS = "status";
	public static final String MEMO = "memo";
	public static final String VERSION = "version";

	protected void initDao() {
		// do nothing
	}

	public void save(StorageIoTask transientInstance) {
		log.debug("saving StorageIoTask instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StorageIoTask persistentInstance) {
		log.debug("deleting StorageIoTask instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StorageIoTask findById(java.lang.Integer id) {
		log.debug("getting StorageIoTask instance with id: " + id);
		try {
			StorageIoTask instance = (StorageIoTask) getHibernateTemplate().get(StorageIoTask.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<StorageIoTask> findByExample(StorageIoTask instance) {
		log.debug("finding StorageIoTask instance by example");
		try {
			List<StorageIoTask> results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public void update(StorageIoTask instance) {
		log.debug("update StorageIoTask instance by example");
		try {
			getHibernateTemplate().update(instance);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<StorageIoTask> findByCriteria(DetachedCriteria criteria) {
		try {
			return getHibernateTemplate().findByCriteria(criteria);
		} catch (DataAccessException e) {
			throw e;
		}
	}
}
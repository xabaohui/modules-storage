package com.xabaohui.modules.storage.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xabaohui.modules.storage.dao.StorageCheckPlanDao;
import com.xabaohui.modules.storage.entity.StorageCheckPlan;

/**
 * A data access object (DAO) providing persistence and search support for
 * StorageCheckPlan entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xabaohui.modules.storage.storage.entiry.StorageCheckPlan
 * @author MyEclipse Persistence Tools
 */
public class StorageCheckPlanDaoImpl extends HibernateDaoSupport implements
		StorageCheckPlanDao {
	private static final Logger log = LoggerFactory
			.getLogger(StorageCheckPlanDaoImpl.class);
	// property constants
	public static final String USER_ID = "userId";
	public static final String STATUS = "status";
	public static final String POS_COUNT = "posCount";
	public static final String VERSION = "version";
	public static final String MEMO = "memo";

	protected void initDao() {
		// do nothing
	}

	public void save(StorageCheckPlan transientInstance) {
		log.debug("saving StorageCheckPlan instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StorageCheckPlan persistentInstance) {
		log.debug("deleting StorageCheckPlan instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StorageCheckPlan findById(java.lang.Integer id) {
		log.debug("getting StorageCheckPlan instance with id: " + id);
		try {
			StorageCheckPlan instance = (StorageCheckPlan) getHibernateTemplate().get(StorageCheckPlan.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<StorageCheckPlan> findByExample(StorageCheckPlan instance) {
		log.debug("finding StorageCheckPlan instance by example");
		try {
			List<StorageCheckPlan> results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public void update(StorageCheckPlan instance) {
		log.debug("update StorageCheckPlan instance by example");
		try {
			getHibernateTemplate().update(instance);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StorageCheckPlan> findByCriteria(DetachedCriteria criteria) {
		try {
			return getHibernateTemplate().findByCriteria(criteria);
		} catch (DataAccessException e) {
			throw e;
		}
	}
}
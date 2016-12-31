package com.xabaohui.modules.storage.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xabaohui.modules.storage.dao.StorageCheckDao;
import com.xabaohui.modules.storage.entity.StorageCheck;

/**
 * A data access object (DAO) providing persistence and search support for
 * StorageCheck entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xabaohui.modules.storage.entity.StorageCheck
 * @author MyEclipse Persistence Tools
 */
public class StorageCheckDaoImpl extends HibernateDaoSupport implements StorageCheckDao {
	private static final Logger log = LoggerFactory
			.getLogger(StorageCheckDaoImpl.class);
	// property constants
	public static final String USER_ID = "userId";
	public static final String PLAN_ID = "planId";
	public static final String POSITION_ID = "positionId";
	public static final String CHECK_RESULT = "checkResult";
	public static final String VERSION = "version";
	public static final String MEMO = "memo";

	protected void initDao() {
		// do nothing
	}

	public void save(StorageCheck transientInstance) {
		log.debug("saving StorageCheck instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StorageCheck persistentInstance) {
		log.debug("deleting StorageCheck instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StorageCheck findById(java.lang.Integer id) {
		log.debug("getting StorageCheck instance with id: " + id);
		try {
			StorageCheck instance = (StorageCheck) getHibernateTemplate().get(StorageCheck.class, id);
					
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<StorageCheck> findByExample(StorageCheck instance) {
		log.debug("finding StorageCheck instance by example");
		try {
			List<StorageCheck> results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	public void update(StorageCheck instance) {
		log.debug("update StorageCheckDiffAdjust instance by example");
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
	public List<StorageCheck> findByCriteria(DetachedCriteria criteria) {
		try {
			return getHibernateTemplate().findByCriteria(criteria);
		} catch (DataAccessException e) {
			throw e;
		}
	}

}
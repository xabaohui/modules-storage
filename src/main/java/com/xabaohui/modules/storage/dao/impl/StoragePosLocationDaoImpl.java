package com.xabaohui.modules.storage.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xabaohui.modules.storage.dao.StoragePosLocationDao;
import com.xabaohui.modules.storage.entiry.StoragePosLocation;

/**
 * A data access object (DAO) providing persistence and search support for
 * StoragePosLocation entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xabaohui.modules.storage.entiry.StoragePosLocation
 * @author MyEclipse Persistence Tools
 */
public class StoragePosLocationDaoImpl extends HibernateDaoSupport implements
		StoragePosLocationDao {
	private static final Logger log = LoggerFactory
			.getLogger(StoragePosLocationDaoImpl.class);
	// property constants
	public static final String CURRENT_LABLE = "currentLable";
	public static final String PARENT_ID = "parentId";
	public static final String VERSION = "version";
	public static final String MEMO = "memo";

	protected void initDao() {
		// do nothing
	}

	public void save(StoragePosLocation transientInstance) {
		log.debug("saving StoragePosLocation instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StoragePosLocation persistentInstance) {
		log.debug("deleting StoragePosLocation instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StoragePosLocation findById(java.lang.Integer id) {
		log.debug("getting StoragePosLocation instance with id: " + id);
		try {
			StoragePosLocation instance = (StoragePosLocation) getHibernateTemplate().get(StoragePosLocation.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<StoragePosLocation> findByExample(StoragePosLocation instance) {
		log.debug("finding StoragePosLocation instance by example");
		try {
			List<StoragePosLocation> results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "+ results.size());
				
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public void update(StoragePosLocation instance) {
		log.debug("update StoragePosLocation instance by example");
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
	public List<StoragePosLocation> findByCriteria(DetachedCriteria criteria) {
		try {
			return getHibernateTemplate().findByCriteria(criteria);
		} catch (DataAccessException e) {
			throw e;
		}
	}
}
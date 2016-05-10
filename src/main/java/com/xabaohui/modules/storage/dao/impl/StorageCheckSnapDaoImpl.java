package com.xabaohui.modules.storage.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xabaohui.modules.storage.dao.StorageCheckSnapDao;
import com.xabaohui.modules.storage.entiry.StorageCheckSnap;

/**
 * A data access object (DAO) providing persistence and search support for
 * StorageCheckSnap entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xabaohui.modules.storage.entiry.StorageCheckSnap
 * @author MyEclipse Persistence Tools
 */
public class StorageCheckSnapDaoImpl extends HibernateDaoSupport implements StorageCheckSnapDao{
	private static final Logger log = LoggerFactory
			.getLogger(StorageCheckSnapDaoImpl.class);
	// property constants
	public static final String CHECK_ID = "checkId";
	public static final String SKU_ID = "skuId";
	public static final String SNAP_NUM = "snapNum";
	public static final String CHECK_TIME = "checkTime";
	public static final String VERSION = "version";

	protected void initDao() {
		// do nothing
	}

	public void save(StorageCheckSnap transientInstance) {
		log.debug("saving StorageCheckSnap instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	public void delete(StorageCheckSnap persistentInstance) {
		log.debug("deleting StorageCheckSnap instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	public StorageCheckSnap findById(java.lang.Integer id) {
		log.debug("getting StorageCheckSnap instance with id: " + id);
		try {
			StorageCheckSnap instance = (StorageCheckSnap) getHibernateTemplate().get(StorageCheckSnap.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<StorageCheckSnap> findByExample(StorageCheckSnap instance) {
		log.debug("finding StorageCheckSnap instance by example");
		try {
			List<StorageCheckSnap> results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public void update(StorageCheckSnap instance) {
		log.debug("update StorageCheckSnap instance by example");
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
	public List<StorageCheckSnap> findByCriteria(DetachedCriteria criteria) {
		try {
			return getHibernateTemplate().findByCriteria(criteria);
		} catch (DataAccessException e) {
			throw e;
		}
	}

}
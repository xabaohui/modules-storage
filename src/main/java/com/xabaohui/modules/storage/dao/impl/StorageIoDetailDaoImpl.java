package com.xabaohui.modules.storage.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xabaohui.modules.storage.dao.StorageIoDetailDao;
import com.xabaohui.modules.storage.entiry.StorageIoDetail;

/**
 * A data access object (DAO) providing persistence and search support for
 * StorageIoDetail entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xabaohui.modules.storage.entiry.StorageIoDetail
 * @author MyEclipse Persistence Tools
 */
public class StorageIoDetailDaoImpl extends HibernateDaoSupport implements StorageIoDetailDao {
	private static final Logger log = LoggerFactory
			.getLogger(StorageIoDetailDaoImpl.class);
	// property constants
	public static final String SKU_ID = "skuId";
	public static final String AMOUNT = "amount";
	public static final String VERSION = "version";

	protected void initDao() {
		// do nothing
	}

	public void save(StorageIoDetail transientInstance) {
		log.debug("saving StorageIoDetail instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StorageIoDetail persistentInstance) {
		log.debug("deleting StorageIoDetail instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StorageIoDetail findById(java.lang.Integer id) {
		log.debug("getting StorageIoDetail instance with id: " + id);
		try {
			StorageIoDetail instance = (StorageIoDetail) getHibernateTemplate()
					.get(StorageIoDetail.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<StorageIoDetail> findByExample(StorageIoDetail instance) {
		log.debug("finding StorageIoDetail instance by example");
		try {
			List<StorageIoDetail> results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	public void update(StorageIoDetail instance) {
		log.debug("update StorageIoDetail instance by example");
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
	public List<StorageIoDetail> findByCriteria(DetachedCriteria criteria) {
		try {
			return getHibernateTemplate().findByCriteria(criteria);
		} catch (DataAccessException e) {
			throw e;
		}
	}
}
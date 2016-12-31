package com.xabaohui.modules.storage.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xabaohui.modules.storage.dao.StorageCheckDiffAdjustDao;
import com.xabaohui.modules.storage.entity.StorageCheckDiffAdjust;

/**
 * A data access object (DAO) providing persistence and search support for
 * StorageCheckDiffAdjust entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xabaohui.modules.storage.entity.StorageCheckDiffAdjust
 * @author MyEclipse Persistence Tools
 */
public class StorageCheckDiffAdjustDaoImpl extends HibernateDaoSupport
		implements StorageCheckDiffAdjustDao {
	private static final Logger log = LoggerFactory
			.getLogger(StorageCheckDiffAdjustDaoImpl.class);
	// property constants
	public static final String SKU_ID = "skuId";
	public static final String ADJUST_NUM = "adjustNum";
	public static final String OPERATOR = "operator";
	public static final String VERSION = "version";
	public static final String MEMO = "memo";

	protected void initDao() {
		// do nothing
	}

	public void save(StorageCheckDiffAdjust transientInstance) {
		log.debug("saving StorageCheckDiffAdjust instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StorageCheckDiffAdjust persistentInstance) {
		log.debug("deleting StorageCheckDiffAdjust instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StorageCheckDiffAdjust findById(java.lang.Integer id) {
		log.debug("getting StorageCheckDiffAdjust instance with id: " + id);
		try {
			StorageCheckDiffAdjust instance = (StorageCheckDiffAdjust) getHibernateTemplate().get(StorageCheckDiffAdjust.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<StorageCheckDiffAdjust> findByExample(StorageCheckDiffAdjust instance) {
		log.debug("finding StorageCheckDiffAdjust instance by example");
		try {
			List<StorageCheckDiffAdjust> results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public void update(StorageCheckDiffAdjust instance) {
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
	public List<StorageCheckDiffAdjust> findByCriteria(DetachedCriteria criteria) {
		try {
			return getHibernateTemplate().findByCriteria(criteria);
		} catch (DataAccessException e) {
			throw e;
		}
	}
}
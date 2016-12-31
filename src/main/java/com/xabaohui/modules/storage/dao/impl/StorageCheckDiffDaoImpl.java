package com.xabaohui.modules.storage.dao.impl;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xabaohui.modules.storage.dao.StorageCheckDiffDao;
import com.xabaohui.modules.storage.entity.StorageCheckDiff;

/**
 * A data access object (DAO) providing persistence and search support for
 * StorageCheckDiff entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xabaohui.modules.storage.entity.StorageCheckDiff
 * @author MyEclipse Persistence Tools
 */
public class StorageCheckDiffDaoImpl extends HibernateDaoSupport implements
		StorageCheckDiffDao {
	private static final Logger log = LoggerFactory
			.getLogger(StorageCheckDiffDaoImpl.class);
	// property constants
	public static final String SKU_ID = "skuId";
	public static final String CHECK_DIFF_NUM = "checkDiffNum";
	public static final String VERSION = "version";
	protected void initDao() {
		// do nothing
	}

	public void save(StorageCheckDiff transientInstance) {
		log.debug("saving StorageCheckDiff instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StorageCheckDiff persistentInstance) {
		log.debug("deleting StorageCheckDiff instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StorageCheckDiff findById(java.lang.Integer id) {
		log.debug("getting StorageCheckDiff instance with id: " + id);
		try {
			StorageCheckDiff instance = (StorageCheckDiff) getHibernateTemplate().get(StorageCheckDiff.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<StorageCheckDiff> findByExample(StorageCheckDiff instance) {
		log.debug("finding StorageCheckDiff instance by example");
		try {
			List<StorageCheckDiff> results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public void update(StorageCheckDiff instance) {
		log.debug("update StorageCheckDiff instance by example");
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
	public List<StorageCheckDiff> findByCriteria(DetachedCriteria criteria) {
		try {
			return getHibernateTemplate().findByCriteria(criteria);
		} catch (DataAccessException e) {
			throw e;
		}
	}

}
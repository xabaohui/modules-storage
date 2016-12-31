package com.xabaohui.modules.storage.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xabaohui.modules.storage.dao.StoragePosStockDao;
import com.xabaohui.modules.storage.entity.StoragePosStock;

/**
 * A data access object (DAO) providing persistence and search support for
 * StoragePosStock entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xabaohui.modules.storage.entity.StoragePosStock
 * @author MyEclipse Persistence Tools
 */
public class StoragePosStockDaoImpl extends HibernateDaoSupport implements StoragePosStockDao {
	private static final Logger log = LoggerFactory
			.getLogger(StoragePosStockDaoImpl.class);
	// property constants
	public static final String POSITION_ID = "positionId";
	public static final String SKU_ID = "skuId";
	public static final String AMOUNT = "amount";
	public static final String MARK = "mark";
	public static final String VERSION = "version";
	public static final String MEMO = "memo";

	protected void initDao() {
		// do nothing
	}

	public void save(StoragePosStock transientInstance) {
		log.debug("saving StoragePosStock instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StoragePosStock persistentInstance) {
		log.debug("deleting StoragePosStock instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StoragePosStock findById(java.lang.Integer id) {
		log.debug("getting StoragePosStock instance with id: " + id);
		try {
			StoragePosStock instance = (StoragePosStock) getHibernateTemplate().get(StoragePosStock.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	@SuppressWarnings("unchecked")
	public List<StoragePosStock> findByExample(StoragePosStock instance) {
		log.debug("finding StoragePosStock instance by example");
		try {
			List<StoragePosStock> results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public void update(StoragePosStock instance) {
		log.debug("update StoragePosStock instance by example");
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
	public List<StoragePosStock> findByCriteria(DetachedCriteria criteria) {
		try {
			return getHibernateTemplate().findByCriteria(criteria);
		} catch (DataAccessException e) {
			throw e;
		}
	}
}
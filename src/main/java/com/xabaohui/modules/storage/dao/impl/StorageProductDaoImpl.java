package com.xabaohui.modules.storage.dao.impl;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xabaohui.modules.storage.dao.StorageProductDao;
import com.xabaohui.modules.storage.entity.StorageProduct;

/**
 * A data access object (DAO) providing persistence and search support for
 * StorageProduct entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control. 库存
 * 
 * @see com.xabaohui.modules.storage.entity.StorageProduct
 * @author MyEclipse Persistence Tools
 */
public class StorageProductDaoImpl extends HibernateDaoSupport implements
		StorageProductDao {
	private static final Logger log = LoggerFactory
			.getLogger(StorageProductDaoImpl.class);
	// property constants
	public static final String SKU_ID = "skuId";
	public static final String SUBJECT_ID = "subjectId";
	public static final String STOCK_AMT = "stockAmt";
	public static final String STOCK_OCCUPY = "stockOccupy";
	public static final String STOCK_AVAILABE = "stockAvailabe";
	public static final String LOCK_FLAG = "lockFlag";
	public static final String VERSION = "version";

	protected void initDao() {
		// do nothing
	}

	public void save(StorageProduct transientInstance) {
		log.debug("saving StorageProduct instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StorageProduct persistentInstance) {
		log.debug("deleting StorageProduct instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StorageProduct findById(java.lang.Integer id) {
		log.debug("getting StorageProduct instance with id: " + id);
		try {
			StorageProduct instance = (StorageProduct) getHibernateTemplate().get(StorageProduct.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<StorageProduct> findByExample(StorageProduct instance) {
		log.debug("finding StorageProduct instance by example");
		try {
			List<StorageProduct> results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
	@Override
	public void update(StorageProduct instance) {
		log.debug("update StorageProduct instance by example");
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
	public List<StorageProduct> findByCriteria(DetachedCriteria criteria) {
		try {
			return getHibernateTemplate().findByCriteria(criteria);
		} catch (DataAccessException e) {
			throw e;
		}
	}

}
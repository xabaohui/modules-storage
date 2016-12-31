package com.xabaohui.modules.storage.dao.impl;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xabaohui.modules.storage.dao.StorageWarehouseInfoDao;
import com.xabaohui.modules.storage.entity.StorageWarehouseInfo;

/**
 * A data access object (DAO) providing persistence and search support for
 * StorageWarehouseInfo entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xabaohui.modules.storage.entity.StorageWarehouseInfo
 * @author MyEclipse Persistence Tools
 */
public class StorageWarehouseInfoDaoImpl extends HibernateDaoSupport implements StorageWarehouseInfoDao {
	private static final Logger log = LoggerFactory
			.getLogger(StorageWarehouseInfoDaoImpl.class);
	// property constants
	public static final String WAREHOUSE_NAME = "warehouseName";
	public static final String USER_ID = "userId";
	public static final String VERSION = "version";
	public static final String MEMO = "memo";

	protected void initDao() {
		// do nothing
	}

	public void save(StorageWarehouseInfo transientInstance) {
		log.debug("saving StorageWarehouseInfo instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StorageWarehouseInfo persistentInstance) {
		log.debug("deleting StorageWarehouseInfo instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public StorageWarehouseInfo findById(java.lang.Integer id) {
		log.debug("getting StorageWarehouseInfo instance with id: " + id);
		try {
			StorageWarehouseInfo instance = (StorageWarehouseInfo) getHibernateTemplate().get(StorageWarehouseInfo.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<StorageWarehouseInfo> findByExample(StorageWarehouseInfo instance) {
		log.debug("finding StorageWarehouseInfo instance by example");
		try {
			List<StorageWarehouseInfo> results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public void update(StorageWarehouseInfo instanceInfo) {
		log.debug("update StorageWarehouseInfo instance by example");
		try {
			getHibernateTemplate().update(instanceInfo);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StorageWarehouseInfo> findByCriteria(DetachedCriteria criteria) {
		try {
			return getHibernateTemplate().findByCriteria(criteria);
		} catch (DataAccessException e) {
			throw e;
		}
	}
}
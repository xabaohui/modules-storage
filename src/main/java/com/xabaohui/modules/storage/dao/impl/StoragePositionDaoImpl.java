package com.xabaohui.modules.storage.dao.impl;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.xabaohui.modules.storage.dao.StoragePositionDao;
import com.xabaohui.modules.storage.entiry.StoragePosition;

/**
 * A data access object (DAO) providing persistence and search support for
 * StoragePosition entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.xabaohui.modules.storage.entiry.StoragePosition
 * @author MyEclipse Persistence Tools
 */
public class StoragePositionDaoImpl extends HibernateDaoSupport implements StoragePositionDao {
	private static final Logger log = LoggerFactory
			.getLogger(StoragePositionDaoImpl.class);
	// property constants
	public static final String POSITION_LABLE = "positionLable";
	public static final String POSITION_CAPACITY = "positionCapacity";
	public static final String POSITION_ISFULL = "positionIsfull";
	public static final String POSITION_ISLOCK = "positionIslock";
	public static final String VERSION = "version";
	public static final String MEMO = "memo";

	protected void initDao() {
		// do nothing
	}

	public void save(StoragePosition transientInstance) {
		log.debug("saving StoragePosition instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(StoragePosition persistentInstance) {
		log.debug("deleting StoragePosition instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	public StoragePosition findById(java.lang.Integer id) {
		log.debug("getting StoragePosition instance with id: " + id);
		try {
			StoragePosition instance = (StoragePosition) getHibernateTemplate().get(StoragePosition.class, id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<StoragePosition> findByExample(StoragePosition instance) {
		log.debug("finding StoragePosition instance by example");
		try {
			List<StoragePosition> results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public void update(StoragePosition instance) {
		log.debug("update StoragePosition instance by example");
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
	public List<StoragePosition> findByCriteria(DetachedCriteria criteria) {
		try {
			return getHibernateTemplate().findByCriteria(criteria);
		} catch (DataAccessException e) {
			throw e;
		}
	}
}
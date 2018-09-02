package com.spring.crud.api.dao.impl;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import com.spring.crud.api.dao.StateDAO;
import com.spring.crud.api.model.State;

@Repository("stateDao")
@Transactional
public class StateDAOImpl implements StateDAO {
	

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public State addState(State state) {
		sessionFactory.getCurrentSession().save(state);
		return state;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public State getStateByCode(String code) {
		try {
			Query query = sessionFactory.getCurrentSession().createNamedQuery("State.findByCode");
			query.setParameter("code", code);
			State state = (State)query.getSingleResult();
			return state;
		} catch (Exception e) {
			throw new RestClientException("State with code " +code+" doesn't exist");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<State> getAllStates() {
		try {
			Query query = sessionFactory.getCurrentSession().createNamedQuery("State.findAll");
			List<State> stateList = query.getResultList();
			return stateList;
		} catch (Exception e) {
			throw new RestClientException("No state exist in db");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteState(State state) {
		sessionFactory.getCurrentSession().remove(state);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public State updateState(State state) {
		sessionFactory.getCurrentSession().update(state);
		return state;
	}

	@Override
	public State getStateByID(Long id) {
		try {
			Query query = sessionFactory.getCurrentSession().createNamedQuery("State.findByID");
			query.setParameter("id", id);
			State state = (State)query.getSingleResult();
			return state;
		} catch (Exception e) {
			throw new RestClientException("State with id " +id+" doesn't exist");
		}
	}

}

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

import com.spring.crud.api.dao.CityDAO;
import com.spring.crud.api.model.City;

@Repository("cityDao")
@Transactional
public class CityDAOImpl implements CityDAO {
	
	
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
	public City addCity(City city) {
		sessionFactory.getCurrentSession().save(city);
		return city;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public City getCityByName(String name) {
		try {
			Query query =sessionFactory.getCurrentSession().createNamedQuery("City.findByName");
			query.setParameter("name", name);
			City city = (City)query.getSingleResult();
			return city;
		} catch (Exception e) {
			throw new RestClientException("City with name " +name+" doesn't exist");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public City updateCity(City city) {
		sessionFactory.getCurrentSession().update(city);
		return city;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<City> getAllCities() {
		try {
			Query query = sessionFactory.getCurrentSession().createNamedQuery("City.findAll");
			List<City> cityList = query.getResultList();
			return cityList;
		} catch (Exception e) {
			throw new RestClientException("There is no city information in DB");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteCity(City city) {
		sessionFactory.getCurrentSession().remove(city);
	}
}
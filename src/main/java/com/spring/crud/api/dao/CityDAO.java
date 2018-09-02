package com.spring.crud.api.dao;

import java.util.List;

import com.spring.crud.api.model.City;

public interface CityDAO {

	public City addCity(City city);
	
	public City getCityByName(String name);
	
	public City updateCity(City city);
	
	public List<City> getAllCities();
	
	public void deleteCity(City city); 
}

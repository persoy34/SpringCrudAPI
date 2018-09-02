package com.spring.crud.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientResponseException;

import com.spring.crud.api.dao.CityDAO;
import com.spring.crud.api.dao.StateDAO;
import com.spring.crud.api.model.City;
import com.spring.crud.api.model.State;

@RestController
@RequestMapping("/city")
public class CityController {

	@Autowired
	CityDAO cityDAO;

	@Autowired
	StateDAO stateDao;

	@GetMapping("/{cityName}")
	ResponseEntity<City> getCityByByName(@PathVariable(value = "cityName") String cityName) {
		City city = cityDAO.getCityByName(cityName);
		if (city != null) {
			return new ResponseEntity<>(city, HttpStatus.OK);
		}
		throw new RestClientResponseException("City with name:" + cityName + " isn't exist",
				HttpStatus.NOT_FOUND.value(), "City isn't exist", null, null, null);
	}

	@PutMapping("/add-city")
	ResponseEntity<City> addCity(@RequestBody City city) {
		try {
			if (city.getState() != null) {
				State state = city.getState();
				if (state.getId() != null) {
					Long stateID = city.getState().getId();
					state = stateDao.getStateByID(stateID);
				} else if (state.getStateCode() != null) {
					state = stateDao.getStateByCode(state.getStateCode());
				}
				city.setState(state);
			}

			return new ResponseEntity<City>(cityDAO.addCity(city), HttpStatus.OK);
		} catch (Exception e) {
			throw new RestClientResponseException(e.getMessage(), HttpStatus.NOT_FOUND.value(), "", null, null, null);
		}
	}

	@GetMapping()
	ResponseEntity<List<City>> getAllCities() {
		List<City> cityList = cityDAO.getAllCities();
		return new ResponseEntity<List<City>>(cityList, HttpStatus.OK);
	}

	@PostMapping("update-city")
	ResponseEntity<City> updateCity(@RequestBody City city) {
		try {
			if (city.getState() != null) {
				State state = city.getState();
				if (state.getId() != null) {
					Long stateID = city.getState().getId();
					state = stateDao.getStateByID(stateID);
				} else if (state.getStateCode() != null) {
					state = stateDao.getStateByCode(state.getStateCode());
				}
				city.setState(state);
			}
			city = cityDAO.updateCity(city);
			return new ResponseEntity<City>(city, HttpStatus.OK);
		} catch (Exception e) {
			throw new RestClientResponseException(e.getMessage(), HttpStatus.NOT_FOUND.value(), "", null, null, null);
		}
	}
}
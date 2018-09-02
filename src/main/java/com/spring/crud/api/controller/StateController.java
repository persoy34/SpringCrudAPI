package com.spring.crud.api.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/state")
public class StateController {

	@Autowired
	StateDAO stateDAO;

	@Autowired
	CityDAO cityDAO;

	@GetMapping("/{stateCode}")
	ResponseEntity<State> getStateByCode(@PathVariable(value = "sateCode") String stateCode) {
		State state = stateDAO.getStateByCode(stateCode);
		if (state != null) {
			return new ResponseEntity<>(state, HttpStatus.OK);
		}
		throw new RestClientResponseException("State with code:" + stateCode + " isn't exist",
				HttpStatus.NOT_FOUND.value(), "State isn't exist", null, null, null);
	}

	@GetMapping()
	ResponseEntity<List<State>> getAllStates() {
		List<State> stateList = stateDAO.getAllStates();
		return new ResponseEntity<List<State>>(stateList, HttpStatus.OK);
	}

	@PostMapping(value = "/update-state")
	ResponseEntity<State> updateState(@RequestBody State state) {
		try {
			State existingState = null;
			if (state.getId() != null ) {
				existingState = stateDAO.getStateByID(state.getId());
			} else {
				existingState = stateDAO.getStateByCode(state.getStateCode());
			}
			state.setId(existingState.getId());
			state = stateDAO.updateState(state);
		} catch (Exception e) {
			state = stateDAO.addState(state);
		}
		return new ResponseEntity<State>(state, HttpStatus.OK);
	}

	@PutMapping(value = "/add-state")
	ResponseEntity<State> addState(@RequestBody State state) {
		state = stateDAO.addState(state);
		return new ResponseEntity<State>(state, HttpStatus.OK);
	}

	@DeleteMapping(value = "/delete-state")
	ResponseEntity<?> deleteState(@RequestBody State state) {
		try {
			if (state.getId() != null && state.getId() != 0) {
				state = stateDAO.getStateByID(state.getId());
			} else {
				state = stateDAO.getStateByCode(state.getStateCode());
			}
			stateDAO.deleteState(state);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			throw new RestClientResponseException("State with code:" + state.getStateCode() + " isn't exist",
					HttpStatus.NOT_FOUND.value(), "State isn't exist", null, null, null);
		}
	}

}

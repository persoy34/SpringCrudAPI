package com.spring.crud.api.dao;

import java.util.List;

import com.spring.crud.api.model.State;

public interface StateDAO {

	public State addState(State state);
	
	public State getStateByCode(String code);
	
	public State getStateByID(Long id);
	
	public List<State> getAllStates();
	
	public void deleteState(State state);
	
	public State updateState(State state);
}

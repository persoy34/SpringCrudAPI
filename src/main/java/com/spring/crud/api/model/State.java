package com.spring.crud.api.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity 
@Table(name="state")
@NamedQueries({
    @NamedQuery(name = "State.findAll", query = "SELECT s FROM State s"),
    @NamedQuery(name = "State.findByCode", query = "SELECT s FROM State s WHERE s.stateCode =:code"),
    @NamedQuery(name = "State.findByID", query = "Select s FROM State s where s.id =: id")
    })
public class State implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "state_id")
	private Long id;
	
	@NotNull
	@Column(unique = true, name ="name")
	private String stateName;
	
	@NotNull
	@Column(unique = true, name="code")
	private String stateCode;
	
	@JsonIgnore
	@OneToMany(mappedBy="state", fetch = FetchType.EAGER)
	Set<City> citySet = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public Set<City> getCitySet() {
		return citySet;
	}

	public void setCitySet(Set<City> citySet) {
		this.citySet = citySet;
	}
	
	

}

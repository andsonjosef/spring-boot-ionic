package com.example.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.example.cursomc.domain.State;

public class StateDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message="Required Field")
	@Length(min=5, max = 120, message="The size must to be between 5 and 120 characters")
	private String name;
	
	
	
	public StateDTO() {
		
	}
	
	public  StateDTO(State obj){
		id = obj.getId();
		name = obj.getName();
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}

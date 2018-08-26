package com.example.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cursomc.domain.State;
import com.example.cursomc.repositories.StateRepository;

@Service
public class StateService {

	@Autowired
	private StateRepository repo;

	public List<State> findAll() {
		return repo.findAllByOrderByName();
	}

	

}

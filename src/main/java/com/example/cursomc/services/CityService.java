package com.example.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cursomc.domain.City;
import com.example.cursomc.repositories.CityRepository;

@Service
public class CityService {

	@Autowired
	private CityRepository repo;

	public List<City> findByState(Integer stateId) {
		return repo.findCities(stateId);
	}
}

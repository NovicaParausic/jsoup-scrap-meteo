package com.scrap.service;

import java.util.List;

import com.scrap.entity.Airport;

import io.github.mivek.model.Metar;

public interface ActiveService {

	List<Airport> getActiveAirports();
	
	String saveToActive(Airport airport);
	
	List<Metar> fetchMetarsFromCart();
	
	Metar getMetar(String code);
	
	void removeFromActive(String code);
	
}

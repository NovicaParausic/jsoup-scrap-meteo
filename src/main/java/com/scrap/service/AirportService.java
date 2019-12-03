package com.scrap.service;

import java.util.List;

import com.scrap.entity.Airport;

public interface AirportService {

	List<Airport> getAirports();
	
	Airport getAirport(String code);
}

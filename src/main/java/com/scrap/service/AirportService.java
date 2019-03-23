package com.scrap.service;

import org.springframework.data.domain.Page;

import com.scrap.dto.AirportDto;
import com.scrap.entity.Airport;

public interface AirportService {

	Page<Airport> getAirports(int page);
	
	Airport getAirport(String code);
	
	String getUrlFromCode(String code);
	
	Airport save(AirportDto airport);
	
	void delete(String code);
}

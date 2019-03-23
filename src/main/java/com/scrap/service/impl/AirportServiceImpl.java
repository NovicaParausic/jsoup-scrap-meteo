package com.scrap.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.scrap.dto.AirportDto;
import com.scrap.entity.Airport;
import com.scrap.repository.AirportRepository;
import com.scrap.service.AirportService;

@Service
public class AirportServiceImpl implements AirportService {

	@Autowired
	private AirportRepository airportRepository;
	
	@Override
	public Page<Airport> getAirports(int page) {
		return airportRepository.findAll(PageRequest.of(page, 10));
	}

	@Override
	public Airport getAirport(String code) {
		return airportRepository.findById(code).orElse(null);
	}

	@Override
	public Airport save(AirportDto newAirport) {
		Airport airport = new Airport();
		
		airport.setCode(newAirport.getCode());
		airport.setName(newAirport.getName());
		airport.setCity(newAirport.getCity());
		airport.setState(newAirport.getState());
		airport.setLatitude(newAirport.getLatitude());
		airport.setLongitude(newAirport.getLongitude());
		airport.setElevation(newAirport.getElevation());
		airport.setUrl(newAirport.getUrl());
		
		return airportRepository.save(airport);
	}

	@Override
	public void delete(String code) {
		airportRepository.deleteById(code);
	}

	@Override
	public String getUrlFromCode(String code) {
		Airport airport =airportRepository.findById(code).orElse(null);
		return airport.getUrl();
	}

}

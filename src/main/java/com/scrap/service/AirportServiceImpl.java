package com.scrap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scrap.entity.Airport;
import com.scrap.repository.AirportRepository;
import com.scrap.service.AirportService;

@Service
public class AirportServiceImpl implements AirportService {

	@Autowired
	private AirportRepository airportRepository;
	
	@Override
	public List<Airport> getAirports() {
		return airportRepository.findAll();
	}

	@Override
	public Airport getAirport(String code) {
		return airportRepository.findById(code).orElse(null);
	}
}

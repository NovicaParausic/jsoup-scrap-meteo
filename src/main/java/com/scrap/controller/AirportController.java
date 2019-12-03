package com.scrap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scrap.entity.Airport;
import com.scrap.service.AirportService;

@RestController
@RequestMapping(value = "/api/scrap")
public class AirportController {

	@Autowired
	private AirportService airportService;
	
	@GetMapping
	public ResponseEntity<List<Airport>> getAirports() {
		
		List<Airport> ret = airportService.getAirports();
		return ResponseEntity.ok(ret);
	}
}

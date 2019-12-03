package com.scrap.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scrap.entity.Airport;
import com.scrap.service.ActiveService;
import com.scrap.service.AirportService;

import io.github.mivek.model.Metar;

@RestController
@RequestMapping(value = "/api/active")
public class ActiveController {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ActiveService activeService;
	
	@Autowired
	private AirportService airportService;
	
	
	@GetMapping
	public ResponseEntity<List<Airport>> getActiveAirports(HttpServletRequest request) { 
		
		HttpSession session = request.getSession();
		log.info("SessionId: " + session.getId());
		
		List<Airport> ret = activeService.getActiveAirports();
		if(ret == null) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.ok(ret);
		}
	}
	
	@PostMapping
	public ResponseEntity<Airport> addToCart(HttpServletRequest request, @RequestBody String code) {
		
		Airport airport = airportService.getAirport(code);
		
		if(airport == null) {
			System.out.println("ActiveController add to Cart airport NULLL");
			return ResponseEntity.badRequest().build();
		} else {
			activeService.saveToActive(airport);
			log.info("Active Controller add to cart: " + code);
			return ResponseEntity.ok(airport);
		}
	}
	
	@GetMapping(value = "/metarsFromCart")
	public ResponseEntity<List<Metar>> getMetarsFromCart() { 
		
		List<Metar> ret = activeService.fetchMetarsFromCart();
		if(ret == null) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.ok(ret);
		}
	}
	
	@PostMapping(value = "/metarFromCode/{code}")
	public ResponseEntity<Metar> translateMetar(@PathVariable String code) {
		
		Airport airport = airportService.getAirport(code);
		 
		if (airport == null) {
			return ResponseEntity.badRequest().build();
		} else {
			Metar metar = activeService.getMetar(code);
			return ResponseEntity.ok(metar);
		}
	}
	
	@DeleteMapping(value = "/{code}")
	public ResponseEntity<Airport> deleteAirportFormCart(@PathVariable String code) {
		log.info(code);
		Airport airport = airportService.getAirport(code);
		 
		if (airport == null) {
			log.warn(code);
			return ResponseEntity.badRequest().build();
		} else {
			log.info(code);
			activeService.removeFromActive(code);
			return ResponseEntity.ok(airport);
		}
	}
}

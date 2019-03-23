package com.scrap.controller;

import java.util.List;

import org.jsoup.nodes.Element;
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
import com.scrap.util.MetarTafUtil;

import io.github.mivek.model.Metar;
import io.github.mivek.model.TAF;

@RestController
@RequestMapping(value = "/api/active")
public class ActiveController {

	@Autowired
	private ActiveService activeService;
	
	@Autowired
	private AirportService airportService;
	
	
	@GetMapping
	public ResponseEntity<List<Airport>> getActiveAirports() { 
		List<Airport> ret = activeService.getActiveAirports();
		
		if(ret == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(ret);
		}
	}
	
	@PostMapping
	public ResponseEntity<Airport> addToCart(@RequestBody String code) {
		Airport airport = airportService.getAirport(code);
		
		if(airport == null) {
			return ResponseEntity.badRequest().build();
		} else {
			activeService.saveToActive(airport);
			return ResponseEntity.ok(airport);
		}
	}
	
	@GetMapping(value = "/metarsFromCart")
	public ResponseEntity<List<Metar>> getMetarsFromCart() { 
		List<Metar> ret = activeService.fetchMetars();
		
		if(ret == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(ret);
		}
	}
	
	@PostMapping(value = "/metarFromCode/{code}")
	public ResponseEntity<Metar> getMetarFromCode(@PathVariable String code) {
		String url = airportService.getUrlFromCode(code);
		Element element = activeService.fetchMetarFromUrl(url);
		
		Metar metar = MetarTafUtil.decodeMetarFromString(element.text().substring(7)); 
		
		if(metar == null) {
			return ResponseEntity.badRequest().build();
		} else {
			return ResponseEntity.ok(metar);
		}
	}
	
	@PostMapping(value = "/decodeMetar")
	public ResponseEntity<Metar> decodeMetar(@RequestBody String code) {
		Metar metar = MetarTafUtil.decodeMetarFromString(code); 
		
		if(metar == null) {
			return ResponseEntity.badRequest().build();
		} else {
			return ResponseEntity.ok(metar);
		}
	}
	
	@PostMapping(value = "/decodeTaf")
	public ResponseEntity<TAF> decodeTaf(@RequestBody String code) {
		TAF taf = MetarTafUtil.decodeTafFromString(code); 
		
		if(taf == null) {
			return ResponseEntity.badRequest().build();
		} else {
			return ResponseEntity.ok(taf);
		}
	}
	
	@DeleteMapping
	public ResponseEntity<Airport> removeFromCart(@RequestBody String code) {
		Airport airport = airportService.getAirport(code);
		
		if(airport == null) {
			return ResponseEntity.badRequest().build();
		} else {
			activeService.removeFromActive(code);
			return ResponseEntity.ok(airport);
		}
	}
	
	@DeleteMapping(value = "/clear")
	public ResponseEntity<Airport> clearCart() {
		activeService.clear();
		return ResponseEntity.ok().build();
		
	}
}

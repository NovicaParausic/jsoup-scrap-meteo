package com.scrap.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scrap.dto.AirportDto;
import com.scrap.entity.Airport;
import com.scrap.service.AirportService;
import com.scrap.service.MetarService;

@RestController
@RequestMapping(value = "/api/scrap")
public class MetarController {

	private final Log logger = LogFactory.getLog(this.getClass());
	
	@Autowired
	private MetarService metarService;
	
	@Autowired
	private AirportService airportService;
	
	@GetMapping(value = "/info")
	public List<String> fetchBg() {
		return metarService.fetchMetarBg();
	}
	
	@GetMapping
	public ResponseEntity<Page<Airport>> getAirports(@RequestParam(value = "page", defaultValue = "0") int page) {
		Page<Airport> ret = airportService.getAirports(page);

		if(ret == null) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(ret);
		}
	}
	
	@PostMapping
	public ResponseEntity<String> saveAirport(@RequestBody final @Valid AirportDto newAirport, BindingResult result) {
		//Airport airport = airportService.getAirport(newAirport.getCode());
		
		if(result.hasErrors()) {
			return ResponseEntity.badRequest().build();
		} else {
			airportService.save(newAirport);
			return ResponseEntity.ok().build();
		}
	}
	
}

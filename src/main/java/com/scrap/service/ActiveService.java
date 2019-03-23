package com.scrap.service;

import java.util.List;

import org.jsoup.nodes.Element;

import com.scrap.entity.Airport;

import io.github.mivek.model.Metar;

public interface ActiveService {

	List<Airport> getActiveAirports();
	
	List<Metar> fetchMetars();
	
	Element fetchMetarFromUrl(String url);
	
	Element fetchMetarAmsterdam();
	
	Airport saveToActive(Airport airport);
	
	void removeFromActive(String code);
	
	boolean isActive(String code);
	
	void clear();
}

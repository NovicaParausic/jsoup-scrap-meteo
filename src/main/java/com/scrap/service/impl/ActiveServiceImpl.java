package com.scrap.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.scrap.entity.Airport;
import com.scrap.service.ActiveService;
import com.scrap.util.MetarTafUtil;

import io.github.mivek.model.Metar;

@Service
//@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ActiveServiceImpl implements ActiveService {

	
	Map<String, Airport> cart = new HashMap<>();
	
	@Override
	public Airport saveToActive(Airport airport) {
		if(!cart.containsKey(airport.getCode())) {
			cart.put(airport.getCode(), airport);
			return airport;
		}
		return null;
	}

	@Override
	public void removeFromActive(String code) {
		cart.remove(code);
	}

	@Override
	public boolean isActive(String code) {
		return cart.containsKey(code);
	}

	@Override
	public void clear() {
		cart.clear();
	}

	@Override
	public List<Airport> getActiveAirports() {
		return new ArrayList<>(cart.values());
	}

	@Override
	public List<Metar> fetchMetars() {
		List<Metar> ret = new ArrayList<>();
		List<Airport> airports = getActiveAirports();
		
		for(Airport airport : airports){
			Element element = fetchMetarFromUrl(airport.getUrl());
			
			Metar metar = MetarTafUtil.decodeMetarFromElement(element);
			ret.add(metar);
		}
		
		return ret;
	}

	@Override
	public Element fetchMetarFromUrl(String url) {
		System.setProperty("javax.net.ssl.trustStore", "C:/UOOP/Scrap/jsoup-scrap-meteo/allmetsatcom.jks");
		
		Document document = null; 
		 
		try {
			document = Jsoup.connect(url).get();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Elements meteos = document.select("div.c1b");
		
		meteos = meteos.select("p");
		
		Element ret = meteos.get(2);
		
		return ret;
	}
	
	@Override
	public Element fetchMetarAmsterdam() {
		System.setProperty("javax.net.ssl.trustStore", "C:/UOOP/Scrap/jsoup-scrap-meteo/allmetsatcom.jks");
		
		Document document = null; 
		 
		try {
			document = Jsoup.connect("https://en.allmetsat.com/metar-taf/europe.php?icao=EHAM").get();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Elements meteos = document.select("div.c1b");
		
		meteos = meteos.select("p");
		
		Element ret = meteos.get(2);
		
		return ret;
	}
}

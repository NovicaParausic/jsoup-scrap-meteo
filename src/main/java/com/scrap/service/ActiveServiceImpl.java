package com.scrap.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.scrap.component.ActiveComponent;
import com.scrap.entity.Airport;
import com.scrap.entity.MetarEntity;
import com.scrap.repository.MetarEntityRepository;
import com.scrap.service.ActiveService;
import com.scrap.util.MetarUtil;

import io.github.mivek.model.Metar;

@Service
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ActiveServiceImpl implements ActiveService {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MetarEntityRepository meRepo;
	
	@Autowired
	private ActiveComponent activeComponent;
	
	private Map<String, Airport> cart = new HashMap<>();
	
	@Override
	public String saveToActive(Airport airport) {
		
		String code = airport.getCode();
		
		cart.put(code, airport);
		
		activeComponent.save(airport);
		
		return null;
	}

	@Override
	public List<Airport> getActiveAirports() {

		List<Airport> ret = new ArrayList<>(cart.values());
		log.info("ASI get active airports");
		for(Airport port : ret) {
			log.info(port.getCode());
		}
		
		return ret;
	}

	@Override
	public List<Metar> fetchMetarsFromCart() {
		
		List<Airport> activeAirport = getActiveAirports();
		List<Metar> ret = new ArrayList<>();
		
		for (Airport airport : activeAirport) {
			MetarEntity me = meRepo.findById(airport.getCode()).orElse(null);
			log.info(me.getCode());
			Metar metar = MetarUtil.decodeMetarFromString(me.getMetar());
			ret.add(metar);
		}
		return ret;
	}
	
	@Override
	public Metar getMetar(String code) {
		MetarEntity me = meRepo.findById(code).orElse(null);
		Metar metar = MetarUtil.decodeMetarFromString(me.getMetar());
		return metar;
	}
	
	@Override
	public void removeFromActive(String code) {
		cart.remove(code);
		activeComponent.deleteJob(code);
	}
	
	@PreDestroy
	private void clearCart() {
		cart.clear();
		activeComponent.clearScheduler();
	}
}

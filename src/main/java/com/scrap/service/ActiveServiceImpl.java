package com.scrap.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PreDestroy;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.scrap.component.ActiveComponent;
import com.scrap.entity.Airport;
import com.scrap.entity.MetarEntity;
import com.scrap.entity.UserIcao;
import com.scrap.repository.MetarEntityRepository;
import com.scrap.repository.UserIcaoRepository;
import com.scrap.util.MetarUtil;

import io.github.mivek.model.Metar;

@Service
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ActiveServiceImpl implements ActiveService {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MetarEntityRepository meRepo;
	
	@Autowired
	private UserIcaoRepository uiRepo;
	
	@Autowired
	private ActiveComponent activeComponent;
	
	private Map<String, Airport> cart = new HashMap<>();
	
	private String sessionId;
	
	@Async("outer")
	@Transactional
	@Override
	public void saveToActive(String sessionId, Airport airport) {
		
		this.sessionId = sessionId;
		log.info(sessionId);
		
		String code = airport.getCode();
		cart.put(code, airport);
		activeComponent.save(airport);
	}

	@Override
	public List<Airport> getActiveAirports() {

		List<Airport> ret = new ArrayList<>(cart.values());
		log.info("ASI get active airports");
		cart.forEach((k, v) -> log.info(k));
		
		
		return ret;
	}

	@Override
	public List<Metar> fetchMetarsFromCart() {
		
		List<Metar> ret = new ArrayList<>();
		
		cart.forEach((k, v) -> {
			MetarEntity me = meRepo.findById(v.getCode()).orElse(null);
			log.info(me.getCode());
			Metar metar = MetarUtil.decodeMetarFromString(me.getMetar());
			ret.add(metar);
		
		});
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
	@Transactional
	private void clearCart() {
		cart.forEach((code, airport) -> {
			List<UserIcao> list = uiRepo.findByIcao(code);
			log.info("ui list size: " + list.size());
			
			if (list.size() < 2) {
				activeComponent.deleteJob(code);
			}
			activeComponent.deleteByUserAndIcao(sessionId, code);
		});
		
	}
}

package com.scrap.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scrap.entity.Airport;
import com.scrap.service.ActiveService;
import com.scrap.service.MetarService;

@Component
public class FetchUtil {

	@Autowired
	private MetarService metarService;
	
	@Autowired
	private ActiveService activeService;
	
	public void fetchInfo(){
		List<Airport> activePorts = activeService.getActiveAirports();
		
		for(Airport port : activePorts){
			//Metar metar = metarService.fetch(port.getCode());
		}
	}
}

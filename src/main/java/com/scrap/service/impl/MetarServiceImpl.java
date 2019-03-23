package com.scrap.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.scrap.service.MetarService;

@Service
public class MetarServiceImpl implements MetarService {

	@Override
	public List<String> fetch(String url) {
		//https://stackoverflow.com/questions/7744075/how-to-connect-via-https-using-jsoup
		System.setProperty("javax.net.ssl.trustStore", "C:/UOOP/allmetsatcom.jks");
		
		List<String> ret = new ArrayList<>();
		
		Document document = null; 
		 
		try {
			document = Jsoup.connect(url).get();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Elements meteos = document.select("div.c1b");
		
		
		
		for(Element meteo : meteos){
			ret.add(meteo.text());
		}
		
		return ret;
	}

	@Override
	public  Element fetchInfo() {
		System.setProperty("javax.net.ssl.trustStore", "C:/UOOP/allmetsatcom.jks");
		
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

	@Override
	public List<String> fetchMetarBg() {
		System.setProperty("javax.net.ssl.trustStore", "C:/UOOP/allmetsatcom.jks");
		
		List<String> ret = new ArrayList<>();
		
		Document document = null; 
		 
		try {
			document = Jsoup.connect("https://en.allmetsat.com/metar-taf/europe.php?icao=EHAM").get();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Elements meteos = document.select("div.c1b");
		
		meteos = meteos.select("p");
		
		for(Element meteo : meteos){
			ret.add(meteo.text());
		}
		
		return ret;	
	}

	@Override
	public List<String> fetchTafBg() {
		System.setProperty("javax.net.ssl.trustStore", "C:/UOOP/allmetsatcom.jks");
		
		List<String> ret = new ArrayList<>();
		
		Document document = null; 
		 
		try {
			document = Jsoup.connect("https://en.allmetsat.com/metar-taf/europe.php?icao=EHAM").get();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Elements tafs = document.select("div.c1");
		
		tafs = tafs.select("p");
		
		for(Element taf : tafs){
			ret.add(taf.text());
		}
		
		return ret;		}

	
}

package com.scrap.util;

import java.util.concurrent.CompletableFuture;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;

import io.github.mivek.exception.ParseException;
import io.github.mivek.facade.MetarFacade;
import io.github.mivek.model.Metar;

public class MetarUtil {

	public static String getRawMetarFromUrl(String url) {
		
		Document document = null;
		
		try {
			document = Jsoup.connect(url).get();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Elements elements = document.select("div.c1b");
		elements = elements.select("p");
		Element element = elements.get(2);
		
		final String raw = element.text().substring(7);
		return raw;
	}
		
	public static Metar decodeMetarFromString(String raw) {
		
		Metar metar = new Metar();
		
		MetarFacade facade = MetarFacade.getInstance();
		try {
			metar = facade.decode(raw);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return metar;
	}
	
	public static Metar getMetarFromUrl(String url) {
		
		String raw = getRawMetarFromUrl(url);
		return decodeMetarFromString(raw);
	}
	
	@Async
	public static CompletableFuture<Metar> getFutureMetarFromUrl(String url) {
		
		System.out.println(url);
		
		Document document = null;
		
		try {
			document = Jsoup.connect(url).get();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Elements elements = document.select("div.c1b");
		elements = elements.select("p");
		
		Element element =  elements.get(2);
		
		Metar metar = decodeMetarFromElement(element);
		
		return CompletableFuture.completedFuture(metar);
	}
	
	public static Metar decodeMetarFromElement(Element element){
		Metar metar = new Metar();
		final String code = element.text().substring(7);
		
		MetarFacade facade = MetarFacade.getInstance();
		try{
			metar = facade.decode(code);
		} catch (ParseException e){
			e.printStackTrace();
		}
		return metar;
	}
}

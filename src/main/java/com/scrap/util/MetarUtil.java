package com.scrap.util;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mivek.facade.MetarFacade;
import io.github.mivek.model.Metar;

public class MetarUtil {

	public static final Logger log = LoggerFactory.getLogger(MetarUtil.class);
	
	public static CompletableFuture<String> getRawMetarFromUrl(String url) {
		
		CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
			
			@Override
			public String get() {
				
				String raw = null;
				try {
					Document document = null;
					
					try {
						document = Jsoup.connect(url).get();
					} catch(Exception e) {
						e.printStackTrace();
					}
					
					Elements elements = document.select("div.c1b");
					elements = elements.select("p");
					Element element = elements.get(2);
					
					raw = element.text().substring(7);
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
				return raw;
			}
		});
		
		return future;
		
	}
		

	public static Metar decodeMetarFromString(String raw) {
		
		Metar metar = new Metar();
		
		//String ode = Arrays.asList(raw.split(" ")).get(0);
		
		MetarFacade facade = MetarFacade.getInstance();
		try {
			metar = facade.decode(raw);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return metar;
	}
	
}


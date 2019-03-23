package com.scrap.util;

import org.jsoup.nodes.Element;

import io.github.mivek.exception.ParseException;
import io.github.mivek.facade.MetarFacade;
import io.github.mivek.facade.TAFFacade;
import io.github.mivek.model.Metar;
import io.github.mivek.model.TAF;

public class MetarTafUtil {

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
	
	public static Metar decodeMetarFromString(String raw){
		Metar metar = new Metar();
		
		MetarFacade facade = MetarFacade.getInstance();
		try{
			metar = facade.decode(raw);
		} catch (ParseException e){
			e.printStackTrace();
		}
		return metar;
	}
	
	public static TAF decodeTafFromElement(Element element){
		TAF taf= new TAF();
		final String code = element.text().substring(7);
		
		TAFFacade facade = TAFFacade.getInstance();
		try{
			taf = facade.decode(code);
		} catch (ParseException e){
			e.printStackTrace();
		}
		return taf;
	}
	
	public static TAF decodeTafFromString(String raw){
		TAF taf= new TAF();
		TAFFacade facade = TAFFacade.getInstance();
		try {
			taf = facade.decode(raw);
		} catch (ParseException e){
			e.printStackTrace();
		}
		return taf;
	}
}

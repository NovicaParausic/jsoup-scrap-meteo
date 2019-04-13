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
		final String code = element.text().substring(5);
		System.out.println(code);
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
	
	public static TAF decodeTaf(){
		String raw = "TAF EHAM 260505Z 2606/2712 30008KT 9999 SCT020 BKN045 \n"
				+ "BECMG 2607/2610 30013KT \n"
				+ "BECMG 2616/2619 32005KT PROB30 2622/2707 VRB03KT 2000 BR MIFG NSC \n"
				+ "BECMG 2709/2712 29010KT";
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

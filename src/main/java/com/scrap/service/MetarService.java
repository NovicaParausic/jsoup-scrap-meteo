package com.scrap.service;

import java.util.List;

import org.jsoup.nodes.Element;

public interface MetarService {

	List<String> fetchMetarBg();
	
	List<String> fetchTafBg();
	
	List<String> fetch(String url);
	
	Element fetchInfo();
}

package com.scrap.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.scrap.entity.MetarEntity;
import com.scrap.repository.MetarEntityRepository;
import com.scrap.util.MetarUtil;

import io.github.mivek.model.Metar;

@Component
public class MetarComponent {

	@Autowired
	private MetarEntityRepository metarRepository;
	
	@Async
	public void getMetarFromUrl(String code, String url) {
		
		Metar metar = MetarUtil.getMetarFromUrl(url);
		metarRepository.save(new MetarEntity(code, metar.getMessage()));
	}
}

/*
 * @Async
	public CompletableFuture<Metar> getMetarFromUrl(String url) {
		
		Document document = null;
		try {
			document = Jsoup.connect(url).get();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Elements meteos = document.select("div.c1b");
		meteos = meteos.select("p");
		
		Element element =  meteos.get(2);
		Metar metar = MetarUtil.decodeMetarFromElement(element);
		
		return CompletableFuture.completedFuture(metar);
	}
*/
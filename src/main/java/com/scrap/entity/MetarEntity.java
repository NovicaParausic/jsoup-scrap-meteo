package com.scrap.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MetarEntity {

	@Id
	private String code;
	private String metar;
	
	public MetarEntity() {}
	
	public MetarEntity(String code, String metar) {
		this.code = code;
		this.metar = metar;
	}

	public String getCode() {
		return code;
	}

	public String getMetar() {
		return metar;
	}
}

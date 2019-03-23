package com.scrap.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AIRPORTS")
public class Airport {

	@Id
	private String code;
	private String name;
	private String city;
	private String state;
	private String longitude;
	private String latitude;
	private String elevation;
	private String url;
	
	public Airport(){}
	
	public Airport(String code, String name, String state, String longitude, String latitude, String url) {
		super();
		this.code = code;
		this.name = name;
		this.state = state;
		this.longitude = longitude;
		this.latitude = latitude;
		this.url = url;
	}
		
	public String getElevation() {
		return elevation;
	}

	public void setElevation(String elevation) {
		this.elevation = elevation;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}

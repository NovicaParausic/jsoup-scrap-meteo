package com.scrap.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

public class AirportDto {

	@NotNull
	@Size(min = 4, max = 30)
	private String code;
	
	@NotNull
	@Size(min = 4, max = 30)
	private String name;
	
	@NotNull
	@Size(min = 4, max = 30)
	private String city;
	
	@NotNull
	@Size(min = 4, max = 30)
	private String state;
	
	@NotNull
	@Size(min = 4, max = 30)
	private String longitude;
	
	@NotNull
	@Size(min = 4, max = 30)
	private String latitude;
	
	@NotNull
	@Size(min = 4, max = 30)
	private String elevation;
	
	@NotNull
	@Size(min = 4, max = 30)
	//@URL
	private String url;

	
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getElevation() {
		return elevation;
	}

	public void setElevation(String elevation) {
		this.elevation = elevation;
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

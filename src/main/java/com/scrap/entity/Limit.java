package com.scrap.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LIMITS")
public class Limit {

	@Id
	@GeneratedValue
	private Long id;
	private long userId;
	private String code;
	private String wind;
	private int temperature;
	//private int pressure;
	//private int humidity;
	private int visbility;
	private String clouds;
	
	public Limit() {}
	
	public Limit(long userId, String code, String wind, int temperature, int visbility,
			String clouds) {
		super();
		this.userId = userId;
		this.code = code;
		this.wind = wind;
		this.temperature = temperature;
		//this.pressure = pressure;
		//this.humidity = humidity;
		this.visbility = visbility;
		this.clouds = clouds;
	}
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getWind() {
		return wind;
	}
	public void setWind(String wind) {
		this.wind = wind;
	}
	public int getTemperature() {
		return temperature;
	}
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	/*
	public int getPressure() {
		return pressure;
	}
	public void setPressure(int pressure) {
		this.pressure = pressure;
	}
	public int getHumidity() {
		return humidity;
	}
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	*/
	public int getVisbility() {
		return visbility;
	}
	public void setVisbility(int visbility) {
		this.visbility = visbility;
	}
	public String getClouds() {
		return clouds;
	}
	public void setClouds(String clouds) {
		this.clouds = clouds;
	}	
}

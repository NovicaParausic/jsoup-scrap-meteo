package com.scrap.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserIcao {

	@Id
	@GeneratedValue
	private Long id;
	private String icao;
	private String user;
	
	public UserIcao() {}
	
	public UserIcao(String icao, String user) {
		this.icao = icao;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public String getIcao() {
		return icao;
	}

	public String getUser() {
		return user;
	}
	
	
	
	
}

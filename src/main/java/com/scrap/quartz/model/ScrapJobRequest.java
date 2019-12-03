package com.scrap.quartz.model;

import java.time.ZonedDateTime;

public class ScrapJobRequest {

	private String code;
	private String url;
	private ZonedDateTime zonedDateTime;
	
	public ScrapJobRequest() {}

	public ScrapJobRequest(String code, String url, ZonedDateTime zonedDateTime) {
		this.code = code;
		this.url = url;
		this.zonedDateTime = zonedDateTime;
	}

	public String getCode() {
		return code;
	}

	public String getUrl() {
		return url;
	}

	public ZonedDateTime getZonedDateTime() {
		return zonedDateTime;
	}
}

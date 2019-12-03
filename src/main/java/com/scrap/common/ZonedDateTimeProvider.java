package com.scrap.common;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZonedDateTimeProvider {

	private ZonedDateTimeProvider() {}
	
	public static ZonedDateTime now() {
		return ZonedDateTime.now(ZoneId.of("UTC"));
	}
}

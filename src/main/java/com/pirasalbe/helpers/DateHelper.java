package com.pirasalbe.helpers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.TimeZone;

/**
 * Helper methods to manage dates
 *
 * @author pirasalbe
 *
 */
public class DateHelper {

	private static final ZoneId ZONE_ID = TimeZone.getDefault().toZoneId();

	private DateHelper() {
		super();
	}

	public static LocalDateTime integerToLocalDateTime(Integer timestampUnix) {
		return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestampUnix), ZONE_ID);
	}

}

package fr.game.advent.day15;

import java.util.HashMap;
import java.util.Map;

public enum StatusCode {
	HIT_A_WALL			(0, PointStatus.WALL),
	MOVE				(1, PointStatus.VOID),
	MOVE_AND_FIND_OS	(2, PointStatus.OS);
	
	private int statusCodeValue;
	private PointStatus pointStatus;

	private StatusCode(int statusCodeValue, PointStatus pointStatus) {
		this.statusCodeValue = statusCodeValue;
		this.pointStatus = pointStatus;
	}
	
	public PointStatus getPointStatus() {
		return pointStatus;
	}

	private static Map<Long, StatusCode> statusCodes;
	
	public static StatusCode toStatusCode(Long statusCodeValue) {
		if (statusCodes == null) {
			fillStatusCodes();
		}
		return statusCodes.get(statusCodeValue);
	}

	private static void fillStatusCodes() {
		statusCodes = new HashMap<>();
		for (StatusCode statusCode : StatusCode.values()) {
			statusCodes.put(new Long(statusCode.statusCodeValue), statusCode);
		}
	}

}

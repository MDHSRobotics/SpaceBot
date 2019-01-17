package org.usfirst.frc.team4141.MDRobotBase.eventmanager;

public class Request {

	private EventManagerWebSocket socket;
	private String message;

	public Request(EventManagerWebSocket socket, String message) {
		this.message = message;
		this.socket = socket;
	}

	public String getMessage() {
		return message;
	}

	public EventManagerWebSocket getSocket() {
		return socket;
	}

	@Override
	public String toString() {
		return message;
	}

}

package org.usfirst.frc.team4141.MDRobotBase.eventmanager;

public class LogNotification extends Notification {

	public enum Level {
		INFO,
		DEBUG,
		ERROR
	}

	private String message;
	private String source;
	private Level level;

	public LogNotification(Level level, String source, String message) {
		this(level, source, message, true, null, false);
	}

	public LogNotification(Level level, String source, String message, boolean showInConsole, String target, boolean record) {
		super("LogNotification", showInConsole, target, record);
		this.message = message;
		this.source = source;
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	public String getSource() {
		return source;
	}

	public Level getLevel(){
		return level;
	}

	protected void addJSONPayload() {
		if (getLevel() != null) {
			if (sb.length() > 1) sb.append(',');
			sb.append(String.format("\"level\":\"%s\"", getLevel().toString()));
		}
		if (getSource() != null) {
			if (sb.length() > 1) sb.append(',');
			sb.append(String.format("\"source\":\"%s\"", getSource()));
		}
		if (getMessage() != null) {
			if (sb.length() > 1) sb.append(',');
			sb.append(String.format("\"message\":\"%s\"", getMessage()));
		}
	}

	public Notification parse(String notification) {
		return null;
	}

}

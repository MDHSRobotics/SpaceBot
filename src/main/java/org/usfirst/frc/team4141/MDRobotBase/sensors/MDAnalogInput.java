package org.usfirst.frc.team4141.MDRobotBase.sensors;


import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;

import edu.wpi.first.wpilibj.AnalogInput;


public class MDAnalogInput extends AnalogInput implements Sensor {

	SensorReading[] readings = new SensorReading[1];
	private boolean observe;
	private MDSubsystem subsystem;

	public MDAnalogInput(String name, int channel) {
		this(null, name, channel);
	}

	public MDAnalogInput(MDSubsystem subsystem, String name, int channel) {
		this(subsystem, name, channel, true);
	}

	public MDAnalogInput(MDSubsystem subsystem, String name, int channel,boolean observe) {
		super(channel);
		this.observe = observe;
		this.subsystem = subsystem;
		this.setName(name);
		readings[0] = new AnalogSensorReading(this, name, getVoltage());
	}

	public String toJSON(){
		StringBuilder sb = new StringBuilder();
		sb.append("{\"channel\":");
		sb.append(readings[0].toJSON());
		sb.append("}");
		return sb.toString();
	}

	@Override
	public SensorReading[] getReadings() {
		return readings;
	}

	@Override
	public void refresh() {
		((AnalogSensorReading)readings[0]).setValue(getVoltage());
	}

	@Override
	public boolean observe() {
		return observe;
	}
	public void setObserve(boolean observe) {
		this.observe = observe;
	}

	@Override
	public MDSubsystem getSubsystemObject() {
		return subsystem;
	}
	@Override
	public Sensor setSubsystem(MDSubsystem subsystem) {
		this.subsystem = subsystem;
		return this;
	}
}

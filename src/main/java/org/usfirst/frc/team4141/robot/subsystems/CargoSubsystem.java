package org.usfirst.frc.team4141.robot.subsystems;

import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDSubsystem;
import org.usfirst.frc.team4141.MDRobotBase.config.ConfigSetting;
import org.usfirst.frc.team4141.robot.commands.CargoCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

public class CargoSubsystem extends MDSubsystem {

	private SpeedController cargoSpeedController;
	public static String motorName = "cargoSpeedController";
	private double governor = 1.0;

	/**
	 * The constructor creates an instance of the CargoSubsystem and sets its robot and name.
	 */
	public CargoSubsystem(MDRobotBase robot, String name) {
		super(robot, name);
	}

	/**
	 * This method houses the configuring of a SpeedController as a fail-safe to check 
	 * that the SpeedController is connected and ready to be used. If the SpeedController
	 * is not connected the Robot will not enable (this appears to be a lie -- need to test.)
	 * 
	 * @return this
	 */
	public MDSubsystem configure() {
		super.configure();

		if (getMotors() == null || !getMotors().containsKey(motorName)) {
			throw new IllegalArgumentException("Invalid motor configuration for cargo System.");
		}
		cargoSpeedController = (SpeedController)(getMotors().get(motorName));

		return this;
	}

	public void moveFromAxis(Joystick xbox) {
		double axisValue = xbox.getRawAxis(1);
		double moveSpeed = axisValue*governor;
		cargoSpeedController.set(moveSpeed);
	}
	
	public void autoMove(double power) {
		cargoSpeedController.set(power);
	}
	
	public void stop() {
		cargoSpeedController.set(0);
		// doesn't stop motor when OI has whenPressed
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new CargoCommand(this.getRobot()));
	}

	@Override
	protected void setUp() {
		if (getConfigSettings().containsKey("governor")) {
			governor = getConfigSettings().get("governor").getDouble();
		}
	}

	/**
	 * This method allows us to change the values of the variable on the fly, 
	 * without going and re-deploying the code every time we want to change the value.
	 * Instead we can now do it with the new and improved MDConsole.
	 */
	@Override
	public void settingChangeListener(ConfigSetting changedSetting) {
		if (changedSetting.getName().equals("governor")) {
			governor = changedSetting.getDouble();
		}
	}

}

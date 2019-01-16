package org.usfirst.frc.team4141.robot.commands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDJoystick;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.robot.subsystems.CargoSubsystem;

public class CargoCommand extends MDCommand {

	private CargoSubsystem cargoSubsystem;
	private MDJoystick axis = null;

	/**
	 * Within the constructor is a fail-safe to check that the cargoSubsystem
	 * is connected and ready to be used. If the cargoSubsystem is not connected 
	 * the Robot will not enable.
	 */
	public CargoCommand(MDRobotBase robot) {
		super(robot, "cargoCommand");
		cargoSubsystem = (CargoSubsystem)getRobot().getSubsystem("cargoSubsystem"); 
		requires(cargoSubsystem);
	}

	protected void initialize() {
		super.initialize();
		axis = getRobot().getOi().getJoysticks().get("xbox");
		System.out.println("initializing cargoCommand");
	}

	/**
	 * @return false because the command never ends by itself.
	 */
	protected boolean isFinished() {
		return false;
	}

	protected void execute() {
		if (cargoSubsystem != null) cargoSubsystem.moveFromAxis(axis);
	}

	/**
	 * This signifies the end of the command by stopping the cargoSubsystem motors
	 * due to the halt of input by the driver.
	 */
	@Override
	protected void end() {
		super.end();
		cargoSubsystem.stop();
	}

}

package org.usfirst.frc.team4141.robot.commands;

import org.usfirst.frc.team4141.MDRobotBase.MDCommand;
import org.usfirst.frc.team4141.MDRobotBase.MDJoystick;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.robot.subsystems.CargoSubsystem;

public class CargoCommand extends MDCommand {
	
	private CargoSubsystem cargoSubsystem;
	
	// ------------------------------------------------ //
	
	/**
	 * The constructor is used to hold the parameters robot and name.
	 * Within the constructor is a fail-safe to check that the cargoSubsystem
	 * is connected and ready to be used. If the cargoSubsystem is not connected 
	 * the Robot will not enable.
	 *  
	 * @param robot the default name used after the MDRobotBase in the constructor
	 * @return true if the cargoSubsystem is found, false if not.
	 */
	public CargoCommand(MDRobotBase robot) {
		super(robot, "cargoCommand");

		cargoSubsystem = (CargoSubsystem)getRobot().getSubsystem("cargoSubsystem"); 
		requires(cargoSubsystem);
	}

	// ------------------------------------------------ //
	
	private MDJoystick axis = null;
	
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
		if (cargoSubsystem!=null)cargoSubsystem.moveFromAxis(axis);
//		log(Level.DEBUG,"execute()","Moving cargo arms");
	}
	
	/**
	 * This signifies the end of the command by stopping the ropeSubsystem motors
	 * due to the halt of input by the driver.
	 */
	@Override
		protected void end() {
		super.end();
		cargoSubsystem.stop();
			
		}
}

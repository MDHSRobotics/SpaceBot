package org.usfirst.frc.team4141.robot; 

import org.usfirst.frc.team4141.MDRobotBase.sensors.MD_BuiltInAccelerometer;
import org.usfirst.frc.team4141.MDRobotBase.sensors.MD_IMU;
import org.usfirst.frc.team4141.MDRobotBase.MDRobotBase;
import org.usfirst.frc.team4141.MDRobotBase.MDTalonSRX;
import org.usfirst.frc.team4141.MDRobotBase.config.DoubleConfigSetting;
import org.usfirst.frc.team4141.MDRobotBase.config.StringConfigSetting;
import org.usfirst.frc.team4141.robot.commands.AUTOMoveFromWall;
import org.usfirst.frc.team4141.robot.commands.HatchCommand;
import org.usfirst.frc.team4141.robot.commands.CargoCommand;
import org.usfirst.frc.team4141.robot.subsystems.AutonomousSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.CoreSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.HatchSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.CargoSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem.MotorPosition;
import org.usfirst.frc.team4141.robot.subsystems.MDDriveSubsystem.Type;

import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 * This system is the entire brain of the robot.
 * A brain talks to different parts of the body and tells it what to do (right?).
 * We assign motors and what positions to different subsystems.
 * A robot is composed of subsystems.
 * A robot will typically have one (1) drive system and several other fit to purpose subsystems.
 */
public class Robot extends MDRobotBase {

	public static String cargoSubsystemName = "cargoSubsystem";
	public static String hatchSubsystemName = "hatchSubsystem";
	public static String autoSubsystemName = "autoSubsystem";

	// This runs as soon as we press enable teleop in Driver Station (the first thing it does essentially.)
	public void teleopInit() {
		super.teleopInit();
	}

	// Robot Configuration
	@Override
	protected void configureRobot() {		

		debug("\nStart configuring the Robot...");

		// The name needs to be updated every year to keep track.
		// The AutoCommand changes every year and is based off the competition and team agreement.
		CoreSubsystem cSubSys = new CoreSubsystem(this, "core");
		cSubSys.add("name", new StringConfigSetting("SpaceBot")); // <--- Name
		cSubSys.add("autoCommand", new StringConfigSetting("AUTOCommand")); // <--- Default AutoCommand
		cSubSys.configure();
		add(cSubSys);

		// Drive Subsystem Configuration
		
		/*********************************************************************************
		 * 																				 *
		 * 	There are two (2) different types of drive systems.							 *
		 * 	TankDrive: Typically the one used by default. Simple as it gets.			 *
		 * 	MecanumDrive: Allows moving left and right without turning but not as fast.	 *
		 *	Each drive type is programmed and hot-swappable below.						 *
		 * 																				 *
		 *********************************************************************************
		 */

		MDDriveSubsystem driveSubsystem = new MDDriveSubsystem(this, "driveSystem", Type.MecanumDrive);
		driveSubsystem.add("accelerometer", new MD_BuiltInAccelerometer());
		driveSubsystem.add("IMU", new MD_IMU());
		driveSubsystem.add(MotorPosition.frontRight, new MDTalonSRX(1));
		driveSubsystem.add(MotorPosition.frontLeft, new MDTalonSRX(2));
		driveSubsystem.add(MotorPosition.rearRight, new MDTalonSRX(3));
		driveSubsystem.add(MotorPosition.rearLeft, new MDTalonSRX(4));
		driveSubsystem.add("Ramp Time In seconds", new DoubleConfigSetting(0.0, 10.0, 1.0));
		driveSubsystem.add("forwardSpeed", new DoubleConfigSetting(0.0, 1.0, 0.25)); //High Speed - Turn Factor
		driveSubsystem.add("rotateSpeed", new DoubleConfigSetting(0.0, 1.0, 1.0)); //Slow Speed - Turn Factor
		driveSubsystem.add("governor", new DoubleConfigSetting(0.0, 1.0, 1.0)); //Speed Governor
		driveSubsystem.configure();
		add(driveSubsystem);

		// Other Subsystems

		/************************************************************************************
		 * 																					*
		 * 	Developing a subsystem is very simple.											*
		 * 	We need the name, the string, and whatever motors and configurations we want.	*
		 * 	Each new subsystem must be identified to the correct							*
		 * 																					*
		 ************************************************************************************
		 */

		AutonomousSubsystem autoSubSys = new AutonomousSubsystem(this, autoSubsystemName);
		autoSubSys.add("delayStartTime", new DoubleConfigSetting(0.0, 15.0, 0.0));
		autoSubSys.configure();
		add(autoSubSys);

		HatchSubsystem hatchSubSys = new HatchSubsystem(this, hatchSubsystemName);
		hatchSubSys.add(HatchSubsystem.motorName, new MDTalonSRX(5));
		hatchSubSys.add("moveSpeed", new DoubleConfigSetting(0.0, 1.0, 1.0));
		hatchSubSys.add("governor", new DoubleConfigSetting(0.0, 1.0, 1.0)); //Speed Governor
		hatchSubSys.configure();
		add(hatchSubSys);

		CargoSubsystem cargoSubSys = new CargoSubsystem(this, cargoSubsystemName);
		cargoSubSys.add(CargoSubsystem.motorName, new MDTalonSRX(6));
		cargoSubSys.add("moveSpeed", new DoubleConfigSetting(0.0, 1.0, 1.0));
		cargoSubSys.add("governor", new DoubleConfigSetting(0.0, 1.0, 1.0)); //Speed Governor
		cargoSubSys.configure();
		add(cargoSubSys);

		initSmartDashboard();

		debug("\n\n\nDone configuring the Robot.");
		debug("Printing the state of the Robot...");
		debug(this.toString());

	}

	private void initSmartDashboard(){
		SmartDashboard.putData(Scheduler.getInstance());
		SmartDashboard.putData(getSubsystem(cargoSubsystemName));
		SmartDashboard.putData(getSubsystem(hatchSubsystemName));
		SmartDashboard.putData(new HatchCommand(this));
		SmartDashboard.putData(new CargoCommand(this));
	}

	public void autonomousInit() {
		super.autonomousInit();
		AUTOMoveFromWall autoMoveFromWallCommand = new AUTOMoveFromWall(this);
		try {
			autoMoveFromWallCommand.start();
		}
		finally {
			autoMoveFromWallCommand.close();
		}
	}

}
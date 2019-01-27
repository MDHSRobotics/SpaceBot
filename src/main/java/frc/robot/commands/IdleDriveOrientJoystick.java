/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command idles the drive, and flips the joystick Y orientation
public class IdleDriveOrientJoystick extends Command {

    // Constructor
    public IdleDriveOrientJoystick() {
        Logger.debug("Constructing IdleDriveOrientJoystick...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Logger.debug("Initializing IdleDriveOrientJoystick...");

        Robot.robotMecDriver.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.robotMecDriver.flip();
    }

    // This finishes immediately
    @Override
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Logger.debug("Ending IdleDriveOrientJoystick...");

        Robot.robotMecDriver.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Logger.debug("Interrupting IdleDriveOrientJoystick...");

        Robot.robotMecDriver.stop();
    }

}

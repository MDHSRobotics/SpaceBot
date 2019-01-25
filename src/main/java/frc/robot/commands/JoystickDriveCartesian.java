/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.helpers.CartesianMovement;
import frc.robot.helpers.Logger;
// Don't import Devices; Commands use OI and control Robot subsystems, but they don't access any raw devices directly
import frc.robot.OI;
import frc.robot.Robot;

// This command uses the joystick input to drive using the cartesian method
public class JoystickDriveCartesian extends Command {

    // Constructor
    public JoystickDriveCartesian() {
        Logger.debug("Constructing JoystickDriveCartesian...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Logger.debug("Initializing JoystickDriveCartesian...");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        CartesianMovement move = OI.getCartesianMovementFromJoystick(Robot.robotMecDriver.isFlipped);
        Robot.robotMecDriver.driveCartesian(move.xSpeed, move.ySpeed, move.zRotation, move.gyroAngle);
    }

    // This command isn't finished until it is interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Logger.debug("Ending JoystickDriveCartesian...");
        Robot.robotMecDriver.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Logger.debug("Interrupting JoystickDriveCartesian...");
        Robot.robotMecDriver.stop();
    }

}

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.helpers.Logger;
import frc.robot.subsystems.EncoderDrive;
// Don't import Devices; Commands use OI and control Robot subsystems, but they don't access any raw devices directly
import frc.robot.Robot;

// This command uses the joystick input to drive using the polar method
public class AutoEncoderDrive extends Command {

    // Constructor
    public AutoEncoderDrive() {
        Logger.debug("Constructing AutoEncoderDrive...");

        // Declare subsystem dependencies
        requires(Robot.robotEncoderDrive);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Logger.debug("Initializing AutoEncoderDrive...");
        Robot.robotEncoderDrive.resetEncoderPosition();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.robotEncoderDrive.positionDrive(4096);
        Logger.debug("Position: " + Robot.robotEncoderDrive.getPosition());
        Logger.debug("Velocity: " + Robot.robotEncoderDrive.getVelocity());
    }

    // This command isn't finished until it is interrupted
    @Override
    protected boolean isFinished() {
        //boolean isPositionMet = EncoderDrive.isPositionMet();
        // if(isPositionMet){
        //     return true;
        // }
        // else{
            return false;
        //}
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Logger.debug("Ending AutoEncoderDrive...");

        Robot.robotEncoderDrive.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Logger.debug("Interrupting AutoEncoderDrive...");

        Robot.robotEncoderDrive.stop();
    }

}

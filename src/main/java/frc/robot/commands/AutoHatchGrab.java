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


// This command grabs the hatch
public class AutoHatchGrab extends Command {

    public AutoHatchGrab() {
        Logger.debug("Constructing AutoBallerGate...");

         // Declare subsystem dependencies
         requires(Robot.robotHatcher);
    }

    @Override
    protected void initialize() {
        Logger.debug("Constructing AutoBallerGate...");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        Robot.robotHatcher.grab();
    }

    // The command is finished when the Hatch is grabbed
    @Override
    protected boolean isFinished() {
        boolean isGrabbed = Robot.robotHatcher.isGrabbed();
        return isGrabbed;
    }


    // Called once after isFinished returns true
    @Override
    protected void end() {
        Logger.debug("Ending AutoHatchClaw...");

        Robot.robotHatcher.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Logger.debug("Interrupted AutoHatchClaw...");

        Robot.robotHatcher.stop();
    }

}
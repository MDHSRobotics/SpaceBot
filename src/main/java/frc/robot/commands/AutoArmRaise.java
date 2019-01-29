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


// This command is activated by a button and raises the arm until it hits a limit switch
public class AutoArmRaise extends Command {

    // TODO: This speed is the same as the AutoArmLower command. One of these should be negative.
    private double m_speed = 0.3;

    public AutoArmRaise() {
        Logger.debug("Constructing AutoRaiseArm...");

        // Declare subsystem dependencies
        requires(Robot.robotArm);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Logger.debug("Initializing AutoRaiseArm...");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.robotArm.move(m_speed);
    }

    // This command is finished when the Arm has been fully raised
    @Override
    protected boolean isFinished() {
        // TODO: Needs to check a limit switch here

        return true;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Logger.debug("Ending AutoRaiseArm...");

        Robot.robotArm.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Logger.debug("Interrupting AutoRaiseArm...");

        Robot.robotArm.stop();
    }

}

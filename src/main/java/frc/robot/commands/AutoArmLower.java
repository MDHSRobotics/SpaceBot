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


// This command is activated by a button and lowers the arm until interrupted
public class AutoArmLower extends Command {

    // TODO: This speed is the same as the AutoArmRaise command. One of these should be negative.
    private double m_speed = 0.3;

    public AutoArmLower() {
        Logger.debug("Constructing AutoArmLower...");

        // Declare subsystem dependencies
        requires(Robot.robotArm);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Logger.debug("Initializing AutoArmLower...");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.robotArm.move(m_speed);
    }

    // This command continues until it is interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Logger.debug("Ending AutoArmLower...");

        Robot.robotArm.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Logger.debug("Interrupting AutoArmLower...");

        Robot.robotArm.stop();
    }

}

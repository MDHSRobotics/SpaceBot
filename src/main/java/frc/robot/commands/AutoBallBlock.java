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


public class AutoBallBlock extends Command {

    public AutoBallBlock() {
        Logger.debug("Constructing AutoBallBlock...");

        // Declare subsystem dependencies
        requires(Robot.robotBaller);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Logger.debug("Initializing AutoBallBlock...");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.robotBaller.block();
    }

    // This command is finished when the Ball has been fully blocked
    @Override
    protected boolean isFinished() {
        boolean isBlocked = Robot.robotBaller.isBlocked();
        return isBlocked;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Logger.debug("Ending AutoBallBlock...");

        Robot.robotBaller.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Logger.debug("Interrupted AutoBallBlock...");

        Robot.robotBaller.stop();
    }

}

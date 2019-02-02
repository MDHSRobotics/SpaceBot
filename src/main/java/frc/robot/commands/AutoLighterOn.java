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

public class AutoLighterOn extends Command {

    public AutoLighterOn() {
        Logger.debug("Constructing AutoLighterOn...");

         // Declare subsystem dependencies
         requires(Robot.robotLighter);
    }

    @Override
    protected void initialize() {
        Logger.debug("Constructing AutoLighterOn...");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        Robot.robotLighter.turnOn();
    }

    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Logger.debug("Ending AutoLighterOn...");

        Robot.robotLighter.turnOff();
    }


    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Logger.debug("Interrupted AutoLighterOff...");

        Robot.robotLighter.turnOff();
    }

}
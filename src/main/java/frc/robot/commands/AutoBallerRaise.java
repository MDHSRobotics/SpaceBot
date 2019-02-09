/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.helpers.Logger;
// Don't import Devices; Commands use OI and control Robot subsystems, but they don't access any raw devices directly
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Devices;




public class AutoBallerRaise extends Command {

    public AutoBallerRaise() {
        Logger.debug("Constructing AutoBallerGate...");

        // Declare subsystem dependencies
        requires(Robot.robotBaller);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Logger.debug("Initializing AutoBallerGate...");
        Robot.robotBaller.ballRaise();

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        Logger.debug("Position: " + Robot.robotBaller.getPosition());
        Logger.debug("Velocity: " +  Robot.robotBaller.getVelocity());
        SmartDashboard.putNumber("Sensor Vel:", Devices.talonSrxBaller.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Sensor Pos:", Devices.talonSrxBaller.getSelectedSensorPosition());
        SmartDashboard.putNumber("Out %",  Devices.talonSrxBaller.getMotorOutputPercent());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        // TODO: need a limit switch to determine when this is done

        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Logger.debug("Ending AutoBallerGate...");

        Robot.robotBaller.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Logger.debug("Interrupted AutoBallerGate...");

        Robot.robotBaller.stop();
    }

}

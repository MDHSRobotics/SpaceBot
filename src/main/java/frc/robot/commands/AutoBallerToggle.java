/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.helpers.Logger;
import frc.robot.subsystems.Hatcher;
import frc.robot.Devices;
// Don't import Devices; Commands use OI and control Robot subsystems, but they don't access any raw devices directly
import frc.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// This command opens or closes the Hatch claw
public class AutoBallerToggle extends Command {

    double joystickValue;

    public AutoBallerToggle() {
        Logger.debug("Constructing AutoHatchToggle...");

         // Declare subsystem dependencies
         requires(Robot.robotBaller);
    }

    @Override
    protected void initialize() {
        Logger.debug("Constructing AutoHatchToggle...");
        if(Robot.robotBaller.getBallToggle() == false){
            Robot.robotBaller.ballRaise();
        }
        else{
            Robot.robotBaller.ballClose();
        }

    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Logger.debug("Position: " + Robot.robotBaller.getPosition());
        Logger.debug("Velocity: " +  Robot.robotBaller.getVelocity());
        SmartDashboard.putNumber("Sensor Vel:", Devices.talonSrxBaller.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Sensor Pos:", Devices.talonSrxBaller.getSelectedSensorPosition());
        SmartDashboard.putNumber("Out %",  Devices.talonSrxBaller.getMotorOutputPercent());
        //SmartDashboard.putBoolean("Out Of Phase:", _faults.SensorOutOfPhase);
        
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished(){
       // return Robot.robotBaller.isStopped();
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Logger.debug("Ending AutoHatchToggle...");
        Robot.robotBaller.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        Logger.debug("Interrupted AutoHatchToggle...");
        Robot.robotBaller.stop();
        Robot.robotBaller.setBallToggle();
    }

}

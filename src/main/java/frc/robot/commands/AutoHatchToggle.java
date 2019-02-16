
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.helpers.Logger;
import frc.robot.Devices;
import frc.robot.Robot;


// This command opens or closes the Hatch claw
public class AutoHatchToggle extends Command {

    public AutoHatchToggle() {
        Logger.debug("Constructing AutoHatchToggle...");

         // Declare subsystem dependencies
         requires(Robot.robotHatcher);
    }

    @Override
    protected void initialize() {
        Logger.debug("Constructing AutoHatchToggle...");
        if (Robot.robotHatcher.getHatchToggle() == false) {
            Robot.robotHatcher.clawOpen();
        }
        else {
            Robot.robotHatcher.clawClose();
        }
        Robot.robotHatcher.setHatchToggle();
    }

    @Override
    protected void execute() {
        Logger.debug("Position: " + Robot.robotHatcher.getPosition());
        Logger.debug("Velocity: " +  Robot.robotHatcher.getVelocity());
        SmartDashboard.putNumber("Sensor Vel:", Devices.talonSrxHatcher.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Sensor Pos:", Devices.talonSrxHatcher.getSelectedSensorPosition());
        SmartDashboard.putNumber("Out %",  Devices.talonSrxHatcher.getMotorOutputPercent());
        //SmartDashboard.putBoolean("Out Of Phase:", _faults.SensorOutOfPhase);
    }

    @Override
    protected boolean isFinished() {
       // return Robot.robotHatcher.isStopped();
        return Robot.robotHatcher.isPositionMet();
       //return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending AutoHatchToggle...");

        Robot.robotHatcher.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupted AutoHatchToggle...");

        Robot.robotHatcher.stop();
    }

}


package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.helpers.Logger;
import frc.robot.Devices;
import frc.robot.Robot;


// This command raises or closes the Baller
public class AutoBallerToggle extends Command {

    public AutoBallerToggle() {
        Logger.debug("Constructing AutoHatchToggle...");

         // Declare subsystem dependencies
         requires(Robot.robotBaller);
    }

    @Override
    protected void initialize() {
        Logger.debug("Constructing AutoHatchToggle...");
        if (Robot.robotBaller.getBallToggle() == false) {
            Robot.robotBaller.ballRaise();
        }
        else {
            Robot.robotBaller.ballClose();
        }
    }

    @Override
    protected void execute() {
        Logger.debug("Position: " + Robot.robotBaller.getPosition());
        Logger.debug("Velocity: " +  Robot.robotBaller.getVelocity());
        SmartDashboard.putNumber("Sensor Vel:", Devices.talonSrxBaller.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Sensor Pos:", Devices.talonSrxBaller.getSelectedSensorPosition());
        SmartDashboard.putNumber("Out %",  Devices.talonSrxBaller.getMotorOutputPercent());
        //SmartDashboard.putBoolean("Out Of Phase:", _faults.SensorOutOfPhase);
    }

    @Override
    protected boolean isFinished() {
       // return Robot.robotBaller.isStopped();
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending AutoHatchToggle...");

        Robot.robotBaller.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupted AutoHatchToggle...");

        Robot.robotBaller.stop();
        Robot.robotBaller.setBallToggle();
    }

}

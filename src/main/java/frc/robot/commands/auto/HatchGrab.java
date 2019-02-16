
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.helpers.Logger;
import frc.robot.Devices;
import frc.robot.Robot;


// This command opens the Hatcher claw to grab the hatch
public class HatchGrab extends Command {

    public HatchGrab() {
        Logger.debug("Constructing Command: HatchGrab...");

         // Declare subsystem dependencies
         requires(Robot.robotHatcher);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: HatchGrab...");

        Robot.robotHatcher.grabHatch();
    }

    @Override
    protected void execute() {
        // Logger.debug("Position: " + Robot.robotHatcher.getPosition());
        // Logger.debug("Velocity: " +  Robot.robotHatcher.getVelocity());
        SmartDashboard.putNumber("Sensor Vel:", Devices.talonSrxHatcher.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Sensor Pos:", Devices.talonSrxHatcher.getSelectedSensorPosition());
        SmartDashboard.putNumber("Out %",  Devices.talonSrxHatcher.getMotorOutputPercent());
        //SmartDashboard.putBoolean("Out Of Phase:", _faults.SensorOutOfPhase);
    }

    // This command is finished when the hatch is grabbed
    @Override
    protected boolean isFinished() {
        return Robot.robotHatcher.isPositionMet();
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: HatchGrab...");

        Robot.robotHatcher.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: HatchGrab...");

        Robot.robotHatcher.stop();
    }

}

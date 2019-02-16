
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.helpers.Logger;
import frc.robot.Devices;
import frc.robot.Robot;


// This command closes the Hatcher claw to release the hatch
public class HatchRelease extends Command {

    public HatchRelease() {
        Logger.debug("Constructing Command: HatchRelease...");

         // Declare subsystem dependencies
         requires(Robot.robotHatcher);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: HatchRelease...");

        Robot.robotHatcher.releaseHatch();
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

    // This command is finished when the Hatch is released
    @Override
    protected boolean isFinished() {
        return Robot.robotHatcher.isPositionMet();
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: HatchRelease...");

        Robot.robotHatcher.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: HatchRelease...");

        Robot.robotHatcher.stop();
    }

}

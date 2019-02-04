
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command turns on the LineDetector "Lighter" light
public class AutoLightOn extends Command {

    public AutoLightOn() {
        Logger.debug("Constructing AutoLightOn...");

         // Declare subsystem dependencies
         requires(Robot.robotLighter);
    }

    @Override
    protected void initialize() {
        Logger.debug("Constructing AutoLightOn...");
    }

    @Override
    protected void execute() {
        Robot.robotLighter.turnOn();
    }

    @Override
    protected boolean isFinished() {
        // TODO: Shouldn't this finish immediately?
        // Or does it take time to turn on? If so, how do you know it's done?

        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending AutoLightOn...");

        Robot.robotLighter.turnOff();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupted AutoLightOn...");

        Robot.robotLighter.turnOff();
    }

}

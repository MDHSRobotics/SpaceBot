
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command turns on the LineDetector "Lighter" light
public class LightOn extends Command {

    public LightOn() {
        Logger.debug("Constructing Command: LightOn...");

         // Declare subsystem dependencies
         requires(Robot.robotLighter);
    }

    @Override
    protected void initialize() {
        Logger.debug("Constructing Command: LightOn...");
    }

    @Override
    protected void execute() {
        Robot.robotLighter.turnOn();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: LightOn...");

        Robot.robotLighter.turnOff();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupted Command: LightOn...");

        Robot.robotLighter.turnOff();
    }

}

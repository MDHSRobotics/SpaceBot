
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command turns on the LineDetector "Lighter" light
public class AutoLightToggle extends Command {

    public AutoLightToggle() {
        Logger.debug("Constructing AutoLightToggle...");

         // Declare subsystem dependencies
         requires(Robot.robotLighter);
    }

    @Override
    protected void initialize() {
        Logger.debug("Constructing AutoLightToggle...");
    }

    @Override
    protected void execute() {
        boolean lineDetected = Robot.robotLineDetector.lineDetected();
        if(lineDetected){
            Robot.robotLighter.turnOnForward();

        }
        else{
            Robot.robotLighter.turnOff();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending AutoLightToggle...");

        Robot.robotLighter.turnOff();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupted AutoLightToggle...");

        Robot.robotLighter.turnOff();
    }

}

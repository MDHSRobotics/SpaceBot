
package frc.robot.commands.interactive;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.sensors.Vision;
import frc.robot.Robot;


// This command toggles the "Lighter" lights when a line is detected by Vision
public class LightToggle extends Command {

    public LightToggle() {
        Logger.setup("Constructing Command: LightToggle...");

         // Declare subsystem dependencies
         requires(Robot.robotLighter);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: LightToggle...");
    }

    @Override
    protected void execute() {
        // TODO: this need to check to see if the left and right lines are detected as well
        boolean lineDetected = Vision.frontLineDetected();
        if (lineDetected) {
            Robot.robotLighter.turnOnForward();
        }
        else {
            Robot.robotLighter.turnOnReverse();
        }
        // TODO: this actually needs to handle three different states:
        // 1. white=lineDetectedInMecDriveMode
        // 2. red=robotPerformingCommandNotUnderActiveUserControl
        // 3. off=robotInUserControlledModeWithNoLineDetected
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: LightToggle...");

        Robot.robotLighter.turnOff();
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: LightToggle...");

        Robot.robotLighter.turnOff();
    }

}

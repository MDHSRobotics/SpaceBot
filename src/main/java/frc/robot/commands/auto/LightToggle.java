
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.Robot;


// This command toggles the LineDetector "Lighter" light
public class LightToggle extends Command {

    public LightToggle() {
        Logger.debug("Constructing Command: LightToggle...");

         // Declare subsystem dependencies
         requires(Robot.robotLighter);
    }

    @Override
    protected void initialize() {
        Logger.debug("Constructing Command: LightToggle...");
    }

    @Override
    protected void execute() {
        // TODO: this needs to handle three different states:
        // 1. white=lineDetectedInMecDriveMode
        // 2. red=robotPerformingCommandNotUnderActiveUserControl
        // 3. off=robotInUserControlledModeWithNoLineDetected
        boolean lineDetected = Robot.robotLineDetectorHatch.lineDetected();
        if (lineDetected) {
            Robot.robotLighter.turnOnForward();
        }
        else {
            Robot.robotLighter.turnOff();
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: LightToggle...");

        Robot.robotLighter.turnOff();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupted Command: LightToggle...");

        Robot.robotLighter.turnOff();
    }

}

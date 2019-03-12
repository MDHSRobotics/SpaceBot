
package frc.robot.commands.reactive;

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
        // 1. off = robot is driving too fast to detect a line
        // 2. red = robot is driving slow enough to detect a line, but no line is detected
        // 3. white = robot is driving slow enough to detect a line, and a line is detected
        // This necessitates that the MecDriver updates a public property called "lineIsDetectable", the value of which is based on input speed while driving
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

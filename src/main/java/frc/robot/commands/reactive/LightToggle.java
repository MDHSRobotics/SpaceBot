
package frc.robot.commands.reactive;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.sensors.Vision;
import frc.robot.OI;
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
        boolean frontLineDetected = Vision.frontLineDetected();
        boolean rightLineDetected = Vision.rightLineDetected();
        boolean leftLineDetected = Vision.leftLineDetected();
        if (frontLineDetected || leftLineDetected || rightLineDetected) {
            Robot.robotLighter.turnOnWhiteOnly();

            int dpadAngle = OI.getDpadAngleForGyro();
            boolean isAligned = Robot.robotMecDriver.isAligned(dpadAngle);
            if (isAligned) {
                Robot.robotLighter.turnOnRedOnly();
            }
            else {
                Robot.robotLighter.turnOnWhiteOnly();
            }
        }
        else {
            Robot.robotLighter.turnOffBoth();
        }

        // TODO: this needs to handle a distance state
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: LightToggle...");

        Robot.robotLighter.turnOffBoth();
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: LightToggle...");

        Robot.robotLighter.turnOffBoth();
    }

}

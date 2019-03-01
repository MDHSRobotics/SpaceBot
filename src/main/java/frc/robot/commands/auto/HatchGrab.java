
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Robot;


// This command opens the Hatcher claw to grab the hatch
public class HatchGrab extends Command {

    public HatchGrab() {
        Logger.setup("Constructing Command: HatchGrab...");

         // Declare subsystem dependencies
         requires(Robot.robotHatcher);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: HatchGrab...");

        Robot.robotHatcher.grabHatch();
    }

    @Override
    protected void execute() {
        int position = Robot.robotHatcher.getPosition();
        int velocity = Robot.robotHatcher.getVelocity();
        Logger.info("HatchGrab -> Position: " + position + "; Velocity: " + velocity);
    }

    // This command is finished when the hatch is grabbed
    @Override
    protected boolean isFinished() {
        return Robot.robotHatcher.isGrabPositionMet();
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: HatchGrab...");

        Robot.robotHatcher.stop();
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: HatchGrab...");

        Robot.robotHatcher.stop();
    }

}

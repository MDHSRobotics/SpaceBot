
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.Robot;


// This command resets the Arm to its starting position
public class ArmReset extends Command {

    public ArmReset() {
        Logger.setup("Constructing Command: TankPulleyReset...");

        requires(Robot.robotArm);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: ArmReset...");

        // Set the encoded position
        Robot.robotArm.resetPosition();
    }

    @Override
    protected void execute() {
        // TODO: Comment out Logger output once this is determined to be working reliably.
        //       Excess logging during executes can slow things down and spam the log.
        int position = Robot.robotArm.getPosition();
        int velocity = Robot.robotArm.getVelocity();
        Logger.info("ArmReset -> Position: " + position + "; Velocity: " + velocity);
    }

    // This will finish when the Arm reaches its encoded "reset" position
    @Override
    protected boolean isFinished() {
        return Robot.robotArm.isResetPositionMet();
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: ArmReset...");

        Robot.robotArm.stop();
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: ArmReset...");

        Robot.robotArm.stop();
    }

}

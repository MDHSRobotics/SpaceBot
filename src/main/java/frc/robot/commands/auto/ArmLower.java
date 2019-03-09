
package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.OI;
import frc.robot.Robot;


// This command lowers the Arm via encoder, with the user option to manually override
public class ArmLower extends Command {

    public ArmLower() {
        Logger.setup("Constructing Command: ArmLower...");

        requires(Robot.robotArm);
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: ArmLower...");

        // Set the encoded position
        Robot.robotArm.lower();
    }

    @Override
    protected void execute() {
        // TODO: You are getting the input from the controller twice,
        //       which is slower, and also could give you different results.
        //       Instead, get the value and put it into a variable, and use that.
        //       (This is good practice anyway, as it forces you to name the value,
        //       which serves as documentation via code, and is easier for others to read and understand.)
        if (OI.getArmLowerSpeed() != 0) {
            // TODO: When this happens, set a state on the Arm subsystem to indicate it's now under manual control.
            //       Check that state first, and if it's manual, don't check that the input is not zero, just use the value.
            Robot.robotArm.manualControl(OI.getArmLowerSpeed());
            // TODO: I know I said otherwise previously, but now I do think it might be better to have this start a new command instead.
        }

        // TODO: Comment out Logger output once this is determined to be working reliably.
        //       Excess logging during executes can slow things down and spam the log.
        int position = Robot.robotArm.getPosition();
        int velocity = Robot.robotArm.getVelocity();
        Logger.info("ArmLower -> Position: " + position + "; Velocity: " + velocity);
    }

    // This will finish when the Arm reaches its encoded "lower" position
    @Override
    protected boolean isFinished() {
        // TODO determine proper finish logic
        return Robot.robotArm.isLowerPositionMet();
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: ArmLower...");

        Robot.robotArm.stop();
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: ArmLower...");

        Robot.robotArm.stop();
    }

}

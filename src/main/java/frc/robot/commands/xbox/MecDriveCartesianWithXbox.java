
package frc.robot.commands.xbox;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.CartesianMovement;
import frc.robot.helpers.Logger;
import frc.robot.OI;
import frc.robot.Robot;


// This command uses the xbox input to mecanum drive using the cartesian method
public class MecDriveCartesianWithXbox extends Command {

    public MecDriveCartesianWithXbox() {
        Logger.debug("Constructing Command: MecDriveCartesianXbox...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: MecDriveCartesianXbox...");
    }

    @Override
    protected void execute() {
        CartesianMovement move = OI.getCartesianMovementFromXbox();
        Robot.robotMecDriver.driveCartesian(move.ySpeed, move.xSpeed, move.zRotation);
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: MecDriveCartesianXbox...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: MecDriveCartesianXbox...");

        Robot.robotMecDriver.stop();
    }

}

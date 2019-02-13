
package frc.robot.commands.interactive;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.CartesianMovement;
import frc.robot.helpers.Logger;
import frc.robot.OI;
import frc.robot.Robot;


// This command uses the joystick input to mecanum drive using the cartesian method
public class MecDriveCartesian extends Command {

    public MecDriveCartesian() {
        Logger.debug("Constructing Command: MecDriveCartesian...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: MecDriveCartesian...");
    }

    @Override
    protected void execute() {
        CartesianMovement move = OI.getCartesianMovement(Robot.robotMecDriver.controlStickDirectionFlipped);
        Robot.robotMecDriver.driveCartesian(move.ySpeed, move.xSpeed, move.zRotation);
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: MecDriveCartesian...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: MecDriveCartesian...");

        Robot.robotMecDriver.stop();
    }

}

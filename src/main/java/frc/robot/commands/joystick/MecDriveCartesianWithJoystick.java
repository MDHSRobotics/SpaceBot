
package frc.robot.commands.joystick;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.CartesianMovement;
import frc.robot.helpers.Logger;
import frc.robot.OI;
import frc.robot.Robot;


// This command uses the joystick input to mecanum drive using the cartesian method
public class MecDriveCartesianWithJoystick extends Command {

    public MecDriveCartesianWithJoystick() {
        Logger.debug("Constructing Command: MecDriveCartesianJoystick...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: MecDriveCartesianJoystick...");
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
        Logger.debug("Ending Command: MecDriveCartesianJoystick...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: MecDriveCartesianJoystick...");

        Robot.robotMecDriver.stop();
    }

}

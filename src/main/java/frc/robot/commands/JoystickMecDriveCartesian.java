
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.CartesianMovement;
import frc.robot.helpers.Logger;
import frc.robot.OI;
import frc.robot.Robot;


// This command uses the joystick input to mecanum drive using the cartesian method
public class JoystickMecDriveCartesian extends Command {

    public JoystickMecDriveCartesian() {
        Logger.debug("Constructing JoystickDriveCartesian...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing JoystickDriveCartesian...");
    }

    @Override
    protected void execute() {
        CartesianMovement move = OI.getCartesianMovementFromJoystick(Robot.robotMecDriver.isFlipped);
        Robot.robotMecDriver.driveCartesian(move.xSpeed, move.ySpeed, move.zRotation, move.gyroAngle);
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending JoystickDriveCartesian...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting JoystickDriveCartesian...");

        Robot.robotMecDriver.stop();
    }

}

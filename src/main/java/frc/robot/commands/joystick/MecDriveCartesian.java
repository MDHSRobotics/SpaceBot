
package frc.robot.commands.joystick;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.CartesianMovement;
import frc.robot.helpers.Logger;
import frc.robot.OI;
import frc.robot.Robot;


// This command uses the joystick input to mecanum drive using the cartesian method
public class MecDriveCartesian extends Command {

    public MecDriveCartesian() {
        Logger.debug("Constructing MecDriveCartesian...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing MecDriveCartesian...");
    }

    @Override
    protected void execute() {
        CartesianMovement move = OI.getCartesianMovementFromJoystick(Robot.robotMecDriver.isControlOrientationFlipped);
        Robot.robotMecDriver.driveCartesian(move.xSpeed, move.ySpeed, move.zRotation, move.gyroAngle);
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending MecDriveCartesian...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting MecDriveCartesian...");

        Robot.robotMecDriver.stop();
    }

}

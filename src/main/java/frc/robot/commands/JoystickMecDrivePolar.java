
package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.helpers.PolarMovement;
import frc.robot.OI;
import frc.robot.Robot;


// This command uses the joystick input to mecanum drive using the polar method
public class JoystickMecDrivePolar extends Command {

    public JoystickMecDrivePolar() {
        Logger.debug("Constructing JoystickMecDrivePolar...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing JoystickMecDrivePolar...");
    }

    @Override
    protected void execute() {
        PolarMovement move = OI.getPolarMovementFromJoystick(Robot.robotMecDriver.isFlipped);
        Robot.robotMecDriver.drivePolar(move.magnitude, move.angle, move.rotation);
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending JoystickMecDrivePolar...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting JoystickMecDrivePolar...");

        Robot.robotMecDriver.stop();
    }

}


package frc.robot.commands.interactive;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.helpers.Logger;
import frc.robot.helpers.PolarMovement;
import frc.robot.OI;
import frc.robot.Robot;


// This command uses the joystick input to mecanum drive using the polar method
public class MecDrivePolar extends Command {

    public MecDrivePolar() {
        Logger.debug("Constructing Command: MecDrivePolar...");

        // Declare subsystem dependencies
        requires(Robot.robotMecDriver);
    }

    @Override
    protected void initialize() {
        Logger.debug("Initializing Command: MecDrivePolar...");
    }

    @Override
    protected void execute() {
        PolarMovement move = OI.getPolarMovementFromJoystick(Robot.robotMecDriver.controlStickDirectionFlipped);
        Robot.robotMecDriver.drivePolar(move.magnitude, move.angle, move.rotation);
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.debug("Ending Command: MecDrivePolar...");

        Robot.robotMecDriver.stop();
    }

    @Override
    protected void interrupted() {
        Logger.debug("Interrupting Command: MecDrivePolar...");

        Robot.robotMecDriver.stop();
    }

}

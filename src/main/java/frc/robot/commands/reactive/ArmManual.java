
package frc.robot.commands.reactive;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.consoles.Logger;
import frc.robot.OI;
import frc.robot.Robot;


// This command lowers the Arm manually
public class ArmManual extends Command {

    private PulleyLift m_pulleyLift;

    public ArmManual() {
        Logger.setup("Constructing Command: ArmManual...");

        requires(Robot.robotArm);

        m_pulleyLift = new PulleyLift();
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: ArmManual...");
    }

    @Override
    protected void execute() {
        double speed = OI.getArmLowerSpeed();
        Robot.robotArm.setSpeed(speed);

        boolean armIsOnHab = Robot.robotArm.isArmOnHab();
        if (armIsOnHab && !Robot.robotPulley.isLifting) {
            m_pulleyLift.start();
        }
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: ArmManual...");
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: ArmManual...");
    }

}

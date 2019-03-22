
package frc.robot.commands.reactive;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.commands.interactive.ArmManual;
import frc.robot.consoles.Logger;
import frc.robot.OI;
import frc.robot.Robot;


// This command lowers the Arm via encoder, and keeps it there
public class ArmLower extends Command {

    private ArmManual  m_armManualCmd;  
    private PulleyLift m_pulleyLift;

    public ArmLower() {
        Logger.setup("Constructing Command: ArmLower...");

        // Declare subsystem dependencies
        requires(Robot.robotArm);

        m_armManualCmd = new ArmManual();
        m_pulleyLift = new PulleyLift();
    }

    @Override
    protected void initialize() {
        Logger.action("Initializing Command: ArmLower...");

        // Set the encoded position
        Robot.robotArm.lowerFull();
    }

    @Override
    protected void execute() {
        // int position = Robot.robotArm.getPosition();
        // int velocity = Robot.robotArm.getVelocity();
        // Logger.info("ArmLower -> Position: " + position + "; Velocity: " + velocity);

        boolean armIsOnHab = Robot.robotArm.isArmOnHab();
        if (armIsOnHab && !Robot.robotPulley.isLifting) {
            m_pulleyLift.start();
        }

        double speed = OI.getArmLowerSpeed();
        if (speed != 0) {
            m_armManualCmd.start();
        }
    }

    // This command continues until interrupted
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Logger.ending("Ending Command: ArmLower...");
    }

    @Override
    protected void interrupted() {
        Logger.ending("Interrupting Command: ArmLower...");
    }

}

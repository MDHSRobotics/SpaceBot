
package frc.robot.commands.instant;

import edu.wpi.first.wpilibj.command.InstantCommand;

import frc.robot.commands.auto.ArmLower;
import frc.robot.commands.groups.ArmPulleyReset;
import frc.robot.consoles.Logger;
import frc.robot.Robot;


public class ClimbNextStage extends InstantCommand {

    private ArmPulleyReset m_armPulleyResetCmd;
    private ArmLower m_armLowerCmd;

    public ClimbNextStage() {
        super();
        Logger.setup("Constructing InstantCommand: ClimbNextStage...");

        m_armPulleyResetCmd = new ArmPulleyReset();
        m_armLowerCmd = new ArmLower();
    }

    @Override
    protected void initialize() {
        System.out.println("--");
        Logger.action("Initializing InstantCommand: ClimbNextStage...");

        // We don't want to have the robot start climbing early because someone dropped the controller
        switch (Robot.robotGameMode) {
            case DELIVERY:
                Logger.info("Climb -> Resetting Arm and Pulley!");
                m_armPulleyResetCmd.start();
                break;

            case CLIMB:
                Logger.info("Climb -> Lowering Arm!");
                m_armLowerCmd.start();
                break;
        }
    }

}

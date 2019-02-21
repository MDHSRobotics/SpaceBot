
package frc.robot.commands.instant;

import edu.wpi.first.wpilibj.command.InstantCommand;

import frc.robot.commands.auto.ArmLowerHalf;
import frc.robot.commands.auto.ArmLowerFull;
import frc.robot.commands.auto.TankPulleyLift;
import frc.robot.commands.groups.ArmPulleyReset;
import frc.robot.commands.interactive.ArmLowerMore;
import frc.robot.commands.interactive.TankSpin;
import frc.robot.consoles.Logger;
import frc.robot.Robot;


public class ClimbNextStage extends InstantCommand {

    private ArmPulleyReset m_armPulleyResetCmd;
    private ArmLowerHalf m_armLowerHalfCmd;
    private ArmLowerFull m_armLowerFullCmd;
    private ArmLowerMore m_armLowerMoreCmd;
    private TankPulleyLift m_tankPulleyLiftCmd;
    private TankSpin m_tankSpinCmd;

    public ClimbNextStage() {
        super();
        Logger.setup("Constructing InstantCommand: ClimbNextStage...");

        m_armPulleyResetCmd = new ArmPulleyReset();
        m_armLowerHalfCmd = new ArmLowerHalf();
        m_armLowerFullCmd = new ArmLowerFull();
        m_armLowerMoreCmd = new ArmLowerMore();
        m_tankPulleyLiftCmd = new TankPulleyLift();
        m_tankSpinCmd = new TankSpin();
    }

    @Override
    protected void initialize() {
        System.out.println("--");
        Logger.action("Initializing InstantCommand: ClimbNextStage...");

        // We don't want to have the robot start climbing early because someone dropped the controller
        switch (Robot.robotGameMode) {
            case DELIVERY: // Reset and stop all Climb subsystems
                m_armPulleyResetCmd.start();
                break;

            case CLIMB:
                switch (Robot.robotClimbMode) {
                    case ARM:
                        switch (Robot.robotArm.currentArmPosition) {
                            case START: // The first Climb action
                                m_armLowerHalfCmd.start();
                                break;

                            case HALF: // The third Climb action
                                m_armLowerFullCmd.start();
                                break;

                            case FULL: case MORE:
                                break;
                        }
                        break;

                    case LIFT: // The second Climb action
                        m_tankPulleyLiftCmd.start();
                        break;

                    case CLIMB: // The fourth Climb action
                        m_armLowerMoreCmd.start();
                        m_tankSpinCmd.start();
                        break;
                }
                break;
        }
    }

}

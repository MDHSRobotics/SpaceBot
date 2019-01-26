/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.JoystickDriveCartesian;
import frc.robot.helpers.Logger;
// Don't import OI; Subsystems control robot devices, they don't access HIDs -- commands do that
import frc.robot.Devices;

/**
 * Add your docs here.
 */
public class Arm extends Subsystem {
    private double m_secondsFromNeutralToFull = 1.0;
    private int m_timeoutMS = 10;

    public Arm(){
        Logger.debug("Contructing Arm...");

        Devices.talonSrxClimbArm.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
    }


  @Override
  public void initDefaultCommand() {
      Logger.debug("Initializing Arm default command...");

      IdleArm defaultCmd = new IdleArm();

  }
  public void stop() {
    Devices.talonSrxClimbArm.stopMotor();
}

// Opens or closes the Baller gate based on speed
public void arms(double speed) {
    Devices.talonSrxClimbArm.set(speed);
}
}

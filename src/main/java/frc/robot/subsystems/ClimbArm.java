/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.IdleClimbArm;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Subsystem to move the Arm
public class ClimbArm extends Subsystem {

    private double m_secondsFromNeutralToFull = 1.0;
    private int m_timeoutMS = 10;

    // Constructor
    public ClimbArm(){
        Logger.debug("Contructing ClimbArm...");

        Devices.talonSrxClimbArm.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing ClimbArm default command...");

        setDefaultCommand(new IdleClimbArm());
    }

    // Stop the Arm motor
    public void stop() {
        Devices.talonSrxClimbArm.stopMotor();
    }

    // Raises or Lowers the Arm based on given speed
    public void move(double speed) {
        Devices.talonSrxClimbArm.set(speed);
    }

}

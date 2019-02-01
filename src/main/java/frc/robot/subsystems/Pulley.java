/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.IdlePulley;
import frc.robot.helpers.Logger;
import frc.robot.Devices;

/**
 * Add your docs here.
 */
public class Pulley extends Subsystem {
    
    private double m_secondsFromNeutralToFull = 1.0;
    private int m_timeoutMS = 10;
    

    public Pulley() {
        Logger.debug("Constructing Pulley...");
    
        Devices.talonSrxClimbPulley.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
    
    }

  @Override
  public void initDefaultCommand() {
    Logger.debug("Initializing Pulley default command...");

        setDefaultCommand(new IdlePulley());
  }

// Stop the Footer motor
  public void stop() {
    Devices.talonSrxClimbPulley.stopMotor();
}

// Pushes in/out Foot based on a given speed
public void lift(double speed) {
    Devices.talonSrxClimbPulley.set(speed);
    }
    
}

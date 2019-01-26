/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.IdleBaller;
import frc.robot.helpers.Logger;
// Don't import OI; Subsystems control robot devices, they don't access HIDs -- commands do that
import frc.robot.Devices;

// Cargo ball subsystem
public class Baller extends Subsystem {

    private double m_secondsFromNeutralToFull = 1.0;
    private int m_timeoutMS = 10;
    private double m_speed = 0.2; 

    public Baller() {
        Logger.debug("Constructing Baller...");

        Devices.talonSrxBaller.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Baller default command...");

        IdleBaller defaultCmd = new IdleBaller();
        setDefaultCommand(defaultCmd);
    }

    // Stop all the drive motors
    public void stop() {
        Devices.talonSrxBaller.stopMotor();
    }

    // Opens or closes the Baller gate based on speed
    public void deploy() {
       
        Devices.talonSrxBaller.set(m_speed);
   
}

    public void block(){
        Devices.talonSrxBaller.set(-m_speed);    
}

    public boolean isDeployed(){

    boolean hitLimit = Devices.limitSwitchBallDown.get();
    if(hitLimit) Logger.debug("LimitSwitch Pressed");
    return hitLimit;

}

public boolean isBlocked(){

    boolean hitLimit = Devices.limitSwitchBallUp.get();
    return hitLimit;

}


}

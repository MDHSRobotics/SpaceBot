/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.helpers.Constants;
import frc.robot.helpers.Logger;
import frc.robot.Devices;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 * Add your docs here.
 */
public class EncoderMovement extends Subsystem{

    public EncoderMovement(){

        Logger.debug("Constructing EncoderMovement...");

    Devices.talonSrxWheelFrontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 30);
    Devices.talonSrxWheelFrontRight.setSensorPhase(Constants.kSensorPhase);
    Devices.talonSrxWheelFrontRight.setInverted(Constants.kMotorInvert);
    Devices.talonSrxWheelFrontRight.configNominalOutputForward(0, Constants.kTimeoutMs);
    Devices.talonSrxWheelFrontRight.configNominalOutputReverse(0, Constants.kTimeoutMs);
    Devices.talonSrxWheelFrontRight.configPeakOutputForward(1, Constants.kTimeoutMs);
    Devices.talonSrxWheelFrontRight.configPeakOutputReverse(-1, Constants.kTimeoutMs);
    Devices.talonSrxWheelFrontRight.configAllowableClosedloopError(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    Devices.talonSrxWheelFrontRight.config_kF(Constants.kPIDLoopIdx, 0.0, Constants.kTimeoutMs);
    Devices.talonSrxWheelFrontRight.config_kP(Constants.kPIDLoopIdx, 0.1, Constants.kTimeoutMs);
    Devices.talonSrxWheelFrontRight.config_kI(Constants.kPIDLoopIdx, 0.0, Constants.kTimeoutMs);
    Devices.talonSrxWheelFrontRight.config_kD(Constants.kPIDLoopIdx, 0.0, Constants.kTimeoutMs);	
    int absolutePosition = Devices.talonSrxWheelFrontRight.getSensorCollection().getPulseWidthPosition();
    // Mask out overflows, keep bottom 12 bits
    absolutePosition &= 0xFFF;
    if (Constants.kSensorPhase)	absolutePosition *= -1;
    if (Constants.kMotorInvert)	absolutePosition *= -1;
    
    Devices.talonSrxWheelFrontRight.setSelectedSensorPosition(absolutePosition, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing EncoderMovement default command...");

        IdleBaller defaultCmd = new IdleBaller();
        setDefaultCommand(defaultCmd);
    }

    // Stop all the drive motors
    public void stop() {
        Devices.talonSrxWheelRearRight.stopMotor();
    }

    // Opens or closes the Baller gate based on speed
    public void gate(double speed) {
        Devices.talonSrxWheelRearRight.set(speed);
    }
}

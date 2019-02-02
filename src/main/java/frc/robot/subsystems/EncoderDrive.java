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
//import frc.robot.commands.AutoEncoderDrive;
import frc.robot.commands.IdleEncoderDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.smartdashboard.*;

/**
 * Add your docs here.
 */
public class EncoderDrive extends Subsystem{

    public EncoderDrive(){

        int absolutePosition = Devices.talonSrxWheelFrontRight.getSensorCollection().getPulseWidthPosition();
        // Mask out overflows, keep bottom 12 bits
        absolutePosition &= 0xFFF;
        if (Constants.kSensorPhase)	absolutePosition *= -1;
        if (Constants.kMotorInvert)	absolutePosition *= -1;

        Logger.debug("Constructing EncoderDrive...");

        //Config TalonSRX encoder     
        Devices.talonSrxWheelRearRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, Constants.kPIDLoopPrimary, Constants.kTimeoutMs);
        Devices.talonSrxWheelRearRight.setSensorPhase(false);
        Devices.talonSrxWheelRearRight.setInverted(false);
        Devices.talonSrxWheelRearRight.configNominalOutputForward(0, Constants.kTimeoutMs);
        Devices.talonSrxWheelRearRight.configNominalOutputReverse(0, Constants.kTimeoutMs);
        Devices.talonSrxWheelRearRight.configPeakOutputForward(1, Constants.kTimeoutMs);
        Devices.talonSrxWheelRearRight.configPeakOutputReverse(-1, Constants.kTimeoutMs);
        Devices.talonSrxWheelRearRight.configAllowableClosedloopError(Constants.kPIDLoopPrimary, 0, Constants.kTimeoutMs);

        Devices.talonSrxWheelRearRight.config_kF(Constants.kPIDLoopPrimary, 0.0, Constants.kTimeoutMs);
        Devices.talonSrxWheelRearRight.config_kP(Constants.kPIDLoopPrimary, 0.15, Constants.kTimeoutMs);
        Devices.talonSrxWheelRearRight.config_kI(Constants.kPIDLoopPrimary, 0.001, Constants.kTimeoutMs);
        Devices.talonSrxWheelRearRight.config_kD(Constants.kPIDLoopPrimary, 1.5, Constants.kTimeoutMs);	

        
        Devices.talonSrxWheelRearRight.setSelectedSensorPosition(absolutePosition, Constants.kPIDLoopPrimary, Constants.kTimeoutMs);
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing EncoderMovement default command...");

        IdleEncoderDrive defaultCmd = new IdleEncoderDrive();
        setDefaultCommand(defaultCmd);
    }

    // Stop all the drive motors
    public void stop() {
        Devices.talonSrxWheelRearRight.stopMotor();
    }

    // Rotates the motor for a certain amount of raw encoder units
    // One rotations equals 4096 raw units
    public void driveRotations(double rotations) {
        double tickCount = rotations*Constants.kRedlineEncoderTpr;
        Devices.talonSrxWheelRearRight.set(ControlMode.Position, tickCount);
    }

    public int getPosition(){
        return Devices.talonSrxWheelRearRight.getSelectedSensorPosition(Constants.kPIDLoopPrimary); 
    }

    public int getVelocity(){
        return Devices.talonSrxWheelRearRight.getSelectedSensorVelocity(Constants.kPIDLoopPrimary);
    }

    // Resets the encoder position
    public void resetEncoderPosition(){
        Devices.talonSrxWheelRearRight.setSelectedSensorPosition(0, Constants.kPIDLoopPrimary, Constants.kTimeoutMs);
    }

    public static boolean isPositionMet(){
        int velocity = Devices.talonSrxWheelRearRight.getSensorCollection().getPulseWidthVelocity();
        if(velocity == 0){
            return true;
        }
        else{
            return false;
        }
    }
}

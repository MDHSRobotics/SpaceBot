/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.helpers.EncoderConstants;
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
      /* choose so that Talon does not report sensor out of phase */
      public static boolean kSensorPhase = false;

      /* choose based on what direction you want to be positive,
          this does not affect motor invert. */
      public static boolean kMotorInvert = false;

    public EncoderDrive(){

        int absolutePosition = Devices.talonSrxWheelFrontRight.getSensorCollection().getPulseWidthPosition();
        // Mask out overflows, keep bottom 12 bits
        absolutePosition &= 0xFFF;
        if (kSensorPhase)	absolutePosition *= -1;
        if (kMotorInvert)	absolutePosition *= -1;

        Logger.debug("Constructing EncoderDrive...");

        //Config TalonSRX encoder     
        Devices.talonSrxWheelRearRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
        Devices.talonSrxWheelRearRight.setSensorPhase(false);
        Devices.talonSrxWheelRearRight.setInverted(false);
        Devices.talonSrxWheelRearRight.configNominalOutputForward(0, EncoderConstants.kTimeoutMs);
        Devices.talonSrxWheelRearRight.configNominalOutputReverse(0, EncoderConstants.kTimeoutMs);
        Devices.talonSrxWheelRearRight.configPeakOutputForward(1, EncoderConstants.kTimeoutMs);
        Devices.talonSrxWheelRearRight.configPeakOutputReverse(-1, EncoderConstants.kTimeoutMs);
        Devices.talonSrxWheelRearRight.configAllowableClosedloopError(EncoderConstants.kPIDLoopPrimary, 0, EncoderConstants.kTimeoutMs);

        Devices.talonSrxWheelRearRight.config_kF(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
        Devices.talonSrxWheelRearRight.config_kP(EncoderConstants.kPIDLoopPrimary, 0.15, EncoderConstants.kTimeoutMs);
        Devices.talonSrxWheelRearRight.config_kI(EncoderConstants.kPIDLoopPrimary, 0.001, EncoderConstants.kTimeoutMs);
        Devices.talonSrxWheelRearRight.config_kD(EncoderConstants.kPIDLoopPrimary, 1.5, EncoderConstants.kTimeoutMs);	

        
        Devices.talonSrxWheelRearRight.setSelectedSensorPosition(absolutePosition, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
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
        double tickCount = rotations*EncoderConstants.kRedlineEncoderTpr;
        Devices.talonSrxWheelRearRight.set(ControlMode.Position, tickCount);
    }

    public int getPosition(){
        return Devices.talonSrxWheelRearRight.getSelectedSensorPosition(EncoderConstants.kPIDLoopPrimary); 
    }

    public int getVelocity(){
        return Devices.talonSrxWheelRearRight.getSelectedSensorVelocity(EncoderConstants.kPIDLoopPrimary);
    }

    // Resets the encoder position
    public void resetEncoderPosition(){
        Devices.talonSrxWheelRearRight.setSelectedSensorPosition(0, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
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

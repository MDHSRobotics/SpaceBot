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
        Devices.talonSrxWheelFrontRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
        Devices.talonSrxWheelFrontRight.setSensorPhase(Constants.kSensorPhase);
        Devices.talonSrxWheelFrontRight.setInverted(Constants.kMotorInvert);
        Devices.talonSrxWheelFrontRight.configNominalOutputForward(0, Constants.kTimeoutMs);
        Devices.talonSrxWheelFrontRight.configNominalOutputReverse(0, Constants.kTimeoutMs);
        Devices.talonSrxWheelFrontRight.configPeakOutputForward(1, Constants.kTimeoutMs);
        Devices.talonSrxWheelFrontRight.configPeakOutputReverse(-1, Constants.kTimeoutMs);
        Devices.talonSrxWheelFrontRight.configAllowableClosedloopError(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);

        Devices.talonSrxWheelFrontRight.config_kF(Constants.kPIDLoopIdx, 0.0, Constants.kTimeoutMs);
        Devices.talonSrxWheelFrontRight.config_kP(Constants.kPIDLoopIdx, 0.1, Constants.kTimeoutMs);
        Devices.talonSrxWheelFrontRight.config_kI(Constants.kPIDLoopIdx, 0.0, Constants.kTimeoutMs);
        Devices.talonSrxWheelFrontRight.config_kD(Constants.kPIDLoopIdx, 0.0, Constants.kTimeoutMs);	

        
        Devices.talonSrxWheelFrontRight.setSelectedSensorPosition(absolutePosition, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
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

    // Opens or closes the Baller gate based on speed
    public void positionDrive(double distance) {
        Devices.talonSrxWheelRearRight.set(ControlMode.Position, distance);
    }

    public int getPosition(){
        return Devices.talonSrxWheelRearRight.getSelectedSensorPosition(Constants.kPIDLoopIdx); 
    }

    // Resets the encoder position
    public void resetEncoderPosition(){
        Devices.talonSrxWheelRearRight.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    }

    public boolean isPositionMet(){
        int velocity = Devices.talonSrxWheelRearRight.getSensorCollection().getPulseWidthVelocity();
        if(velocity == 0){
            return true;
        }
        else{
            return false;
        }
    }
}

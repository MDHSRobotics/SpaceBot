
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.HatcherStop;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
// import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.helpers.Logger;
import frc.robot.helpers.EncoderConstants;
import frc.robot.Devices;
import frc.robot.Robot;


// Hatcher subsystem, for grabbing and releasing hatches
public class Hatcher extends Subsystem {

    private boolean m_talonsAreConnected = false;
    double targetRotations = 0.8;
    double targetPositionUnits;	
    static final double k_stopThreshold = 10;

      /* choose so that Talon does not report sensor out of phase */
      public static boolean kSensorPhase = false;
      

      /* choose based on what direction you want to be positive,
          this does not affect motor invert. */
      public static boolean kMotorInvert = true;
    
      public boolean hatchToggle = false;

    public Hatcher() {
        Logger.debug("Constructing Subsystem: Hatcher...");

        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxHatcher);
        Devices.talonSrxHatcher.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
        Devices.talonSrxHatch.configFactoryDefault();

        Devices.talonSrxHatch.configPeakCurrentDuration(40, 20);
        Devices.talonSrxHatch.configPeakCurrentLimit(11, 20);
        Devices.talonSrxHatch.configContinuousCurrentLimit(10, 20);

        Devices.talonSrxHatch.configNominalOutputForward(0);
        Devices.talonSrxHatch.configNominalOutputReverse(0);
        Devices.talonSrxHatch.configPeakOutputForward(0.5);
        Devices.talonSrxHatch.configPeakOutputReverse(-0.5);

        Devices.talonSrxHatch.configMotionAcceleration(6000, 20);
        Devices.talonSrxHatch.configMotionCruiseVelocity(15000, 20);

        //Config TalonSRX Redline encoder     
        Devices.talonSrxHatch.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
        Devices.talonSrxHatch.setSensorPhase(kSensorPhase);
        Devices.talonSrxHatch.setInverted(kMotorInvert);
        Devices.talonSrxHatch.configAllowableClosedloopError(0, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);

        Devices.talonSrxHatch.config_kF(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
        Devices.talonSrxHatch.config_kP(EncoderConstants.kPIDLoopPrimary, 0.06, EncoderConstants.kTimeoutMs);
        Devices.talonSrxHatch.config_kI(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
        Devices.talonSrxHatch.config_kD(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);	

        //Reset Encoder Position 
        Devices.talonSrxHatch.setSelectedSensorPosition(0, 0, 20);
        int absolutePosition = Devices.talonSrxHatch.getSensorCollection().getPulseWidthPosition();
        absolutePosition &= 0xFFF;
		if (kSensorPhase)	absolutePosition *= -1;
        if (kMotorInvert)	absolutePosition *= -1;
        // Set the quadrature (relative) sensor to match absolute
		Devices.talonSrxHatch.setSelectedSensorPosition(absolutePosition, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Hatcher DefaultCommand -> HatcherStop...");

        setDefaultCommand(new HatcherStop());
    }

    // Stop all the drive motors
    public void stop() {
        if (m_talonsAreConnected) {
            Devices.talonSrxHatcher.stopMotor();
        }
    }
    

    public int getPosition(){
        return Devices.talonSrxHatch.getSelectedSensorPosition(); 
    }

    public int getVelocity(){
        return Devices.talonSrxHatch.getSelectedSensorVelocity();
    }

    public void resetEncoderPosition(){
        Devices.talonSrxHatch.setSelectedSensorPosition(0, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
    }

    public void driveStatic(){
         Devices.talonSrxHatch.set(0.2);
    }  

    public void clawOpen(){
		Devices.talonSrxHatch.setSelectedSensorPosition(0, 0, 20);
        targetPositionUnits = targetRotations * EncoderConstants.kRedlineEncoderTpr;

        Devices.talonSrxHatch.set(ControlMode.MotionMagic, targetPositionUnits);
        Logger.debug("Target Open Position: " + targetPositionUnits);
    }
			   
    public void clawClose(){
        Devices.talonSrxHatch.setSelectedSensorPosition(0, 0, 20);    
        targetPositionUnits = -(targetRotations * EncoderConstants.kRedlineEncoderTpr);
        Devices.talonSrxHatch.set(ControlMode.MotionMagic, targetPositionUnits);
        Logger.debug("Target Close Position: " + targetPositionUnits);
       // Devices.talonSrxHatch.stopMotor();
    }

    public void setHatchToggle(){
        hatchToggle = !hatchToggle;
    }

    public boolean getHatchToggle(){
        return hatchToggle;
    }

    public boolean isStopped(){
            double currentVelocity = Devices.talonSrxHatch.getSelectedSensorVelocity();
             Logger.debug("Position: " + currentVelocity);
            return (Math.abs(currentVelocity) < k_stopThreshold);
    }

    public boolean isPositionMet(){
        int currentPosition = Devices.talonSrxHatch.getSelectedSensorPosition();

        targetPositionUnits = targetRotations * EncoderConstants.kRedlineEncoderTpr;
        Logger.debug("Position: " + currentPosition);

        return (Math.abs((Math.abs(currentPosition) - targetPositionUnits)) < 600); 
    }
}
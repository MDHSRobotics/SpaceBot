
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
    double targetRotations = 10;
    double targetPositionUnits;	
    static final double k_stopThreshold = 10;

      /* choose so that Talon does not report sensor out of phase */
      public static boolean kSensorPhase = true;

      /* choose based on what direction you want to be positive,
          this does not affect motor invert. */
      public static boolean kMotorInvert = false;
    
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
        //Devices.talonSrxHatch.configMotion

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

    public void joystickMove(double joystickValue){
        Devices.talonSrxHatch.set(ControlMode.PercentOutput, joystickValue);
        
    }

    public void driveStatic(){
         Devices.talonSrxHatch.set(-0.2);
    }  

    		// On button1 press, enter closed-loop mode on target position (but ignore sequential presses)
    public void clawOpen(){

		Devices.talonSrxHatch.setSelectedSensorPosition(0, 0, 20);

        targetPositionUnits = targetRotations * EncoderConstants.kRedlineEncoderTpr;
        Devices.talonSrxHatch.set(ControlMode.Position, targetPositionUnits);
        Logger.debug("Target Position: " + targetPositionUnits);
			   
        }

        public void clawClose(){

        Devices.talonSrxHatch.set(ControlMode.Position, 0);
        //Logger.debug("Target Position: " + targetPositionUnits);
        }

        public boolean isStopped(){
            double currentVelocity = Devices.talonSrxHatch.getSelectedSensorVelocity();
             Logger.debug("Position: " + currentVelocity);
            return (Math.abs(currentVelocity) < k_stopThreshold);
        }
}
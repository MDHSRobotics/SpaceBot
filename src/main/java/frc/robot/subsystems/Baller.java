
package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.BallerStop;
import frc.robot.helpers.Logger;
import frc.robot.Devices;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import frc.robot.helpers.EncoderConstants;

// Cargo ball subsystem
public class Baller extends Subsystem {
    double targetRotations = 3.73;
    double targetPositionUnits;	
    private static final double k_stopThreshold = 10;
    boolean ballToggle = false;


    private boolean m_talonsAreConnected = false;
    private double m_secondsFromNeutralToFull = 1.0;
    private int m_timeoutMS = 10;
    private double m_speed = 0.2; 

      /* choose so that Talon does not report sensor out of phase */
      public static boolean kSensorPhase = false;

      /* choose based on what direction you want to be positive,
          this does not affect motor invert. */
      public static boolean kMotorInvert = false;

    public Baller() {
        Logger.debug("Constructing Subsystem: Baller...");

        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxBaller);
        Devices.talonSrxBaller.configOpenloopRamp(m_secondsFromNeutralToFull, m_timeoutMS);
        Devices.talonSrxBaller.configFactoryDefault();

        Devices.talonSrxBaller.configPeakCurrentDuration(40, 20);
        Devices.talonSrxBaller.configPeakCurrentLimit(11, 20);
        Devices.talonSrxBaller.configContinuousCurrentLimit(10, 20);

        Devices.talonSrxBaller.configNominalOutputForward(0);
        Devices.talonSrxBaller.configNominalOutputReverse(0);
        Devices.talonSrxBaller.configPeakOutputForward(0.3);
        Devices.talonSrxBaller.configPeakOutputReverse(-0.3);

        Devices.talonSrxBaller.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
        Devices.talonSrxBaller.setSensorPhase(kSensorPhase);
        Devices.talonSrxBaller.setInverted(kMotorInvert);
        Devices.talonSrxBaller.configAllowableClosedloopError(0, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);

        Devices.talonSrxBaller.config_kF(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
        Devices.talonSrxBaller.config_kP(EncoderConstants.kPIDLoopPrimary, 0.06, EncoderConstants.kTimeoutMs);
        Devices.talonSrxBaller.config_kI(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
        Devices.talonSrxBaller.config_kD(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);	

        //Reset Encoder Position 
        Devices.talonSrxBaller.setSelectedSensorPosition(0, 0, 20);
        int absolutePosition = Devices.talonSrxBaller.getSensorCollection().getPulseWidthPosition();
        absolutePosition &= 0xFFF;
		if (kSensorPhase)	absolutePosition *= -1;
        if (kMotorInvert)	absolutePosition *= -1;
        // Set the quadrature (relative) sensor to match absolute
        Devices.talonSrxBaller.setSelectedSensorPosition(absolutePosition, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
        
        Devices.talonSrxBaller.configMotionAcceleration(1500, 20);
        Devices.talonSrxBaller.configMotionCruiseVelocity(4000, 20);
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Baller DefaultCommand -> BallerStop...");

        setDefaultCommand(new BallerStop());
    }

    // Stop all the drive motors
    public void stop() {
        if (m_talonsAreConnected) {
            Devices.talonSrxBaller.stopMotor();
        }
    }

    // Holds the ball
    public void holdBall() {
        if (m_talonsAreConnected) {
            Devices.talonSrxBaller.set(-m_speed);
        }
    }

    public int getPosition(){
        return Devices.talonSrxBaller.getSelectedSensorPosition(); 
    }

    public int getVelocity(){
        return Devices.talonSrxBaller.getSelectedSensorVelocity();
    }

    public void resetEncoderPosition(){
        Devices.talonSrxBaller.setSelectedSensorPosition(0, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
    }

    public void ballRaise(){
        Devices.talonSrxBaller.setSelectedSensorPosition(0, 0, 20);
        targetPositionUnits = targetRotations * EncoderConstants.kRedlineEncoderTpr;

        Devices.talonSrxBaller.set(ControlMode.Position, targetPositionUnits);
        Logger.debug("Target Position: " + targetPositionUnits);
    }

    public void ballClose(){
        Devices.talonSrxBaller.setSelectedSensorPosition(0, 0, 20);
        targetPositionUnits = -(targetRotations * EncoderConstants.kRedlineEncoderTpr);

        Devices.talonSrxBaller.set(ControlMode.Position, targetPositionUnits);
        Logger.debug("Target Position: " + targetPositionUnits);
    }

    public void setBallToggle(){
        ballToggle = !ballToggle;
    }

    public boolean getBallToggle(){
        return ballToggle;
    }

    public boolean isStopped(){
        double currentVelocity = Devices.talonSrxBaller.getSelectedSensorVelocity();
         Logger.debug("Position: " + currentVelocity);
        return (Math.abs(currentVelocity) < k_stopThreshold);
    }

    public boolean isPositionMet(){
        int currentPosition = Devices.talonSrxBaller.getSelectedSensorPosition();

        targetPositionUnits = targetRotations * EncoderConstants.kRedlineEncoderTpr;
        Logger.debug("Position: " + currentPosition);

        return (Math.abs((Math.abs(currentPosition) - targetPositionUnits)) < 600); 
    }
}

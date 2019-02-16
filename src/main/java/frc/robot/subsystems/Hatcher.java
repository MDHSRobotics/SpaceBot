
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.HatcherStop;
import frc.robot.helpers.EncoderConstants;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Hatcher subsystem, for grabbing and releasing hatches
public class Hatcher extends Subsystem {

    private boolean m_talonsAreConnected = false;

    // TODO: Be explicit. Are these public or private? Make them private unless they need to be public. Name them accordingly.
    double targetRotations = 0.8;
    double targetPositionUnits;	
    static final double k_stopThreshold = 10;

    // Choose so that Talon does not report sensor out of phase
    public static boolean kSensorPhase = false;
    
    // Choose based on what direction you want to be positive, this does not affect motor invert
    public static boolean kMotorInvert = true;

    public boolean hatchIsGrabbed = false;

    // Constructor
    public Hatcher() {
        Logger.debug("Constructing Subsystem: Hatcher...");

        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxHatcher);
        if (m_talonsAreConnected) {
            Devices.talonSrxHatcher.configFactoryDefault();

            Devices.talonSrxHatcher.configPeakCurrentDuration(40, 20);
            Devices.talonSrxHatcher.configPeakCurrentLimit(11, 20);
            Devices.talonSrxHatcher.configContinuousCurrentLimit(10, 20);

            Devices.talonSrxHatcher.configNominalOutputForward(0);
            Devices.talonSrxHatcher.configNominalOutputReverse(0);
            Devices.talonSrxHatcher.configPeakOutputForward(0.5);
            Devices.talonSrxHatcher.configPeakOutputReverse(-0.5);

            Devices.talonSrxHatcher.configMotionAcceleration(6000, 20);
            Devices.talonSrxHatcher.configMotionCruiseVelocity(15000, 20);

            // Config TalonSRX Redline encoder
            Devices.talonSrxHatcher.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
            Devices.talonSrxHatcher.setSensorPhase(kSensorPhase);
            Devices.talonSrxHatcher.setInverted(kMotorInvert);
            Devices.talonSrxHatcher.configAllowableClosedloopError(0, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);

            Devices.talonSrxHatcher.config_kF(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
            Devices.talonSrxHatcher.config_kP(EncoderConstants.kPIDLoopPrimary, 0.06, EncoderConstants.kTimeoutMs);
            Devices.talonSrxHatcher.config_kI(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
            Devices.talonSrxHatcher.config_kD(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);

            // Reset Encoder Position 
            Devices.talonSrxHatcher.setSelectedSensorPosition(0, 0, 20);
            SensorCollection sensorCol = Devices.talonSrxHatcher.getSensorCollection();
            int absolutePosition = sensorCol.getPulseWidthPosition();
            absolutePosition &= 0xFFF;
            if (kSensorPhase) absolutePosition *= -1;
            if (kMotorInvert) absolutePosition *= -1;
            // Set the quadrature (relative) sensor to match absolute
            Devices.talonSrxHatcher.setSelectedSensorPosition(absolutePosition, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
        }
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Hatcher DefaultCommand -> HatcherStop...");

        setDefaultCommand(new HatcherStop());
    }

    // Stop the Hatcher motor
    public void stop() {
        if (m_talonsAreConnected) {
            Devices.talonSrxHatcher.stopMotor();
        }
    }

    // Open the Hatcher claw to grab the hatch
    public void grabHatch() {
        if (m_talonsAreConnected) {
            Devices.talonSrxHatcher.setSelectedSensorPosition(0, 0, 20);
            targetPositionUnits = targetRotations * EncoderConstants.kRedlineEncoderTpr;
            Devices.talonSrxHatcher.set(ControlMode.MotionMagic, targetPositionUnits);
            Logger.debug("Hatcher -> Target Grab Position: " + targetPositionUnits);
        }
    }

    // Close the Hatcher claw to release the hatch
    public void releaseHatch() {
        if (m_talonsAreConnected) {
            Devices.talonSrxHatcher.setSelectedSensorPosition(0, 0, 20);
            targetPositionUnits = -(targetRotations * EncoderConstants.kRedlineEncoderTpr);
            Devices.talonSrxHatcher.set(ControlMode.MotionMagic, targetPositionUnits);
            Logger.debug("Hatcher -> Target Release Position: " + targetPositionUnits);
        }
    }

    // Get the current Hatcher claw motor velocity
    public int getVelocity() {
        if (!m_talonsAreConnected) return -1; // TODO: Is there a better return value?
        return Devices.talonSrxHatcher.getSelectedSensorVelocity();
    }

    // Get the current Hatcher claw motor position
    public int getPosition() {
        if (!m_talonsAreConnected) return -1; // TODO: Is there a better return value?
        return Devices.talonSrxHatcher.getSelectedSensorPosition();
    }

    // Return whether or not the motor has reached the encoded position
    public boolean isPositionMet() {
        int currentPosition = getPosition();
        if (currentPosition == -1) return true;
        targetPositionUnits = targetRotations * EncoderConstants.kRedlineEncoderTpr;
        return (Math.abs((Math.abs(currentPosition) - targetPositionUnits)) < 600);
    }

    // Toggle the hatchIsGrabbed state
    public void toggleHatchGrabbed() {
        hatchIsGrabbed = !hatchIsGrabbed;
    }

    //--------//
    // Unused //
    //--------//

    public void resetEncoderPosition() {
        if (m_talonsAreConnected) {
            Devices.talonSrxHatcher.setSelectedSensorPosition(0, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
        }
    }

    public void driveStatic() {
        if (m_talonsAreConnected) {
            Devices.talonSrxHatcher.set(0.2);
        }
    }

    public boolean isStopped() {
        if (!m_talonsAreConnected) return true;
        double currentVelocity = Devices.talonSrxHatcher.getSelectedSensorVelocity();
        return (Math.abs(currentVelocity) < k_stopThreshold);
    }

}
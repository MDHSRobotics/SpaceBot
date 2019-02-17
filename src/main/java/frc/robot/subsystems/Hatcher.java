
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

    private double m_targetRotations = 0.572; //Calculation: 0.572
    private double m_targetPositionUnits;
    // Choose so that Talon does not report sensor out of phase
    private boolean m_sensorPhase = true; //false -- TODO: Why is this comment out of sink with the value, but not so on Baller?
    // Choose based on what direction you want to be positive, this does not affect motor invert
    private boolean m_motorInvert = true;

    // The public property to determine the Hatcher state
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
            Devices.talonSrxHatcher.setSensorPhase(m_sensorPhase);
            Devices.talonSrxHatcher.setInverted(m_motorInvert);
            Devices.talonSrxHatcher.configAllowableClosedloopError(0, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);

            Devices.talonSrxHatcher.config_kF(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
            Devices.talonSrxHatcher.config_kP(EncoderConstants.kPIDLoopPrimary, 0.065, EncoderConstants.kTimeoutMs);
            Devices.talonSrxHatcher.config_kI(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
            Devices.talonSrxHatcher.config_kD(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);

            // Reset Encoder Position 
            Devices.talonSrxHatcher.setSelectedSensorPosition(0, 0, 20);
            SensorCollection sensorCol = Devices.talonSrxHatcher.getSensorCollection();
            int absolutePosition = sensorCol.getPulseWidthPosition();
            absolutePosition &= 0xFFF;
            if (m_sensorPhase) absolutePosition *= -1;
            if (m_motorInvert) absolutePosition *= -1;
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
            m_targetPositionUnits = m_targetRotations * EncoderConstants.kRedlineEncoderTpr;
            Devices.talonSrxHatcher.set(ControlMode.MotionMagic, m_targetPositionUnits);
            Logger.debug("Hatcher -> Target Grab Position: " + m_targetPositionUnits);
        }
    }

    // Close the Hatcher claw to release the hatch
    public void releaseHatch() {
        if (m_talonsAreConnected) {
            Devices.talonSrxHatcher.setSelectedSensorPosition(0, 0, 20);
            m_targetPositionUnits = -(m_targetRotations * EncoderConstants.kRedlineEncoderTpr);
            Devices.talonSrxHatcher.set(ControlMode.MotionMagic, m_targetPositionUnits);
            Logger.debug("Hatcher -> Target Release Position: " + m_targetPositionUnits);
        }
    }

    // Get the current Hatcher claw motor velocity
    public int getVelocity() {
        return Devices.talonSrxHatcher.getSelectedSensorVelocity();
    }

    // Get the current Hatcher claw motor position
    public int getPosition() {
        return Devices.talonSrxHatcher.getSelectedSensorPosition();
    }

    // Return whether or not the motor has reached the encoded position
    public boolean isPositionMet() {
        if (!m_talonsAreConnected) return true;
        int currentPosition = getPosition();
        m_targetPositionUnits = m_targetRotations * EncoderConstants.kRedlineEncoderTpr;
        return (Math.abs((Math.abs(currentPosition) - m_targetPositionUnits)) < 700);
    }

    // Toggle the hatchIsGrabbed state
    public void toggleHatchGrabbed() {
        hatchIsGrabbed = !hatchIsGrabbed;
    }

    //---------//
    // Testing //
    //---------//

    public void driveStatic() {
        if (m_talonsAreConnected) {
            Devices.talonSrxHatcher.set(0.2);
        }
    }

}
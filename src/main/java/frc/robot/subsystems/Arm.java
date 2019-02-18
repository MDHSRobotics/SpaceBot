package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.ArmStop;
import frc.robot.helpers.EncoderConstants;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Subsystem to move the Arm down into two different set positions,
// plus a little extra controlled by the user.
public class Arm extends Subsystem {

    public enum ArmPosition {
        START, HALF, FULL, MORE
    }

    // The public property to determine the Arm state
    public ArmPosition currentArmPosition = ArmPosition.START;

    // Encoder variables
    private double m_targetRotationHalf = 0.125;	
    private double m_targetRotationFull = 0.25;
    // Choose so that Talon does not report sensor out of phase
    public boolean m_sensorPhase = false;
    // Choose based on what direction you want to be positive, this does not affect motor invert
    public boolean m_motorInvert = false;

    // The Talon connection state, to prevent watchdog warnings during testing
    private boolean m_talonsAreConnected = false;

    public Arm() {
        Logger.debug("Contructing Subsystem: Arm...");

        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxArm);
        if (m_talonsAreConnected) {
            Devices.talonSrxArm.configFactoryDefault();

            Devices.talonSrxArm.configPeakCurrentDuration(40, 20);
            Devices.talonSrxArm.configPeakCurrentLimit(11, 20);
            Devices.talonSrxArm.configContinuousCurrentLimit(10, 20);

            Devices.talonSrxArm.configNominalOutputForward(0);
            Devices.talonSrxArm.configNominalOutputReverse(0);
            Devices.talonSrxArm.configPeakOutputForward(0.3);
            Devices.talonSrxArm.configPeakOutputReverse(-0.3);

            Devices.talonSrxArm.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
            Devices.talonSrxArm.setSensorPhase(m_sensorPhase);
            Devices.talonSrxArm.setInverted(m_motorInvert);
            Devices.talonSrxArm.configAllowableClosedloopError(0, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);

            Devices.talonSrxArm.config_kF(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
            Devices.talonSrxArm.config_kP(EncoderConstants.kPIDLoopPrimary, 0.06, EncoderConstants.kTimeoutMs);
            Devices.talonSrxArm.config_kI(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);
            Devices.talonSrxArm.config_kD(EncoderConstants.kPIDLoopPrimary, 0.0, EncoderConstants.kTimeoutMs);

            // Reset Encoder Position 
            Devices.talonSrxArm.setSelectedSensorPosition(0, 0, 20);
            SensorCollection sensorCol = Devices.talonSrxArm.getSensorCollection();
            int absolutePosition = sensorCol.getPulseWidthPosition();
            absolutePosition &= 0xFFF;
            if (m_sensorPhase) absolutePosition *= -1;
            if (m_motorInvert) absolutePosition *= -1;
            // Set the quadrature (relative) sensor to match absolute
            Devices.talonSrxArm.setSelectedSensorPosition(absolutePosition, EncoderConstants.kPIDLoopPrimary, EncoderConstants.kTimeoutMs);
            
            Devices.talonSrxArm.configMotionAcceleration(1500, 20);
            Devices.talonSrxArm.configMotionCruiseVelocity(4000, 20);
        }
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Arm DefaultCommand -> ArmStop...");

        setDefaultCommand(new ArmStop());
    }

    // Stop the Arm motor
    public void stop() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxArm.stopMotor();
    }

    // TODO: The subsystem needs a "resetPosition" method

    // Lowers the Arm to the encoded "half" position
    public void lowerHalf() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxArm.setSelectedSensorPosition(0, 0, 20);
        double targetPositionUnits = m_targetRotationHalf * EncoderConstants.kRedlineEncoderTpr;
        Logger.debug("Arm -> Target Lower Half Position: " + targetPositionUnits);
        Devices.talonSrxArm.set(ControlMode.Position, targetPositionUnits);
    }

    // Lowers the Arm to the encoded "full" position
    public void lowerFull() {
        if (!m_talonsAreConnected) return;
        double targetPositionUnits = m_targetRotationFull * EncoderConstants.kRedlineEncoderTpr;
        Logger.debug("Arm -> Target Lower Full Position: " + targetPositionUnits);
        Devices.talonSrxArm.set(ControlMode.Position, targetPositionUnits);
    }

    // Lowers the Arm beyond the full encoded position, based on given speed
    // TODO: Use encoders to set a limit? There will also be a physical stop
    public void lowerMore(double speed) {
        if (m_talonsAreConnected) {
            Devices.talonSrxArm.set(speed);
        }
    }

    // Get the current motor velocity
    public int getVelocity() {
        return Devices.talonSrxArm.getSelectedSensorVelocity();
    }

    // Get the current motor position
    public int getPosition() {
        return Devices.talonSrxArm.getSelectedSensorPosition();
    }

    // Return whether or not the motor has reached the encoded "half" position
    public boolean isPositionHalfMet() {
        if (!m_talonsAreConnected) return true;
        int currentPosition = getPosition();
        double targetPositionUnits = m_targetRotationHalf * EncoderConstants.kRedlineEncoderTpr;
        return (Math.abs((Math.abs(currentPosition) - targetPositionUnits)) < 512);
    }

    // Return whether or not the motor has reached the encoded "full" position
    public boolean isPositionFullMet() {
        if (!m_talonsAreConnected) return true;
        int currentPosition = getPosition();
        double targetPositionUnits = m_targetRotationFull * EncoderConstants.kRedlineEncoderTpr;
        return (Math.abs((Math.abs(currentPosition) - targetPositionUnits)) < 800);
    }

}

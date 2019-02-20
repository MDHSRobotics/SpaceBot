
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.PulleyStop;
import frc.robot.consoles.Logger;
import frc.robot.Devices;
import frc.robot.helpers.EncoderConstants;


// Pulley subsystem for lifting the robot onto the platform
public class Pulley extends Subsystem {

    public enum PulleyPosition {
        DOWN, UP
    }

    // The public property to determine the Pulley state
    public PulleyPosition currentPulleyPosition = PulleyPosition.DOWN;

    // Motor constants
    private final double PULLEY_SPEED = .5;

    // Encoder constants
    private final double POSITION_TOLERANCE = 100;
    private final double TARGET_POSITION_RESET = 0;
    private final boolean SENSOR_PHASE = false; // So that Talon does not report sensor out of phase
    private final boolean MOTOR_INVERT = false; // Which direction you want to be positive; this does not affect motor invert

    // The Talon connection state, to prevent watchdog warnings during testing
    private boolean m_talonsAreConnected = false;

    public Pulley() {
        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxPulleyMaster);
        if (m_talonsAreConnected) {
            Devices.talonSrxPulleyMaster.configPeakCurrentDuration(40, 20);
            Devices.talonSrxPulleyMaster.configPeakCurrentLimit(11, 20);
            Devices.talonSrxPulleyMaster.configContinuousCurrentLimit(10, 20);

            Devices.talonSrxPulleyMaster.configNominalOutputForward(0);
            Devices.talonSrxPulleyMaster.configNominalOutputReverse(0);
            Devices.talonSrxPulleyMaster.configPeakOutputForward(0.5);
            Devices.talonSrxPulleyMaster.configPeakOutputReverse(-0.5);

            Devices.talonSrxPulleyMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, EncoderConstants.PID_LOOP_PRIMARY, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxPulleyMaster.setSensorPhase(SENSOR_PHASE);
            Devices.talonSrxPulleyMaster.setInverted(MOTOR_INVERT);
            Devices.talonSrxPulleyMaster.configAllowableClosedloopError(0, EncoderConstants.PID_LOOP_PRIMARY, EncoderConstants.TIMEOUT_MS);

            Devices.talonSrxPulleyMaster.config_kF(EncoderConstants.PID_LOOP_PRIMARY, 0.0, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxPulleyMaster.config_kP(EncoderConstants.PID_LOOP_PRIMARY, 0.0125, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxPulleyMaster.config_kI(EncoderConstants.PID_LOOP_PRIMARY, 0.0, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxPulleyMaster.config_kD(EncoderConstants.PID_LOOP_PRIMARY, 0.1, EncoderConstants.TIMEOUT_MS);

            // Reset Encoder Position 
            Devices.talonSrxPulleyMaster.setSelectedSensorPosition(0, 0, 20);
            SensorCollection sensorCol = Devices.talonSrxPulleyMaster.getSensorCollection();
            int absolutePosition = sensorCol.getPulseWidthPosition();
            absolutePosition &= 0xFFF;
            if (SENSOR_PHASE) absolutePosition *= -1;
            if (MOTOR_INVERT) absolutePosition *= -1;
            // Set the quadrature (relative) sensor to match absolute
            Devices.talonSrxPulleyMaster.setSelectedSensorPosition(absolutePosition, EncoderConstants.PID_LOOP_PRIMARY, EncoderConstants.TIMEOUT_MS);
            
            Devices.talonSrxPulleyMaster.configMotionAcceleration(6000, 20);
            Devices.talonSrxPulleyMaster.configMotionCruiseVelocity(15000, 20);
        }
    }

    @Override
    public void initDefaultCommand() {
        Logger.setup("Initializing Pulley DefaultCommand -> PulleyStop...");

        setDefaultCommand(new PulleyStop());
    }

    // Stop the Pulley motor
    public void stop() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxPulleyMaster.stopMotor();
    }

    public void lift() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxPulleyMaster.set(PULLEY_SPEED);
    }

    public void lower() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxPulleyMaster.set(-PULLEY_SPEED);
    }

    public void resetPulleyPosition() {
        if (!m_talonsAreConnected) return;
        Logger.info("Pulley -> Reset Position: " + TARGET_POSITION_RESET);
        Devices.talonSrxPulleyMaster.set(ControlMode.Position, TARGET_POSITION_RESET);
    }

    // Get the current motor position
    public int getPosition() {
        if (!m_talonsAreConnected) return 0;
        return Devices.talonSrxPulleyMaster.getSelectedSensorPosition();
    }

    public boolean isPulleyPositionResetMet() {
        if (!m_talonsAreConnected) return true;
        int currentPosition = getPosition();
        return (Math.abs(currentPosition) < POSITION_TOLERANCE);
    }

    public double getGyroAngle() {
        if (!m_talonsAreConnected) return 0;
        double gyroAngle = Devices.imuMecDrive.getAngleX();
        return gyroAngle;
    }

}

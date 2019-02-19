
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.HatcherStop;
import frc.robot.consoles.Logger;
import frc.robot.helpers.EncoderConstants;
import frc.robot.Devices;


// Hatcher subsystem, for grabbing and releasing hatches
public class Hatcher extends Subsystem {

    // The public property to determine the Hatcher state
    public boolean hatchIsGrabbed = false;

    // Encoder constants
    private final double TARGET_ROTATIONS = 0.572;
    private final boolean SENSOR_PHASE = false; // So that Talon does not report sensor out of phase
    private final boolean MOTOR_INVERT = false; // Which direction you want to be positive; this does not affect motor invert

    // The Talon connection state, to prevent watchdog warnings during testing
    private boolean m_talonsAreConnected = false;

    public Hatcher() {
        Logger.setup("Constructing Subsystem: Hatcher...");

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
            Devices.talonSrxHatcher.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, EncoderConstants.PID_LOOP_PRIMARY, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxHatcher.setSensorPhase(SENSOR_PHASE);
            Devices.talonSrxHatcher.setInverted(MOTOR_INVERT);
            Devices.talonSrxHatcher.configAllowableClosedloopError(0, EncoderConstants.PID_LOOP_PRIMARY, EncoderConstants.TIMEOUT_MS);

            Devices.talonSrxHatcher.config_kF(EncoderConstants.PID_LOOP_PRIMARY, 0.0, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxHatcher.config_kP(EncoderConstants.PID_LOOP_PRIMARY, 0.32, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxHatcher.config_kI(EncoderConstants.PID_LOOP_PRIMARY, 0.0, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxHatcher.config_kD(EncoderConstants.PID_LOOP_PRIMARY, 0.0, EncoderConstants.TIMEOUT_MS);

            // Reset Encoder Position 
            Devices.talonSrxHatcher.setSelectedSensorPosition(0, 0, 20);
            SensorCollection sensorCol = Devices.talonSrxHatcher.getSensorCollection();
            int absolutePosition = sensorCol.getPulseWidthPosition();
            absolutePosition &= 0xFFF;
            if (SENSOR_PHASE) absolutePosition *= -1;
            if (MOTOR_INVERT) absolutePosition *= -1;
            // Set the quadrature (relative) sensor to match absolute
            Devices.talonSrxHatcher.setSelectedSensorPosition(absolutePosition, EncoderConstants.PID_LOOP_PRIMARY, EncoderConstants.TIMEOUT_MS);
        }
    }

    @Override
    public void initDefaultCommand() {
        Logger.setup("Initializing Hatcher DefaultCommand -> HatcherStop...");

        setDefaultCommand(new HatcherStop());
    }

    // Stop the Hatcher motor
    public void stop() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxHatcher.stopMotor();
    }

    // Open the Hatcher claw to grab the hatch
    public void grabHatch() {
        if (!m_talonsAreConnected) return;
        double targetPositionUnits = TARGET_ROTATIONS * EncoderConstants.REDLIN_ENCODER_TPR;
        Logger.info("Hatcher -> Target Grab Position: " + targetPositionUnits);
        Devices.talonSrxHatcher.set(ControlMode.MotionMagic, targetPositionUnits);
    }

    // Close the Hatcher claw to release the hatch
    public void releaseHatch() {
        if (!m_talonsAreConnected) return;
        Logger.info("Hatcher -> Target Release Position: " + 0);
        Devices.talonSrxHatcher.set(ControlMode.MotionMagic, 0);
    }

    // Get the current Hatcher claw motor velocity
    public int getVelocity() {
        if (!m_talonsAreConnected) return 0;
        return Devices.talonSrxHatcher.getSelectedSensorVelocity();
    }

    // Get the current Hatcher claw motor position
    public int getPosition() {
        if (!m_talonsAreConnected) return 0;
        return Devices.talonSrxHatcher.getSelectedSensorPosition();
    }

    // Return whether or not the motor has reached the encoded position
    public boolean isPositionMet() {
        if (!m_talonsAreConnected) return true;
        int currentPosition = getPosition();
        double targetPositionUnits = TARGET_ROTATIONS * EncoderConstants.REDLIN_ENCODER_TPR;
        return (Math.abs((Math.abs(currentPosition) - targetPositionUnits)) < 0);
    }

    // Toggle the hatchIsGrabbed state
    public void toggleHatchGrabbed() {
        hatchIsGrabbed = !hatchIsGrabbed;
    }

    //---------//
    // Testing //
    //---------//

    public void driveStatic() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxHatcher.set(0.2);
    }

}

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.BallerStop;
import frc.robot.helpers.EncoderConstants;
import frc.robot.helpers.Logger;
import frc.robot.Devices;


// Baller subsystem, for holding and tossing cargo balls
public class Baller extends Subsystem {

    // The public property to determine the Baller state
    public boolean ballIsTossed = false;

    // Encoder constants
    private final double TARGET_ROTATIONS = 4.44; //Calculation: 4.44 -- TODO: Why is this comment out of sync with the value, but not so on Hatcher?
    private final boolean SENSOR_PHASE = false; // So that Talon does not report sensor out of phase
    private final boolean MOTOR_INVERT = false; // Which direction you want to be positive; this does not affect motor invert

    // The Talon connection state, to prevent watchdog warnings during testing
    private boolean m_talonsAreConnected = false;

    public Baller() {
        Logger.debug("Constructing Subsystem: Baller...");

        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxBaller);
        if (m_talonsAreConnected) {
            Devices.talonSrxBaller.configFactoryDefault();

            Devices.talonSrxBaller.configPeakCurrentDuration(40, 20);
            Devices.talonSrxBaller.configPeakCurrentLimit(11, 20);
            Devices.talonSrxBaller.configContinuousCurrentLimit(10, 20);

            Devices.talonSrxBaller.configNominalOutputForward(0);
            Devices.talonSrxBaller.configNominalOutputReverse(0);
            Devices.talonSrxBaller.configPeakOutputForward(0.5);
            Devices.talonSrxBaller.configPeakOutputReverse(-0.5);

            Devices.talonSrxBaller.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, EncoderConstants.PID_LOOP_PRIMARY, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxBaller.setSensorPhase(SENSOR_PHASE);
            Devices.talonSrxBaller.setInverted(MOTOR_INVERT);
            Devices.talonSrxBaller.configAllowableClosedloopError(0, EncoderConstants.PID_LOOP_PRIMARY, EncoderConstants.TIMEOUT_MS);

            Devices.talonSrxBaller.config_kF(EncoderConstants.PID_LOOP_PRIMARY, 0.0, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxBaller.config_kP(EncoderConstants.PID_LOOP_PRIMARY, 0.0125, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxBaller.config_kI(EncoderConstants.PID_LOOP_PRIMARY, 0.0, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxBaller.config_kD(EncoderConstants.PID_LOOP_PRIMARY, 0.1, EncoderConstants.TIMEOUT_MS);

            // Reset Encoder Position 
            Devices.talonSrxBaller.setSelectedSensorPosition(0, 0, 20);
            SensorCollection sensorCol = Devices.talonSrxBaller.getSensorCollection();
            int absolutePosition = sensorCol.getPulseWidthPosition();
            absolutePosition &= 0xFFF;
            if (SENSOR_PHASE) absolutePosition *= -1;
            if (MOTOR_INVERT) absolutePosition *= -1;
            // Set the quadrature (relative) sensor to match absolute
            Devices.talonSrxBaller.setSelectedSensorPosition(absolutePosition, EncoderConstants.PID_LOOP_PRIMARY, EncoderConstants.TIMEOUT_MS);
            
            Devices.talonSrxBaller.configMotionAcceleration(6000, 20);
            Devices.talonSrxBaller.configMotionCruiseVelocity(15000, 20);
        }
    }

    @Override
    public void initDefaultCommand() {
        Logger.debug("Initializing Baller DefaultCommand -> BallerStop...");

        setDefaultCommand(new BallerStop());
    }

    // Stop the Baller motor
    public void stop() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxBaller.stopMotor();
    }

    // Move the Baller flipper to toss the ball
    public void tossBall() {
        if (!m_talonsAreConnected) return;
        double targetPositionUnits = TARGET_ROTATIONS * EncoderConstants.REDLIN_ENCODER_TPR;
        Logger.debug("Baller -> Target Toss Position: " + targetPositionUnits);
        Devices.talonSrxBaller.set(ControlMode.Position, targetPositionUnits);
    }

    // Move the Baller flipper back to the hold position
    public void holdBall() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxBaller.set(ControlMode.Position, 0);
        Logger.debug("Baller -> Target Hold Position: " + 0);
    }

    // Get the current Baller flipper motor velocity
    public int getVelocity() {
        return Devices.talonSrxBaller.getSelectedSensorVelocity();
    }

    // Get the current Baller flipper motor position
    public int getPosition() {
        return Devices.talonSrxBaller.getSelectedSensorPosition();
    }

    // Return whether or not the motor has reached the encoded position
    public boolean isPositionMet() {
        if (!m_talonsAreConnected) return true;
        int currentPosition = getPosition();
        double targetPositionUnits = TARGET_ROTATIONS * EncoderConstants.REDLIN_ENCODER_TPR;
        return (Math.abs((Math.abs(currentPosition) - targetPositionUnits)) < 600);
    }

    // Toggle the ballIsTossed state
    public void toggleBallTossed() {
        ballIsTossed = !ballIsTossed;
    }

    //---------//
    // Testing //
    //---------//

    public void driveStatic() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxBaller.set(0.2);
    }

}

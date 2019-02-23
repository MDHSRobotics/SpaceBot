
package frc.robot.subsystems;

import java.lang.Math;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.PulleyStop;
import frc.robot.consoles.Logger;
import frc.robot.helpers.TalonConstants;
import frc.robot.Devices;


// Pulley subsystem for lifting the robot onto a platform, using the gyro to mitigate tipping over
public class Pulley extends Subsystem {

    public enum PulleyPosition {
        DOWN, UP
    }

    // The public property to determine the Pulley state
    public PulleyPosition currentPulleyPosition = PulleyPosition.DOWN;

    // Encoder Constants
    private final double GEAR_RATIO = 28;

    private final double RESET_POSITION = 0;
    private final double END_POSITION = 0; // TODO: Determine actual end position of Pulley in ticks
    private final double POSITION_TOLERANCE = 0;

    private final double SPOOL_DIAMETER = 1.625; // In inches
    private final double SPOOL_CIRCUMFERENCE = Math.PI * SPOOL_DIAMETER;

    private final boolean SENSOR_PHASE = false; // So that Talon does not report sensor out of phase
    private final boolean MOTOR_INVERT = false; // Which direction you want to be positive; this does not affect motor invert

    // Leveling Constants
    private final double MOTOR_HOLD_POWER = 0;
    private final double DISTANCE_FROM_CENTER_TO_PULLEY = 0; // In inches

    // The Talon connection state, to prevent watchdog warnings during testing
    private boolean m_talonsAreConnected = false;

    public Pulley() {
        Logger.setup("Constructing Subsystem: Pulley...");

        boolean talonMasterIsConnected = Devices.isConnected(Devices.talonSrxPulleyMaster);
        boolean talonSlaveAIsConnected = Devices.isConnected(Devices.talonSrxPulleySlaveA);
        boolean talonSlaveBIsConnected = Devices.isConnected(Devices.talonSrxPulleySlaveB);
        boolean talonSlaveCIsConnected = Devices.isConnected(Devices.talonSrxPulleySlaveC);
        m_talonsAreConnected = (talonMasterIsConnected &&
                                talonSlaveAIsConnected && 
                                talonSlaveBIsConnected && 
                                talonSlaveCIsConnected);

        if (!m_talonsAreConnected) {
            Logger.error("Pulley talons not all connected! Disabling Pulley...");
        }
        else {
            Devices.talonSrxPulleyMaster.configPeakCurrentDuration(TalonConstants.PEAK_CURRENT_DURATION, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxPulleyMaster.configPeakCurrentLimit(TalonConstants.PEAK_CURRENT_AMPS, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxPulleyMaster.configContinuousCurrentLimit(TalonConstants.CONTINUOUS_CURRENT_LIMIT, TalonConstants.TIMEOUT_MS);
           
            Devices.talonSrxPulleyMaster.configNominalOutputForward(TalonConstants.NOMINAL_OUTPUT_FORWARD);
            Devices.talonSrxPulleyMaster.configNominalOutputReverse(TalonConstants.NOMINAL_OUTPUT_REVERSE);
            Devices.talonSrxPulleyMaster.configPeakOutputForward(0.5);
            Devices.talonSrxPulleyMaster.configPeakOutputReverse(-0.5);

            Devices.talonSrxPulleyMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, TalonConstants.PID_LOOP_PRIMARY, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxPulleyMaster.setSensorPhase(SENSOR_PHASE);
            Devices.talonSrxPulleyMaster.setInverted(MOTOR_INVERT);
            Devices.talonSrxPulleyMaster.configAllowableClosedloopError(TalonConstants.PID_LOOP_PRIMARY, 0, TalonConstants.TIMEOUT_MS);

            Devices.talonSrxPulleyMaster.config_kF(TalonConstants.PID_LOOP_PRIMARY, 0.0, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxPulleyMaster.config_kP(TalonConstants.PID_LOOP_PRIMARY, 0.32, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxPulleyMaster.config_kI(TalonConstants.PID_LOOP_PRIMARY, 0.0, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxPulleyMaster.config_kD(TalonConstants.PID_LOOP_PRIMARY, 0.1, TalonConstants.TIMEOUT_MS);

            // Reset Encoder Position 
            Devices.talonSrxPulleyMaster.setSelectedSensorPosition(0, TalonConstants.PID_LOOP_PRIMARY, TalonConstants.TIMEOUT_MS);
            SensorCollection sensorCol = Devices.talonSrxPulleyMaster.getSensorCollection();
            int absolutePosition = sensorCol.getPulseWidthPosition();
            absolutePosition &= 0xFFF;
            if (SENSOR_PHASE) absolutePosition *= -1;
            if (MOTOR_INVERT) absolutePosition *= -1;
            // Set the quadrature (relative) sensor to match absolute
            Devices.talonSrxPulleyMaster.setSelectedSensorPosition(absolutePosition, TalonConstants.PID_LOOP_PRIMARY, TalonConstants.TIMEOUT_MS);
            
            Devices.talonSrxPulleyMaster.configMotionAcceleration(6000, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxPulleyMaster.configMotionCruiseVelocity(15000, TalonConstants.TIMEOUT_MS);
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

     // Reset the Pulley to its starting position
     public void resetPosition() {
        if (!m_talonsAreConnected) return;
        Logger.info("Pulley -> Reset Position: " + RESET_POSITION);
        Devices.talonSrxPulleyMaster.set(ControlMode.Position, RESET_POSITION);
    }

    /**
     * Transforms an inputed gyro offset angle into a motor power output percentage in 
     * order to level the robot
     * @param offsetAngle The input offset angle from current angle to level with the ground
     */
    public void levelRobot(double offsetAngle) {
        if (!m_talonsAreConnected) return;
        double offsetAngleInDegrees = Math.toDegrees(Math.atan(offsetAngle));
        double offsetDistance = DISTANCE_FROM_CENTER_TO_PULLEY * offsetAngleInDegrees;
        double motorOutput = MOTOR_HOLD_POWER + offsetDistance / 13;
        Logger.info("Target Position: " + motorOutput);
        Devices.talonSrxPulleyMaster.set(ControlMode.PercentOutput, motorOutput);
    }  

    // Get the Robot's Pitch from the gyro
    // TODO: Perhaps this should be somewhere more central, like Devices, OI, or maybe a new class called Gyro, or Sensors?
    public double getRobotPitch() {
        double gyroAngle = Devices.gyro.getPitch();
        return gyroAngle;
    }

     // Get the current motor position
     public int getPosition() {
        if (!m_talonsAreConnected) return 0;
        return Devices.talonSrxPulleyMaster.getSelectedSensorPosition();
    }

    // Get the current motor velocity
    public int getVelocity() {
        if (!m_talonsAreConnected) return 0;
        return Devices.talonSrxPulleyMaster.getSelectedSensorVelocity();
    }

    // Return whether or not the motor has reached the encoded "reset" position
    public boolean isResetPositionMet() {
        if (!m_talonsAreConnected) return true;
        int currentPosition = getPosition();
        return (Math.abs(currentPosition - RESET_POSITION) < POSITION_TOLERANCE);
    }

    // Return whether or not the motor has reached the encoded "end" position
    public boolean isEndPositionMet() {
        if (!m_talonsAreConnected) return true;
        double currentPosition = getPosition();
        // TODO: Is this logic correct? The Arm does it differently.
        return (Math.abs((Math.abs(currentPosition) - END_POSITION)) < POSITION_TOLERANCE);
    }  

}

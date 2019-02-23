
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.PulleyStop;
import frc.robot.consoles.Logger;
import frc.robot.helpers.EncoderConstants;
import frc.robot.helpers.TalonConstants;
import frc.robot.Devices;


// Pulley subsystem for lifting the robot onto a platform, using the gyro to mitigate tipping over
public class Pulley extends Subsystem {

    public enum PulleyPosition {
        DOWN, UP
    }

    // The public property to determine the Pulley state
    public PulleyPosition currentPulleyPosition = PulleyPosition.DOWN;

    // Motor constants
    private final double PULLEY_SPEED = .5;
    // TODO: This next line will eventually produce a runtime error, because you have assigned it no value, and it never gets set, but it does get used.
    //       Is MOTOR_OUTPUT intended to be a constant?
    //       If so, it should set to "final" and initialized here (use a reasonable default value if the actual value still needs to be determined.)
    //       If not, it should not be in all caps, but instead start with "m_", and it should not be grouped with the "Motor Constants".
    private double MOTOR_OUTPUT;

    // Encoder constants
    private final double POSITION_TOLERANCE = 0;
    private final double TARGET_POSITION_RESET = 0;
    private final double END_POSITION = 0; // TODO: Determine actual end position of pulley in ticks 
    private final boolean SENSOR_PHASE = false; // So that Talon does not report sensor out of phase
    private final boolean MOTOR_INVERT = false; // Which direction you want to be positive; this does not affect motor invert

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

            Devices.talonSrxPulleyMaster.config_kF(EncoderConstants.PID_LOOP_PRIMARY, 0.0, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxPulleyMaster.config_kP(EncoderConstants.PID_LOOP_PRIMARY, 0.32, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxPulleyMaster.config_kI(EncoderConstants.PID_LOOP_PRIMARY, 0.0, EncoderConstants.TIMEOUT_MS);
            Devices.talonSrxPulleyMaster.config_kD(EncoderConstants.PID_LOOP_PRIMARY, 0.1, EncoderConstants.TIMEOUT_MS);

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
        Logger.info("Pulley -> Reset Position: " + TARGET_POSITION_RESET);
        Devices.talonSrxPulleyMaster.set(ControlMode.Position, TARGET_POSITION_RESET);
    }

    public void lift() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxPulleyMaster.set(PULLEY_SPEED);
    }

    public void lower() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxPulleyMaster.set(-PULLEY_SPEED);
    }

    // Get the current motor velocity
    public int getVelocity() {
        if (!m_talonsAreConnected) return 0;
        return Devices.talonSrxPulleyMaster.getSelectedSensorVelocity();
    }

    // Get the current motor position
    public int getPosition() {
        if (!m_talonsAreConnected) return 0;
        return Devices.talonSrxPulleyMaster.getSelectedSensorPosition();
    }

    // Return whether or not the motor has reached the encoded "reset" position
    public boolean isPositionResetMet() {
        if (!m_talonsAreConnected) return true;
        int currentPosition = getPosition();
        return (Math.abs(currentPosition) < POSITION_TOLERANCE);
    }

    // Return whether or not the motor has reached the encoded "end" position
    public boolean isEndPositionMet() {
        if (!m_talonsAreConnected) return true;
        double currentPosition = getPosition();
        return (Math.abs((Math.abs(currentPosition) - END_POSITION)) < POSITION_TOLERANCE);
    }

    /**
     * Transforms an inputed gyro offset angle into a motor power output percentage in 
     * order to level the robot
     * @param offsetAngle The input offset angle from current angle to level with the ground
     */
    public void levelRobot(double offsetAngle) {
        if (!m_talonsAreConnected) return;
        // TODO: add algorithm to convert offset angle into motor percent output
        Logger.info("Target Position: " + MOTOR_OUTPUT);
        Devices.talonSrxPulleyMaster.set(ControlMode.PercentOutput, MOTOR_OUTPUT);
    }

    public double getRobotPitch() {
        double gyroAngle = Devices.imuMecDrive.getPitch();
        return gyroAngle;
    }

}

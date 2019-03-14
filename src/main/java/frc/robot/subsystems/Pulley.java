
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.PulleyStop;
import frc.robot.consoles.Logger;
import frc.robot.helpers.TalonConstants;
import frc.robot.Devices;


// Pulley subsystem for lifting the back end of robot up above a platform
public class Pulley extends Subsystem {

    // The public property to indicate whether the Pulley has started lifting
    public boolean isLifting = false;

    // Position Constants
    // TODO: The constants that might change from the test robot to the competition robot need to be added to Shuffleboard
    private final double GEAR_RATIO = 28;
    private final double START_POSITION = 0;

    private final double LIFT_ROTATION_DEGREE = 90;
    private final double LIFT_ROTATION_COUNT_GS = LIFT_ROTATION_DEGREE / 360; // Amount of rotations on the gearbox shaft
    private final double LIFT_ROTATION_COUNT_MS = LIFT_ROTATION_COUNT_GS * GEAR_RATIO; // Amount of rotations on the motor shaft
    private final double LIFT_POSITION = LIFT_ROTATION_COUNT_MS * TalonConstants.REDLIN_ENCODER_TPR;

    // Encoder Constants
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
            Devices.talonSrxPulleyMaster.configPeakCurrentDuration(TalonConstants.PEAK_AMPERAGE_DURATION, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxPulleyMaster.configPeakCurrentLimit(TalonConstants.PEAK_AMPERAGE, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxPulleyMaster.configContinuousCurrentLimit(TalonConstants.CONTINUOUS_AMPERAGE_LIMIT, TalonConstants.TIMEOUT_MS);
           
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

    // Set the Pulley motor speed explicitly
    public void setSpeed(double speed){
        Devices.talonSrxPulleyMaster.set(speed);
    }

     // Reset the Pulley to its starting position
     public void resetPosition() {
        if (!m_talonsAreConnected) return;
        Logger.info("Pulley -> Reset Position: " + START_POSITION);
        Devices.talonSrxPulleyMaster.set(ControlMode.Position, START_POSITION);
    }

    // Lift the robot to the encoded Pulley motor position
    public void lift() {
        if (!m_talonsAreConnected) return;
        Logger.info("Pulley -> Lift Position: " + LIFT_POSITION);
        Devices.talonSrxPulleyMaster.set(ControlMode.Position, LIFT_POSITION);
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

}

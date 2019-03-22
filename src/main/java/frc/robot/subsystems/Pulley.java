
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.InvertType;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.reactive.PulleyReset;
import frc.robot.consoles.Logger;
import frc.robot.helpers.TalonConstants;
import frc.robot.Brain;
import frc.robot.Devices;
import frc.robot.Robot;


// Pulley subsystem for lifting the back end of robot up above a platform
public class Pulley extends Subsystem {

    // The public property to indicate whether the Pulley has started lifting
    public boolean isLifting = false;

    // Position Constants
    private final double SPOOL_DIAMETER = 2.42; // inches
    private final double GEAR_RATIO = 28;
    private final double START_POSITION = 0;

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
                    
            // Associate the master controller with each of the followers
            Devices.talonSrxPulleySlaveA.follow(Devices.talonSrxPulleyMaster);
            Devices.talonSrxPulleySlaveB.follow(Devices.talonSrxPulleyMaster);
            Devices.talonSrxPulleySlaveC.follow(Devices.talonSrxPulleyMaster);
            
            // Assume that the follower controllers have the same "invert" state as the master
            Devices.talonSrxPulleySlaveA.setInverted(InvertType.FollowMaster);
            Devices.talonSrxPulleySlaveB.setInverted(InvertType.FollowMaster);
            Devices.talonSrxPulleySlaveC.setInverted(InvertType.FollowMaster);
        }
    }

    @Override
    public void initDefaultCommand() {
        Logger.setup("Initializing Pulley DefaultCommand -> PulleyReset...");

        setDefaultCommand(new PulleyReset());
    }

    // Stop the Pulley motor
    public void stop() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxPulleyMaster.stopMotor();
    }

    // Set the Pulley motor speed explicitly
    public void setSpeed(double speed) {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxPulleyMaster.set(speed);
    }

     // Reset the Pulley to its starting position
     public void resetPosition() {
        Logger.info("Pulley -> Set Position to START: " + START_POSITION + " ticks");

        if (!m_talonsAreConnected) return;
        Devices.talonSrxPulleyMaster.set(ControlMode.Position, START_POSITION);
    }

    // Lift the robot to the encoded Pulley motor position
    public void lift() {
        double distance = 0;
        if (Robot.robotClimbMode == Robot.ClimbMode.HAB2) {
            distance = Brain.getPulleyHAB2Distance();
        }
        else if (Robot.robotClimbMode == Robot.ClimbMode.HAB3) {
            distance = Brain.getPulleyHAB3Distance();
        }
        double ticks = TalonConstants.translateDistanceToTicks(distance, SPOOL_DIAMETER, GEAR_RATIO);
        Logger.info("Pulley -> Set Position to " + Robot.robotClimbMode + " LIFT: " + distance + " distance, " + ticks + " ticks");

        if (!m_talonsAreConnected) return;
        Devices.talonSrxPulleyMaster.set(ControlMode.Position, ticks);
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

    //---------//
    // Testing //
    //---------//

    public void testMotors() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxPulleyMaster.set(0.2);
    }

}

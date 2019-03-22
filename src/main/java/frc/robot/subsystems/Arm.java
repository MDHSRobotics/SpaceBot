
package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.reactive.ArmReset;
import frc.robot.consoles.Logger;
import frc.robot.helpers.TalonConstants;
import frc.robot.Brain;
import frc.robot.Devices;
import frc.robot.Robot;


// Subsystem to move the Arm down into two different set positions,
// plus a little extra controlled by the user.
public class Arm extends Subsystem {

    // Position constants
    private final double GEAR_RATIO = 20;
    private final double START_POSITION = 0;

    // Encoder constants
    private final boolean SENSOR_PHASE = true; // So that Talon does not report sensor out of phase
    private final boolean MOTOR_INVERT = false; // Which direction you want to be positive; this does not affect motor invert

    // The Talon connection state, to prevent watchdog warnings during testing
    private boolean m_talonsAreConnected = false;

    public Arm() {
        Logger.setup("Constructing Subsystem: Arm...");

        boolean talonMasterIsConnected = Devices.isConnected(Devices.talonSrxArmMaster);
        boolean talonSlaveIsConnected = Devices.isConnected(Devices.talonSrxArmSlave);

        m_talonsAreConnected = (talonMasterIsConnected && talonSlaveIsConnected);
        if (!m_talonsAreConnected) {
            Logger.error("Arm talons not all connected! Disabling Arm...");
        }
        else {
            Devices.talonSrxArmMaster.configFactoryDefault();

            Devices.talonSrxArmMaster.configPeakCurrentDuration(TalonConstants.PEAK_AMPERAGE_DURATION, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArmMaster.configPeakCurrentLimit(TalonConstants.PEAK_AMPERAGE, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArmMaster.configContinuousCurrentLimit(TalonConstants.CONTINUOUS_AMPERAGE_LIMIT, TalonConstants.TIMEOUT_MS);

            Devices.talonSrxArmMaster.configNominalOutputForward(0);
            Devices.talonSrxArmMaster.configNominalOutputReverse(0);
            Devices.talonSrxArmMaster.configPeakOutputForward(1.0);
            Devices.talonSrxArmMaster.configPeakOutputReverse(-0.5);

            Devices.talonSrxArmMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, TalonConstants.PID_LOOP_PRIMARY, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArmMaster.setSensorPhase(SENSOR_PHASE);
            Devices.talonSrxArmMaster.setInverted(MOTOR_INVERT);
            Devices.talonSrxArmMaster.configAllowableClosedloopError(0, TalonConstants.PID_LOOP_PRIMARY, TalonConstants.TIMEOUT_MS);

            Devices.talonSrxArmMaster.config_kF(TalonConstants.PID_LOOP_PRIMARY, 0.0, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArmMaster.config_kP(TalonConstants.PID_LOOP_PRIMARY, 0.045, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArmMaster.config_kI(TalonConstants.PID_LOOP_PRIMARY, 0.0, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArmMaster.config_kD(TalonConstants.PID_LOOP_PRIMARY, 0.0, TalonConstants.TIMEOUT_MS);

            // Reset Encoder Position 
            Devices.talonSrxArmMaster.setSelectedSensorPosition(0, 0, TalonConstants.TIMEOUT_MS);
            SensorCollection sensorCol = Devices.talonSrxArmMaster.getSensorCollection();
            int absolutePosition = sensorCol.getPulseWidthPosition();
            absolutePosition &= 0xFFF;
            if (SENSOR_PHASE) absolutePosition *= -1;
            if (MOTOR_INVERT) absolutePosition *= -1;
            // Set the quadrature (relative) sensor to match absolute
            Devices.talonSrxArmMaster.setSelectedSensorPosition(absolutePosition, TalonConstants.PID_LOOP_PRIMARY, TalonConstants.TIMEOUT_MS);
            
            Devices.talonSrxArmMaster.configMotionAcceleration(1500, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArmMaster.configMotionCruiseVelocity(4000, TalonConstants.TIMEOUT_MS);

            Devices.talonSrxArmSlave.follow(Devices.talonSrxArmMaster);
        }
    }

    @Override
    public void initDefaultCommand() {
        Logger.setup("Initializing Arm DefaultCommand -> ArmReset...");

        setDefaultCommand(new ArmReset());
    }

    // Stop the Arm motor
    public void stop() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxArmMaster.stopMotor();
    }

    // Set the Arm motor speed explicitly
    public void setSpeed(double speed) {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxArmMaster.set(speed);
    }

    // Reset the Arm to its encoded starting position
    public void resetPosition() {
        Logger.info("Arm -> Set Position to START: " + START_POSITION + " ticks");

        if (!m_talonsAreConnected) return;
        Devices.talonSrxArmMaster.set(ControlMode.Position, START_POSITION);
    }

    // Lowers the Arm to the encoded "full" position
    // TODO: Do we need a different full angle for HAB2 and HAB3?
    public void lowerFull() {
        double angle = 0;
        if (Robot.robotClimbMode == Robot.ClimbMode.HAB2) {
            angle = Brain.getArmFullAngle();
        }
        else if (Robot.robotClimbMode == Robot.ClimbMode.HAB3) {
            angle = Brain.getArmFullAngle();
        }
        double ticks = TalonConstants.translateAngleToTicks(angle, GEAR_RATIO);
        Logger.info("Arm -> Motion Magic to " + Robot.robotClimbMode + " FULL: " + angle + " angle, " + ticks + " ticks");

        if (!m_talonsAreConnected) return;
        Devices.talonSrxArmMaster.set(ControlMode.MotionMagic, ticks);
    }

    // Get the current motor velocity
    public int getVelocity() {
        if (!m_talonsAreConnected) return 0;
        return Devices.talonSrxArmMaster.getSelectedSensorVelocity();
    }

    // Get the current motor position
    public int getPosition() {
        if (!m_talonsAreConnected) return 0;
        return Devices.talonSrxArmMaster.getSelectedSensorPosition();
    }

    // Return true if the Arm is at or beyond the HAB position
    public boolean isArmOnHab() {
        int armPosition = getPosition();
        double angle = 0;
        if (Robot.robotClimbMode == Robot.ClimbMode.HAB2) {
            angle = Brain.getArmHAB2Angle();
        }
        else if (Robot.robotClimbMode == Robot.ClimbMode.HAB3) {
            angle = Brain.getArmHAB3Angle();
        }
        double habTicks = TalonConstants.translateAngleToTicks(angle, GEAR_RATIO);
        boolean armIsOnHab = armPosition >= habTicks;
        return armIsOnHab;
    }

    //---------//
    // Testing //
    //---------//

    public void testMotors() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxArmMaster.set(0.2);
    }

}

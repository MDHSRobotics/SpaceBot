package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.commands.idle.ArmStop;
import frc.robot.consoles.Logger;
import frc.robot.helpers.TalonConstants;
import frc.robot.Devices;
import frc.robot.Robot;


// Subsystem to move the Arm down into two different set positions,
// plus a little extra controlled by the user.
public class Arm extends Subsystem {

    // Position constants
    // TODO: The constants that might change from the test robot to the competition robot need to be added to Shuffleboard
    private final double GEAR_RATIO = 81;
    private final double START_POSITION = 0;
    private final double HAB2_ROTATION_DEGREE = 55; // Amount of degrees the Arm will lower to contact the HAB2
    private final double HAB3_ROTATION_DEGREE = 110; // Amount of degrees the Arm will lower to contact the HAB3
    private final double FULL_ROTATION_DEGREE = 220;

    // Encoder constants
    private final boolean SENSOR_PHASE = true; // So that Talon does not report sensor out of phase
    private final boolean MOTOR_INVERT = false; // Which direction you want to be positive; this does not affect motor invert

    // The Talon connection state, to prevent watchdog warnings during testing
    private boolean m_talonsAreConnected = false;

    public Arm() {
        Logger.setup("Constructing Subsystem: Arm...");
        m_talonsAreConnected = Devices.isConnected(Devices.talonSrxArm);
        if (!m_talonsAreConnected) {
            Logger.error("Arm talons not all connected! Disabling Arm...");
        }
        else {
            Devices.talonSrxArm.configFactoryDefault();

            Devices.talonSrxHatcher.configPeakCurrentDuration(TalonConstants.PEAK_AMPERAGE_DURATION, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxHatcher.configPeakCurrentLimit(TalonConstants.PEAK_AMPERAGE, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxHatcher.configContinuousCurrentLimit(TalonConstants.CONTINUOUS_AMPERAGE_LIMIT, TalonConstants.TIMEOUT_MS);

            Devices.talonSrxArm.configNominalOutputForward(0);
            Devices.talonSrxArm.configNominalOutputReverse(0);
            Devices.talonSrxArm.configPeakOutputForward(0.5);
            Devices.talonSrxArm.configPeakOutputReverse(-0.5);

            Devices.talonSrxArm.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, TalonConstants.PID_LOOP_PRIMARY, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArm.setSensorPhase(SENSOR_PHASE);
            Devices.talonSrxArm.setInverted(MOTOR_INVERT);
            Devices.talonSrxArm.configAllowableClosedloopError(0, TalonConstants.PID_LOOP_PRIMARY, TalonConstants.TIMEOUT_MS);

            Devices.talonSrxArm.config_kF(TalonConstants.PID_LOOP_PRIMARY, 0.0, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArm.config_kP(TalonConstants.PID_LOOP_PRIMARY, 0.045, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArm.config_kI(TalonConstants.PID_LOOP_PRIMARY, 0.0, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArm.config_kD(TalonConstants.PID_LOOP_PRIMARY, 0.0, TalonConstants.TIMEOUT_MS);

            // Reset Encoder Position 
            Devices.talonSrxArm.setSelectedSensorPosition(0, 0, TalonConstants.TIMEOUT_MS);
            SensorCollection sensorCol = Devices.talonSrxArm.getSensorCollection();
            int absolutePosition = sensorCol.getPulseWidthPosition();
            absolutePosition &= 0xFFF;
            if (SENSOR_PHASE) absolutePosition *= -1;
            if (MOTOR_INVERT) absolutePosition *= -1;
            // Set the quadrature (relative) sensor to match absolute
            Devices.talonSrxArm.setSelectedSensorPosition(absolutePosition, TalonConstants.PID_LOOP_PRIMARY, TalonConstants.TIMEOUT_MS);
            
            Devices.talonSrxArm.configMotionAcceleration(1500, TalonConstants.TIMEOUT_MS);
            Devices.talonSrxArm.configMotionCruiseVelocity(4000, TalonConstants.TIMEOUT_MS);
        }
    }

    @Override
    public void initDefaultCommand() {
        Logger.setup("Initializing Arm DefaultCommand -> ArmStop...");

        setDefaultCommand(new ArmStop());
    }

    // Stop the Arm motor
    public void stop() {
        if (!m_talonsAreConnected) return;
        Devices.talonSrxArm.stopMotor();
    }

    // Set the Arm motor speed explicitly
    public void setSpeed(double speed){
        Devices.talonSrxArm.set(speed);
    }

    // Reset the Arm to its encoded starting position
    public void resetPosition() {
        if (!m_talonsAreConnected) return;
        Logger.info("Arm -> Start Position: " + START_POSITION);
        Devices.talonSrxArm.set(ControlMode.Position, START_POSITION);
    }

    // Lowers the Arm to the encoded "full" position
    public void lowerFull() {
        if (!m_talonsAreConnected) return;
        double fullPositionDegrees = FULL_ROTATION_DEGREE;
        double fullPositionTicks = TalonConstants.translateDegreesToTicks(fullPositionDegrees, GEAR_RATIO);
        Logger.info("Arm -> Target Full Position: " + fullPositionTicks);
        Devices.talonSrxArm.set(ControlMode.MotionMagic, fullPositionTicks);
    }

    // Get the current motor velocity
    public int getVelocity() {
        if (!m_talonsAreConnected) return 0;
        return Devices.talonSrxArm.getSelectedSensorVelocity();
    }

    // Get the current motor position
    public int getPosition() {
        if (!m_talonsAreConnected) return 0;
        return Devices.talonSrxArm.getSelectedSensorPosition();
    }
    
    // Return true if the Arm is at or beyond the HAB position
    public boolean isArmOnHab() {
        int armPosition = getPosition();
        double habPositionDegrees = 0;
        if (Robot.robotClimbMode == Robot.ClimbMode.HAB2) {
            habPositionDegrees = HAB2_ROTATION_DEGREE;
        }
        else if (Robot.robotClimbMode == Robot.ClimbMode.HAB3) {
            habPositionDegrees = HAB3_ROTATION_DEGREE;
        }
        double habPositionTicks = TalonConstants.translateDegreesToTicks(habPositionDegrees, GEAR_RATIO);
        boolean armIsOnHab = armPosition >= habPositionTicks;
        return armIsOnHab;
    }

}

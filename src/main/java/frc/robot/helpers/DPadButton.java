
package frc.robot.helpers;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.GenericHID;


public class DPadButton extends Button {

    public enum Direction {
        UP (0),
        DOWN (180),
        LEFT (270),
        RIGHT (90),
        UP_LEFT (315),
        UP_RIGHT (45),
        DOWN_LEFT (225),
        DOWN_RIGHT (135);

        public int degrees;
        Direction(int povAngle) {
            this.degrees = povAngle;
        }
    }

    public GenericHID device;
    public Direction direction;

    public DPadButton(GenericHID humanInterfaceDevice, Direction dPadButtonDirection) {
        this.device = humanInterfaceDevice;
        this.direction = dPadButtonDirection;
    }

    @Override
    public boolean get() {
        int angle = device.getPOV(0);
        return (angle == direction.degrees);
    }

}
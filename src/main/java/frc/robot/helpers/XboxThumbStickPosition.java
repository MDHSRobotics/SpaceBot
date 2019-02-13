
package frc.robot.helpers;


// The position values obtained from the Xbox Controller
public class XboxThumbStickPosition {

    public double yLeftPosition = 0;
    public double xLeftPosition = 0;
    public double xRightPosition = 0;

    public XboxThumbStickPosition() {
    }

    public XboxThumbStickPosition(double yLeft, double xLeft, double xRight) {
        yLeftPosition = yLeft;
        xLeftPosition = xLeft;
        xRightPosition = xRight;
    }

}

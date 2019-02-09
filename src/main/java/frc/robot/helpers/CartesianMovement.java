
package frc.robot.helpers;


// The values needed to drive using cartesian coordinates
public class CartesianMovement {

    public double ySpeed = 0; // Forward & Backward
    public double xSpeed = 0; // Side to Side
    public double zRotation = 0; // Rotate

    public CartesianMovement() {
    }

    public CartesianMovement(double y, double x, double z) {
        ySpeed = y;
        xSpeed = x;
        zRotation = z;
    }

}

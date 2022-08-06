
import java.awt.*;

/**
 * The Target interface is implemented by any object that can be hit by a missile.
 * @author Kimberly A. Baram
 * @version 1.0 7 February 2022
 */
public interface Target{

    /**
     * Fires a missile
     */
    boolean fire();

    /**
     *
     * @return the x-coordinate of the target's left side
     */
    int getX();

    /**
     *
     * @return the y-coordinate of the target's right side
     */
    int getY();

    /**
     * @return the width of the target
     */
    int getWidth();

    /**
     * @return the height of the target
     */
    int getHeight();

    /**
     * moves the target one frame
     */
    void move();

    /**
     *
     * @param missile the missile aiming for the target
     * @return true if the missile hit the target, false otherwise
     */
    boolean hitByMissile(Missile missile);

    /**
     * @return true if this target hits the opposing target, false otherwise
     */
    boolean hitTarget(Target target);

    /**
     * Draws the target's sprite on the screen
     * @param g the graphics object to paint on the screen
     */
    void draw(Graphics g);


}

import java.awt.*;

/**
 * The Missile class maintains data and functions for missiles fired from aliens and cannons
 * @author Kimberly A. Baram
 * @version 1.0 8 February 2002
 */
public abstract class Missile {
    //coordinates of uppermost point
    private int x, topY;

    //vertical speed
    private final int dy;

    //length and zigzag degree
    public static final int LENGTH = 20, ZIGZAG = 3;

    /**
     * Creates a missile at the specified location, traveling in the specified direction
     * @param x the x coordinate
     * @param topY the y coordinate
     * @param travelsDown true if an alien missile, false if a cannon missile
     */
    public Missile(int x, int topY, boolean travelsDown) {
        this.x = x;
        this.topY = topY;
        dy = (travelsDown ? 5 : -5);
    }

    /**
     *
     * @return The x-coordinate of the missile
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return The y-coordinate of the top of the missile
     */
    public int getTopY() {
        return topY;
    }

    /**
     * Move the missile one frame
     */
    public void move(){
        topY += dy;
    }

    /**
     *
     * @param pixel a pixel within a shelter
     * @return true if the missile hit the pixel, false otherwise
     */
    public abstract boolean hitShelterPixel(ShelterPixel pixel);

    /**
     *
     * @return true if the missile has gone out of bounds, false otherwise
     */
    public abstract boolean offScreen();

    /**
     * Checks if two opposing missiles have hit each other
     * @param other the opposing missile
     * @return true if the missiles collided, false otherwise
     */
    public boolean missileCollide(Missile other){
        return getX() == other.getX() && getTopY() + LENGTH == other.getTopY();
    }

    /**
     * Draws a missile in a zigzag pattern
     * @param g the graphics object used for drawing
     */
    public void draw(Graphics g) {

        //allow for thicker lines
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));

        //top-center to mid-right
        g.drawLine(x, topY, x + ZIGZAG, topY + LENGTH / 3);

        //mid-right to mid-left
        g.drawLine(x + ZIGZAG, topY + LENGTH / 3, x - 2, topY + 2 * LENGTH / 3);

        //mid-left to bottom-center
        g.drawLine(x - ZIGZAG, topY + 2 * LENGTH / 3, x, topY + LENGTH);
    }
}
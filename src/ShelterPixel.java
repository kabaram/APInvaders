
import java.awt.*;

/**
 * The ShelterPixel class maintains data on one pixel of a shelter. Pixels are destroyed individually
 * on contact with a missile or alien.
 * @author Kimberly A. Baram
 * @version 1.0
 */
public class ShelterPixel {

    //location and dimension data
    private final int x, y;
    public static final int WIDTH = 8, HEIGHT = 20;

    /**
     * Creates a pixel at the specified location
     * @param x the x coordinate of the left side of the pixel
     * @param y the y coordinate of the top of the pixel
     */
    public ShelterPixel(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return the x coordinate of the left side of the pixel
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return the y coordinate of the top of the pixel
     */
    public int getY(){
        return y;
    }

    /**
     * Draws the pixel, if active, on the game field
     * @param g the graphics object to enable drawing
     */
    public void draw(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }
}

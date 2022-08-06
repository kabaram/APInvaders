
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * The Alien class controls one alien in the AP Invaders game.
 *
 * @author Kimberly A. Baram
 * @version 1.0 7 February 2022
 */

public class Alien implements Target {

    //used to obtain alien sprites
    private static final InvadersSprites sheet = new InvadersSprites();
    private static final BufferedImage[] sprites = {sheet.alien1(), sheet.alien2()};
    private static BufferedImage current = sprites[0];

    //coordinate, state and speed information
    private int x, y, state;
    private static int dx = 10;

    //the alien's missile
    private AlienMissile missile;

    //alien dimensions
    public static final int WIDTH = 30, HEIGHT = 20;

    /**
     * Generates an alien in the specified location
     *
     * @param x the x-coordinate of the left edge of the alien
     * @param y the y-coordinate of the upper edge of the alien
     */
    public Alien(int x, int y) {
        this.x = x;
        this.y = y;
        missile = null;
    }

    @Override
    public boolean fire() {
        if (missile == null) {
            missile = new AlienMissile(x + WIDTH / 2, y + HEIGHT);
            return true;
        }
        return false;
    }


    @Override
    public void draw(Graphics g) {
        g.drawImage(current, x, y, WIDTH, HEIGHT, null);
    }

    /**
     * "Destroys" this alien's missile
     */
    public void loseMissile() {
        missile = null;
    }

    /**
     * Determines if the alien is in contact with the shelter pixel
     *
     * @param pixel the shelter pixel
     * @return true if the alien is touching the shelter, false otherwise
     */
    public boolean onShelterPixel(ShelterPixel pixel) {
        if (pixel != null && (y + HEIGHT >= pixel.getY())) {
            for (int i = x; i < x + WIDTH; i++) {
                if (i >= pixel.getX() - ShelterPixel.WIDTH && i <= pixel.getX() + 2 * ShelterPixel.WIDTH) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return this alien's missile
     */
    public AlienMissile getMissile() {
        return missile;
    }

    @Override
    public void move() {
        x += dx;
        current = sprites[++state % 2];
    }

    /**
     * Toggle the current sprite displayed
     */
    public void moveInPlace() {
        current = sprites[++state % 2];
    }

    /**
     * Drop the alien by 15 pixels
     */
    public void drop() {
        y += 15;
    }

    /**
     * Toggle between moving left and right
     */
    public static void changeDir() {
        dx = -dx;
    }

    /**
     * @return the speed/direction of the alien
     */
    public static int getDx() {
        return dx;
    }

    /**
     * Determines if the alien was hit by a cannon missile
     *
     * @param missile the cannon's missile
     * @return true if the alien was hit by a cannon missile, false otherwise
     */
    @Override
    public boolean hitByMissile(Missile missile) {
        if (missile.getTopY() <= y + HEIGHT && missile.getTopY() + Missile.LENGTH >= y &&
                missile.getX() >= x && missile.getX() <= x + WIDTH) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the alien hit a shelter or cannon
     *
     * @param target the alien's target
     * @return true if the alien hit the target, false otherwise
     */
    @Override
    public boolean hitTarget(Target target) {
        if (missile != null && target.hitByMissile(missile)) {
            missile = null;
            return true;
        }
        return false;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

}

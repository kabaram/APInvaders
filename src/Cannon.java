
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The Cannon class represents a cannon controlled by the player, capable of firing up to 2 missiles
 * at a time.
 * @author Kimberly A. Baram
 * @version 1.0 8 February 2022
 */
public class Cannon implements Target {

    //missiles assigned to cannon
    private final CannonMissile[] missiles = new CannonMissile[2];

    //position and dimension data
    public static final int WIDTH = 50, HEIGHT = 10;
    public static final int START_X = App.WIDTH / 2 - WIDTH / 2;
    public static final int START_Y = 475;
    private int x, y, dx;

    //Sprite data
    private final InvadersSprites sheet = new InvadersSprites();
    private BufferedImage sprite = sheet.cannon();

    /**
     * Creates a cannon in the specified location
     * @param x the x-coordinate of the left of the cannon
     * @param y the y-coordinate of the right of the cannon
     */
    public Cannon(int x, int y) {
        this.x = x;
        this.y = y;
        dx = 20;
    }

    /**
     *
     * @return the speed of the cannon
     */
    public int getDx() {
        return dx;
    }

    /**
     * Change the speed of the cannon
     * @param dx the new speed of the cannon
     */
    public void setDx(int dx) {
        this.dx = dx;
    }

    /**
     * Checks both cannon missiles for a collision with the alien missile
     * @param missile the alien missile
     * @return true if either cannon missile collided with the alien missile,
     * false otherwise
     */
    public boolean checkMissileCollide(AlienMissile missile) {
        if (missiles[0] != null && missile.missileCollide(missiles[0])) {
            missiles[0] = null;
            return true;
        } else if (missiles[1] != null && missile.missileCollide(missiles[1])) {
            missiles[1] = null;
            return true;
        }
        return false;
    }

    @Override
    public boolean fire() {
        if (missiles[0] == null) {
            missiles[0] = new CannonMissile(x + WIDTH / 2, y);
            return true;      //returns true to activate sound
        } else if (missiles[1] == null) {
            missiles[1] = new CannonMissile(x + WIDTH / 2, y);
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

    @Override
    public void move() {
        if (x + dx > APInvaders.FIELD_LEFT && x + WIDTH + dx < APInvaders.FIELD_RIGHT) {
            x += dx;
        } else if (x + dx <= APInvaders.FIELD_LEFT) {
            x = APInvaders.FIELD_LEFT;
        } else {
            x = APInvaders.FIELD_RIGHT - WIDTH;
        }
    }


    @Override
    public boolean hitByMissile(Missile missile) {
        if (missile.getTopY() + Missile.LENGTH >= y &&
                missile.getX() >= x && missile.getX() <= x + WIDTH) {
            destroySprite();
            return true;
        }
        return false;
    }

    @Override
    public boolean hitTarget(Target target) {
        int i = 0;
        for (Missile missile : missiles){
            if (missile != null && target.hitByMissile(missile)){
                missiles[i] = null;
                return true;
            }
            i++;
        }
        return false;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(sprite, x, y - 10, WIDTH, HEIGHT + 10, null);
        for (int i = 0; i < missiles.length; i++) {
            if (missiles[i] != null) {
                missiles[i].move();
                missiles[i].draw(g);
                if (missiles[i].offScreen()) {
                    missiles[i] = null;
                }
            }
        }
    }

    /**
     * Removes all cannon missiles from the screen
     */
    public void removeMissiles() {
        for (int i = 0; i < missiles.length; i++) {
            missiles[i] = null;
        }
    }

    /**
     * Changes the cannon's sprite to dark grey to indicate destroyed cannon
     */
    public void destroySprite() {
        sprite = sheet.destroyedCannon();
    }

    /**
     * Changes the cannon's sprite to cyan to indicate active cannon
     */
    public void renewSprite() {
        sprite = sheet.cannon();
        x = START_X;
        y = START_Y;
    }

}

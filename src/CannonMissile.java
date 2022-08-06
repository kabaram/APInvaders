
/**
 * The CannonMissile class maintains data on an upward traveling missile fired from a cannon
 * @author Kimberly A. Baram
 * @version 1.0 8 February 2022
 */
public class CannonMissile extends Missile {
    /**
     * Creates the cannon missile to start at the indicated location, traveling up
     * @param x the x-coordinate of the left of the cannon missile
     * @param y the y-coordinate of the right of the cannon missile
     */
    public CannonMissile(int x, int y) {
        super(x, y, false);
    }

    @Override
    public boolean hitShelterPixel(ShelterPixel pixel) {
        return (getX() >= pixel.getX() && getX() <= pixel.getX() + pixel.WIDTH &&
                getTopY() <= pixel.getY() + pixel.WIDTH);
    }

    @Override
    public boolean offScreen() {
        return getTopY() + LENGTH <= 0;
    }
}

/**
 * The AlienMissile class represents a downward-traveling missile spawned by an alien
 *
 * @author Kimberly A. Baram
 * @version 1.0 8 February 2022
 */
public class AlienMissile extends Missile {

    /**
     * Creates a downward traveling missile
     *
     * @param x the missile's x coordinate
     * @param y the top of the missile's y coordinate
     */
    public AlienMissile(int x, int y) {
        super(x, y, true);
    }

    @Override
    public boolean hitShelterPixel(ShelterPixel pixel) {
        return (getX() >= pixel.getX() && getX() <= pixel.getX() + ShelterPixel.WIDTH &&
                getTopY() + LENGTH >= pixel.getY());
    }

    @Override
    public boolean offScreen() {
        return getTopY() + LENGTH >= APInvaders.FIELD_BOTTOM;
    }
}
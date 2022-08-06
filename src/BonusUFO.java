
/**
 * The BonusUFO maintains the UFO in the bonus round. When hit, rather than disappearing,
 * it shrinks in size, changes direction, and speeds up.
 */
public class BonusUFO extends UFO{

    /**
     * Constructs the UFO to randomly choose which side to start from
     */
    public BonusUFO(){
        super();
    }

    /**
     * Manages UFO maneuvers when hit by a cannon missile
     */
    public void onHit(){
        //speed up and  switch direction
        if (getDx() > 0){
            setDx(getDx() + 1);
        }
        else{
            setDx(getDx() - 1);
        }
        setDx(-1 * getDx());

        //shrink
        if (getWidth() > 10){
            setWidth(getWidth() - 10);
        }
        if (getHeight() > 5){
            setHeight(getHeight() - 5);
        }
    }

}

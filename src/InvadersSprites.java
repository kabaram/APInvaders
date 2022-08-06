
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * The InvadersSprites class maintains image information for all sprites used in AP Invaders
 * @author Kimberly A. Baram
 * @version 1.0
 */
public class InvadersSprites {

    //the full image containing all sprites
    private static BufferedImage sheet;

    /**
     * Accesses the file location of the full image
     */
    public InvadersSprites() {
        try {
            sheet = ImageIO.read(new File("sprites.png"));
        } catch (Exception e) {
            sheet = null;
        }
    }

    /**
     * @return the first alien sprite
     */
    public BufferedImage alien1() {
        BufferedImage alien = sheet.getSubimage(73, 224, 23, 18);
        for (int i = 0; i < alien.getWidth(); i++) {
            for (int j = 0; j < alien.getHeight(); j++) {
                if (alien.getRGB(i, j) != Color.BLACK.getRGB()) {
                    alien.setRGB(i, j, Color.MAGENTA.getRGB());
                }
            }
        }
        return alien;
    }

    /**
     *
     * @return the second alien sprite
     */
    public BufferedImage alien2() {
        BufferedImage alien = sheet.getSubimage(106, 224, 23, 18);
        for (int i = 0; i < alien.getWidth(); i++) {
            for (int j = 0; j < alien.getHeight(); j++) {
                if (alien.getRGB(i, j) != Color.BLACK.getRGB()) {
                    alien.setRGB(i, j, Color.MAGENTA.getRGB());
                }
            }
        }
        return alien;
    }

    /**
     *
     * @return the UFO sprite
     */
    public BufferedImage ufo(){
        BufferedImage ufo = sheet.getSubimage(215, 222, 47, 20);
        return ufo;
    }

    /**
     * Colors the cannon sprite to the indicated color
     * @param cannon the cannon to be recolored
     * @param color the new color
     */
    private void colorCannon(BufferedImage cannon, Color color){
        //access all non-black pixels and change them to color
        for (int i = 0; i < cannon.getWidth(); i++){
            for (int j = 0; j < cannon.getHeight(); j++){
                if (cannon.getRGB(i, j) != Color.BLACK.getRGB()){
                    cannon.setRGB(i, j, color.getRGB());
                }
            }
        }
    }

    /**
     *
     * @return the sprite of the cannon in cyan
     */
    public BufferedImage cannon(){
        BufferedImage cannon = sheet.getSubimage(277, 228, 25, 14);
        colorCannon(cannon, Color.CYAN);
        return cannon;
    }

    /**
     *
     * @return the sprite of the cannon in dark gray
     */
    public BufferedImage destroyedCannon(){
        BufferedImage cannon = sheet.getSubimage(277, 228, 25, 14);
        colorCannon(cannon, Color.DARK_GRAY);
        return cannon;
    }
}

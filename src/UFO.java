
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The UFO class maintains data on UFOs displayed during standard gameplay
 * @author Kimberly A. Baram
 * @version 1.0 8 February 2022
 */

public class UFO implements Target{

   //location, dimension and speed info
   private int x, y, dx, width, height;
   public static final int WIDTH = 40, HEIGHT = 20;

   //sprite info
   private final InvadersSprites sheet = new InvadersSprites();
   private final BufferedImage ufo = sheet.ufo();

   /**
    * Creates a UFO, to randomly start at the left or right edge of the screen
    */
   public UFO(){
      //start from right
      if (Math.random() < .5){
         x = APInvaders.FIELD_RIGHT - WIDTH;
         dx = -2;
         width = WIDTH;
         height = HEIGHT;
      }
      
      //start from left
      else{
         x = APInvaders.FIELD_LEFT;
         dx = 2;
      }
      y = 30;
   }

   @Override
   public boolean fire() {
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

   /**
    * Setter for width
    * @param width the new width
    */
   public void setWidth(int width){
      this.width = width;
   }

   @Override
   public int getWidth(){
      return width;
   }

   /**
    * Setter for height
    * @param height the new height
    */
   public void setHeight(int height){
      this.height = height;
   }

   @Override
   public int getHeight(){
      return height;
   }

   /**
    * Setter for dx
    * @param dx the new dx
    */
   public void setDx(int dx){
      this.dx = dx;
   }

   /**
    *
    * @return the number of pixels moved horizontally per frame
    */
   public int getDx(){
      return dx;
   }

   @Override
   public void move() {
      x += dx;
   }

   /**
    *
    * @return true if the UFO has left the screen, false otherwise
    */
   public boolean offScreen(){
      if (x < APInvaders.FIELD_LEFT || x + WIDTH > APInvaders.FIELD_RIGHT){
         return true;
      }
      return false;
   }

   @Override
   public boolean hitByMissile(Missile missile) {
      return (missile.getX() >= x && missile.getX() <= x + WIDTH &&
             missile.getTopY() <= y);
   }

   @Override
   public boolean hitTarget(Target target) {
      return false;
   }

   @Override
   public void draw(Graphics g) {
      g.drawImage(ufo, x, y, width, height, null);
   }
}

import java.awt.Graphics;

/**
 * The Shelter class maintains data on a shelter in AP Invaders. Shelters are composed of "pixels" that
 * when hit by a missile, will cause the shelter to decompose
 * @author Kimberly A. Baram
 * @version 1.0
 */
public class Shelter implements Target{
   //location and dimension data
   private int x;
   public static final int TOP = 375;
   private ShelterPixel[][] pixels = new ShelterPixel[3][11];

   /**
    * Creates a 3-row, 11-column shelter with a "doorway" at the bottom
    * @param x the x-coordinate of the left side of the shelter
    */
   public Shelter(int x){
      this.x = x;
      for (int i = 0; i < pixels.length; i++){
         for (int j = 0; j < pixels[0].length; j++){
            pixels[i][j] = new ShelterPixel(x + (j * ShelterPixel.WIDTH), TOP + (i * ShelterPixel.HEIGHT));
         }
      }
   
      //"angle" the top row
      pixels[0][0] = pixels[0][pixels[0].length - 1] = null;
   
      //"cut in" the doorway
      pixels[pixels.length - 1][pixels[0].length / 2 - 1] = null;
      pixels[pixels.length - 1][pixels[0].length / 2] = null;
      pixels[pixels.length - 1][pixels[0].length / 2 + 1] = null;
   
   }

   /**
    * not used
    */
   @Override
   public boolean fire() {
      return false;
   }

   @Override
   public int getX(){
      return x;
   }

   @Override
   public int getY() {
      return TOP;
   }

   @Override
   public int getWidth() {
      return ShelterPixel.WIDTH * pixels.length;
   }

   @Override
   public int getHeight() {
      return ShelterPixel.WIDTH * pixels[0].length;
   }

   @Override
   public void move() {
      return;}

   /**
    * Checks if an alien has reached one or more pixels of the shelter. If so, those pixels
    * and the left and right neighbors are destroyed
    * @param alien the alien
    * @return true if a pixel has been touched by an alien, false otherwise
    */
   public boolean touchedByAlien(Alien alien){
      for (int r = 0; r < pixels.length; r++){
         for (int c = 0; c < pixels[0].length; c++){
            if (pixels[r][c] != null && alien.onShelterPixel(pixels[r][c])){
               pixels[r][c] = null;
               if (c > 0){
                  pixels[r][c - 1] = null;
               }
               if (c < pixels[0].length - 1){
                  pixels[r][c + 1] = null;
               }
               return true;
            }
         }
      }
      return false;
   }

   @Override
   public boolean hitByMissile(Missile missile) {
      for (int i = 0; i < pixels.length; i++){
         for (int j = 0; j < pixels[i].length; j++){
            if (pixels[i][j] != null &&
                   missile.hitShelterPixel(pixels[i][j])){
               pixels[i][j] = null;
               return true;
            }
         }
      }
      return false;
   }

   @Override
   public boolean hitTarget(Target target) {
      return false;
   }

   @Override
   public void draw(Graphics g ){
      for (ShelterPixel[] row : pixels){
         for(ShelterPixel pixel : row){
            if (pixel != null){
               pixel.draw(g);
            }
         }
      }
   }
}

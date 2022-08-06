
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 * The APInvaders class maintains the display of all screen items in the game.
 *
 * @author Kimberly A. Baram
 * @version 1.0 7 February 2022
 */
public class APInvaders extends JPanel implements KeyListener {

   //playing field boundaries
   public static final int FIELD_LEFT = 50;
   public static final int FIELD_RIGHT = App.WIDTH - 50;
   public static final int FIELD_BOTTOM = App.HEIGHT - 50;

   //left/right/bottommost active alien columns and row
   private int leftCol, rightCol, bottomRow;

   //controls animation
   private final Timer movement = new Timer(5, new Movement());

   //matrix of 55 aliens
   private final Alien[][] aliens = new Alien[5][11];

   //4 shelters
   private final Shelter[] shelters = new Shelter[4];

   //3 cannons to start (ArrayList to allow option to add extra lives)
   private final ArrayList<Cannon> cannons = new ArrayList<>();
   private Cannon activeCannon;

   //Animation components
   //tick updates every frame
   //cycle controls alien movement. Aliens move every "cycle" ticks, and move
   //faster as more aliens are destroyed
   //aliensHit checks for every third alien destroyed, which decreases cycle
   private int tick, cycle, aliensHit;

   //Game states
   //standardPlay regular round animation
   //betweenLives "intermission" between death and start of next life
   //gameOver all lives run out, aliens reach bottom of screen, or bonus UFO escaped
   //bonusRound achieved after defeating all aliens in standard play
   private boolean standardPlay, betweenLives, gameOver, bonusRound;

   //probability that an alien launches a missile when matrix moves
   private double alienFireChance = 0.5;

   //UFO that appears randomly during standard gameplay
   private UFO ufo;

   //UFO that appears in the bonus round
   private BonusUFO bonusUfo;

   /**
    * Calls the reset method, starts timer and allows panel to respond to keys
    */
   public APInvaders() {
      Sound.initialize();   //prepare for sound!
      reset();
      addKeyListener(this);
      movement.start();
   }

   /**
    * Starts a round in standard play
    */
   public void reset() {
      tick = aliensHit = 0;
   
      //set game state
      standardPlay = true;
      gameOver = betweenLives = bonusRound = false;
   
      //no UFOs at start of round
      ufo = null;
      bonusUfo = null;
   
      //aliens move every 20 "ticks"
      cycle = 20;
   
      //instantiate all aliens
      for (int i = 0; i < aliens.length; i++) {
         for (int j = 0; j < aliens[0].length; j++) {
            aliens[i][j] = new Alien((j + 2) * App.WIDTH / 15, (i + 2) * App.HEIGHT / 15);
         }
      }
   
      //instantiate all shelters
      for (int i = 0; i < shelters.length; i++) {
         shelters[i] = new Shelter((i) * App.WIDTH / 5 + FIELD_LEFT + 100);
      }
   
      //instantiate 3 cannons
      cannons.add(new Cannon(Cannon.START_X, Cannon.START_Y));
      cannons.add(new Cannon(50, App.HEIGHT - 50));
      cannons.add(new Cannon(150, App.HEIGHT - 50));
      activeCannon = cannons.get(0);
   
      //all rows and columns are active
      leftCol = 0;
      rightCol = aliens[0].length - 1;
      bottomRow = aliens.length - 1;
   }

   /**
    * @return leftmost column containing active aliens
    */
   public int getLeftCol() {
      return leftCol;
   }

   /**
    * @return rightmost column containing active aliens
    */
   public int getRightCol() {
      return rightCol;
   }

   /**
    * @return bottommost column containing active aliens
    */
   public int getBottomRow() {
      return bottomRow;
   }


   /**
    * Updates the visuals in the frame
    *
    * @param g the graphics object to draw components
    */
   public void paintComponent(Graphics g) {
      //set the background
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, App.WIDTH, App.HEIGHT);
   
      //Graphics2D to enable wider line drawing
      Graphics2D g2 = (Graphics2D) g;
      g2.setColor(Color.WHITE);
      g2.setStroke(new BasicStroke(4));
      g2.drawLine(0, App.HEIGHT - 100, App.WIDTH, App.HEIGHT - 100);
   
      //Paint active aliens and their missiles, if any
      for (Alien[] row : aliens) {
         for (Alien alien : row) {
            if (alien != null) {
               alien.draw(g);
               if (alien.getMissile() != null) {
                  alien.getMissile().draw(g);
               
                  //check if missile is destroyed or goes offscreen
                  if (alien.getMissile().offScreen()) {
                     alien.loseMissile();
                  } else if (cannons.get(0).checkMissileCollide(alien.getMissile())) {
                     alien.loseMissile();
                  }
               }
            }
         }
      }
   
      //paint the shelters
      for (Shelter shelter : shelters) {
         if (shelter != null) {
            shelter.draw(g);
         }
      }
   
      //paint the active cannons
      for (Cannon cannon : cannons) {
         if (cannon != null) {
            cannon.draw(g);
         }
      }
   
      //paint the UFO (if active, standard play only)
      if (ufo != null) {
         ufo.draw(g);
      }
   
      //paint the bonus UFO (bonus round only)
      if (bonusUfo != null) {
         bonusUfo.draw(g);
      }
   
      //Print instructions to continue game after a life is lost
      if (betweenLives) {
         g.setColor(Color.WHITE);
         g.setFont(new Font("Arial", Font.BOLD, 20));
         g.drawString("Press Enter to Continue", getWidth() / 3, getHeight() / 2);
      }
      
      //Print instructions to restart game after game over
      else if (gameOver) {
         g.setColor(Color.WHITE);
         g.setFont(new Font("Arial", Font.BOLD, 20));
         g.drawString("GAME OVER: Press R to retry", getWidth() / 3, getHeight() / 2);
      }
   }

   /**
    * Not used.
    */
   @Override
   public void keyTyped(KeyEvent e) {
   }

   /**
    * Respond to key presses depending on game state
    */
   @Override
   public void keyPressed(KeyEvent e) {
      int key = e.getKeyCode();
   
      //respond to arrow keys and space in standard and bonus rounds
      if (bonusRound || standardPlay && cannons.get(0) != null) {
         Cannon active = cannons.get(0);
         if (key == KeyEvent.VK_LEFT) {
            active.setDx((Math.abs(active.getDx())) * -1);
            cannons.get(0).move();
         
         } else if (key == KeyEvent.VK_RIGHT) {
            active.setDx(Math.abs(active.getDx()));
            active.move();
         
         } else if (key == KeyEvent.VK_SPACE) {
            boolean missleLaunched = active.fire();
            if(missleLaunched)
               Sound.cannonShoot();
         }
      }
      
      //respond to enter key when between lives
      else if (betweenLives && key == KeyEvent.VK_ENTER) {
         cannons.remove(cannons.size() - 1);
         cannons.get(0).renewSprite();
         betweenLives = false;
         standardPlay = true;
      }
      
      //respond to R key after game over
      else if (gameOver && key == KeyEvent.VK_R) {
         cannons.clear();
         gameOver = false;
         reset();
      }
   }

   /**
    * not used
    *
    * @param e
    */
   @Override
   public void keyReleased(KeyEvent e) {
   }

   /**
    * Move all active aliens left/right and applicable alien missiles downward
    */
   public void moveAlienMissiles() {
      for (Alien[] row : aliens) {
         for (Alien alien : row) {
            if (alien != null) {
               if (alien.getMissile() != null) {
                  alien.getMissile().move();
               }
            }
         }
      }
   }

   /**
    * Move all active aliens downward. Game is over if bottom row of aliens reaches cannon level
    */
   public void dropAll() {
      for (Alien[] row : aliens) {
         for (Alien alien : row) {
            if (alien != null) {
               alien.drop();
               if (alien.getY() + Alien.HEIGHT >= cannons.get(0).getY()) {
                  standardPlay = false;
                  gameOver = true;
                  Sound.gameOver();
               }
            }
         }
      }
      Alien.changeDir();
   }

   /**
    * Check if alien matrix is at end of screen and needs to drop
    *
    * @return true if aliens dropped, false otherwise
    */
   public boolean dropCheck() {
      //Check leftmost left-moving aliens against left boundary
      if (Alien.getDx() < 0) {
         for (int i = 0; i <= bottomRow; i++) {
            if (aliens[i][leftCol] != null && aliens[i][leftCol].getX() <= FIELD_LEFT) {
               dropAll();
               return true;
            }
         }
      }
      //Check rightmost right-moving aliens against right boundary
      else {
         for (int i = 0; i <= bottomRow; i++) {
            if (aliens[i][rightCol] != null &&
                   aliens[i][rightCol].getX() + Alien.WIDTH >= FIELD_RIGHT) {
               dropAll();
               return true;
            }
         }
      }
      return false;
   }

   /**
    * Calculates the leftmost column of active aliens
    */
   public void checkLeftCol() {
      if (leftCol > rightCol) {
         return;
      }
      for (int i = 0; i <= bottomRow; i++) {
         if (aliens[i][leftCol] != null) {
            return;
         }
      }
      leftCol++;
      checkLeftCol();
   }

   /**
    * Calculates the rightmost column of active aliens
    */
   public void checkRightCol() {
      if (leftCol > rightCol) {
         return;
      }
      for (int i = 0; i <= bottomRow; i++) {
         if (aliens[i][rightCol] != null) {
            return;
         }
      }
      rightCol--;
      checkRightCol();
   }

   /**
    * Calculates the bottommost row of active aliens
    */
   public void checkBottomRow() {
      if (bottomRow < 0) {
         return;
      }
      for (int i = leftCol; i <= rightCol; i++) {
         if (aliens[bottomRow][i] != null) {
            return;
         }
      }
      bottomRow--;
      checkBottomRow();
   }

   /**
    * Initializes the bonus round by clearing all aliens and shelters and
    * spawning the bonus UFO
    */
   public void startBonusRound() {
      bonusRound = true;
      for (int i = 0; i < aliens.length; i++) {
         for (int j = 0; j < aliens[0].length; j++) {
            aliens[i][j] = null;
         }
      }
      for (int i = 0; i < shelters.length; i++) {
         shelters[i] = null;
      }
      ufo = null;
      bonusUfo = new BonusUFO();
   }

   /**
    * Checks if all aliens have been cleared, and if so, starts the bonus round
    */
   public void checkForWin() {
      for (Alien[] row : aliens) {
         for (Alien alien : row) {
            if (alien != null) {
               return;
            }
         }
      }
      standardPlay = false;
      Sound.win();
      startBonusRound();
   }

   /**
    * Removes all active alien missiles
    */
   public void removeAlienMissiles() {
      for (Alien[] row : aliens) {
         for (Alien alien : row) {
            if (alien != null) {
               alien.loseMissile();
            }
         }
      }
   }

   /**
    * Checks all aliens against the active cannon. If the alien has a missile that has
    * touched the cannon, all cannon and alien missiles are removed
    *
    * @return true if an alien missile made contact the active cannon, false otherwise
    */
   public boolean checkAlienHitsCannon() {
      for (Alien[] row : aliens) {
         for (Alien alien : row) {
            if (alien != null && alien.hitTarget(activeCannon)) {
               removeAlienMissiles();
               activeCannon.removeMissiles();
               Sound.cannonHit();
               return true;
            }
         }
      }
      return false;
   }

   /**
    * Checks all active aliens. If an active alien has an active missile, checks if
    * the missile has made contact with the shelter
    *
    * @param shelter the shelter to check for hit
    * @return true if an alien missile hit the shelter, false otherwise
    */
   public boolean checkAlienHitsShelter(Shelter shelter) {
      for (Alien[] row : aliens) {
         for (Alien alien : row) {
            if (alien != null && alien.getMissile() != null) {
               if (alien.hitTarget(shelter)) {
                  return true;
               }
            }
         }
      }
      return false;
   }

   /**
    * Checks if the active cannon hit shelter with one of its missiles
    *
    * @param shelter the shelter to check for a hit
    * @return true if a cannon missile hit a shelter, false otherwise
    */
   public boolean checkCannonHitsShelter(Shelter shelter) {
      return activeCannon.hitTarget(shelter);
   }

   /**
    * Checks if one of the cannon's missiles hit one of the active aliens.
    * If so, checks for a win
    *
    * @return true if the cannon hit an alien, false otherwise
    */
   public boolean checkCannonHitsAlien() {
      for (int i = 0; i <= bottomRow; i++) {
         for (int j = leftCol; j <= rightCol; j++) {
            if (aliens[i][j] != null && activeCannon.hitTarget(aliens[i][j])) {
               aliens[i][j] = null;
               checkForWin();
               checkLeftCol();
               checkRightCol();
               checkBottomRow();
               Sound.alienHit();
               return true;
            }
         }
      }
      return false;
   }

   /**
    * Sets game state to "game over" when one cannon remains, otherwise
    * sets game state to "between lives"
    */
   public void checkBetweenLives() {
      if (cannons.size() == 1) {
         gameOver = true;
         cycle = 20;
         Sound.gameOver();
      } else {
         betweenLives = true;
      }
   }

   /**
    * Generates a random alien in the bounds of (0, leftCol) and (bottomRow, rightCol)
    * If the alien is active, that alien fires a missile.
    */
   public void spawnAlienMissile() {
      int row = (int) (Math.random() * (bottomRow + 1));
      int col = (int) (Math.random() * (rightCol - leftCol + 1) + leftCol);
      if (aliens[row][col] != null) {
         aliens[row][col].fire();
      }
   }

   /**
    * Handles frame updates
    */
   class Movement implements ActionListener {
   
      @Override
      public void actionPerformed(ActionEvent e) {
      
         //Standard round
         if (standardPlay) {
            tick = (tick + 1) % cycle;
         
            //aliens move horizontally
            if (!dropCheck()) {
            
               //aliens move only every "cycle" frames
               if (tick == 0) {
               
                  //Generate possibility of spawning an alien missile
                  if (Math.random() < alienFireChance) {
                     spawnAlienMissile();
                  }
               
                  //Generate possibility of spawning the UFO if not currently active
                  if (ufo == null && Math.random() < .01) {
                     ufo = new UFO();
                  }
               
                  //Move all active aliens and check if a moved alien hits any shelter
                  for (Alien[] row : aliens) {
                     for (Alien alien : row) {
                        if (alien != null) {
                           alien.move();
                           for (Shelter shelter : shelters) {
                              if (shelter != null) {
                                 shelter.touchedByAlien(alien);
                              }
                           }
                        }
                     }
                  }
               }
            
               //Moves UFO if active
               if (ufo != null) {
                  ufo.move();
               
                  //Checks if a cannon missile hit the UFO
                  if (activeCannon.hitTarget(ufo)) {
                     Sound.cannonHit();
                     ufo = null;
                  }
                  
                  //Checks if UFO escaped off screen
                  else {
                     if (ufo.offScreen()) {
                        ufo = null;
                     }
                  
                  }
               }
            
               //move all active alien missiles
               moveAlienMissiles();
            
               //check if a cannon missile hit an alien
               if (checkCannonHitsAlien()) {
                  aliensHit++;
               
                  //every 3rd alien hit, speed alien movement and increase
                  //probability for aliens spawning a missile
                  if (aliensHit % 3 == 0) {
                     cycle--;
                     alienFireChance += 0.05;
                  }
               }
            
               //check if an alien missile hits the cannon. If so, check if
               //game state is "between lives" or "game over"
               if (checkAlienHitsCannon()) {
                  standardPlay = false;
                  checkBetweenLives();
               }
            
               //Check all active shelters to see if any alien or cannon missile
               //has hit it
               for (Shelter shelter : shelters) {
                  if (shelter != null) {
                     checkAlienHitsShelter(shelter);
                     checkCannonHitsShelter(shelter);
                  }
               }
            }
            repaint();
         }
         
         //bonus round update
         else if (bonusRound) {
            bonusUfo.move();
            System.out.println(bonusUfo.getX());
         
            //if a cannon missile hits the ufo, ufo shrinks and changes direction
            if (activeCannon.hitTarget(bonusUfo)) {
               bonusUfo.onHit();
            }
            
            //check if ufo went offscreen. If so, game over.
            else if (bonusUfo.offScreen()) {
               bonusRound = false;
               gameOver = true;
               Sound.gameOver();
            }
            repaint();
         }
         
         //game over - make aliens "flaunt" victory
         else if (gameOver) {
            tick = (tick + 1) % cycle;
            if (tick == 0) {
               for (Alien[] row : aliens) {
                  for (Alien alien : row) {
                     if (alien != null) {
                        alien.moveInPlace();
                     }
                  }
               }
            }
            repaint();
         }
      }
   }
}

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The App class displays the window containing the AP Invaders game panel.
 * @author Kimberly A Baram
 * @version 1.0 7 February 2022
 */

public class App {

    //window dimensions
    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;

    public static void main(String[] args) throws Exception {
        JFrame frame = new JFrame("AP Invaders");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(100, 50);
        APInvaders field = new APInvaders();
        frame.addKeyListener(field);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(field);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}

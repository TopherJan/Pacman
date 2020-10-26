import javax.swing.JFrame;
import java.awt.*;
public class Game {
  public static void main(String[] args){
    JFrame window = new JFrame("Platform");
    //window.setExtendedState(JFrame.MAXIMIZED_BOTH);
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    window.setBounds(0,0,1366,768);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setContentPane(new GamePanel());
    //window.setBounds(0,0,1350,740);
    //window.setSize(1350,740);
    //window.pack();
    window.setUndecorated(true);
    window.setVisible(true);
  }
}

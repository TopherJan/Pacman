import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.*;
import javax.swing.*;

public class Main {
  
  public static void main(String args[]) {
	Frame frame;
	JWindow window = new JWindow();
	SoundClip startClip = new SoundClip("audio/gs_start1.wav", 0);
	startClip.start();
	try {
      for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (Exception e) {}
  
  window.getContentPane().add(
    new JLabel("", new ImageIcon("images/intro2.gif"), SwingConstants.CENTER));
    window.setBounds(440, 180, 500, 315);
    window.setVisible(true);
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    window.setVisible(false);
	Frame.checker = false;
	startClip.stop();
	frame = new Frame();

    window.dispose();


  frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frame.setUndecorated(true);
  frame.setVisible(true);
  }
}

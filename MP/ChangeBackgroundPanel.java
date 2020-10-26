import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;


public class ChangeBackgroundPanel extends JPanel {
  private Image backgroundIcon;
  private JLabel centerLabel;

  public ChangeBackgroundPanel(String directory) {
    this.setLayout(null);

    backgroundIcon = new ImageIcon(directory).getImage();
  }

  public void addCenterLabel(String nameLabel, String styleFont, int sizeFont, Color colorFont) {
    centerLabel = new JLabel(nameLabel);
    centerLabel.setFont(new Font(styleFont, Font.BOLD, sizeFont));
    centerLabel.setForeground(colorFont);
    centerLabel.setBounds(330,630,1000,100);
    add(centerLabel);
    BlinkFrame frame = new BlinkFrame(centerLabel);
    frame.start();
  }

  public void paintComponent(Graphics g) {
    g.drawImage(backgroundIcon, 0, 0, getWidth(), getHeight(), this);
  }

}

import javax.swing.*;
import java.awt.*;

public class TileGrid {
  private int size;
  private int x, y;
  private JPanel panel;
  public TileGrid(int size) {

    this.size = size;

  }
  public void draw(int x, int y, JPanel panel, Graphics g, String filename){
    g.drawImage(new ImageIcon(filename).getImage(), x, y, 30, 30, null);
  }

}

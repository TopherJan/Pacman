import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
public class TileMap {
  private int x;
  private int y;

  private int tileSize;
  private int[][] map;
  private int mapWidth;
  private int mapHeight;
  private BufferedImage slate;
    private TexturePaint slatetp;
    private TileGrid tileGrid;
    private Ghost ghost;
    public Color color = Color.RED;
  BufferedImage image;
  JPanel panel;
  public int flag = 1;
  public boolean lol = true;
  
  public TileMap(String s, int tileSize, JPanel panel){
    this.panel = panel;
    this.tileSize = tileSize;

    tileGrid = new TileGrid(tileSize);
    try{
      FileWriter fw2 = new FileWriter("inside.txt");
      FileWriter fw = new FileWriter("testmap.txt");

      fw.write("45" + "\n");;
      fw.write("24" + "\n");;
      fw.write("1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1" + "\n");
      fw.write("1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1" + "\n");
      fw.write("1 0 3 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 3 0 1" + "\n");
      fw.write("1 0 2 0 0 0 0 0 2 0 2 0 0 0 0 0 0 0 0 0 2 0 2 0 2 0 0 0 0 0 0 0 0 0 2 0 2 0 0 0 0 0 2 0 1" + "\n");
      fw.write("1 0 2 0 2 2 2 2 2 0 2 0 1 1 1 1 1 1 1 0 2 0 2 0 2 0 1 1 1 1 1 1 1 0 2 0 2 2 2 2 2 0 2 0 1" + "\n");
      fw.write("1 0 2 0 2 0 0 0 2 0 2 0 0 0 0 0 0 0 0 0 2 0 2 0 2 0 0 0 0 0 0 0 0 0 2 0 2 0 0 0 2 0 2 0 1" + "\n");
      fw.write("1 0 2 0 2 0 1 0 2 0 2 2 2 2 2 2 2 2 2 2 2 0 2 0 2 2 2 2 2 2 2 2 2 2 2 0 2 0 1 0 2 0 2 0 1" + "\n");
      fw.write("1 0 2 0 2 0 0 0 2 0 0 0 0 0 0 0 0 2 0 0 0 0 2 0 0 0 0 2 0 0 0 0 0 0 0 0 2 0 0 0 2 0 2 0 1" + "\n");
      fw.write("1 0 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 0 1" + "\n");
      fw.write("1 0 2 0 0 0 0 0 2 0 0 0 0 0 0 0 0 2 0 0 0 0 1 0 0 0 0 2 0 0 0 0 0 0 0 0 2 0 0 0 0 0 2 0 1" + "\n");
      fw.write("1 0 2 2 2 2 2 2 2 0 1 1 1 1 1 1 0 2 0 1 1 1 1 1 1 1 0 2 0 1 1 1 1 1 1 0 2 2 2 2 2 2 2 0 1" + "\n");
      fw.write("1 0 2 0 0 0 0 0 2 0 0 0 0 0 0 0 0 2 0 0 0 0 0 0 0 0 0 2 0 0 0 0 0 0 0 0 2 0 0 0 0 0 2 0 1" + "\n");
      fw.write("1 0 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 0 1" + "\n");
      fw.write("1 0 2 0 2 0 0 0 2 0 0 0 0 0 0 0 0 2 0 0 0 0 2 0 0 0 0 2 0 0 0 0 0 0 0 0 2 0 0 0 2 0 2 0 1" + "\n");
      fw.write("1 0 2 0 2 0 1 0 2 0 2 2 2 2 2 2 2 2 2 2 2 0 2 0 2 2 2 2 2 2 2 2 2 2 2 0 2 0 1 0 2 0 2 0 1" + "\n");
      fw.write("1 0 2 0 2 0 0 0 2 0 2 0 0 0 0 0 0 0 0 0 2 0 2 0 2 0 0 0 0 0 0 0 0 0 2 0 2 0 0 0 2 0 2 0 1" + "\n");
      fw.write("1 0 1 0 2 2 2 2 2 0 2 0 1 1 1 1 1 1 1 0 2 0 2 0 2 0 1 1 1 1 1 1 1 0 2 0 2 2 2 2 2 0 2 0 1" + "\n");
      fw.write("1 0 2 0 0 0 0 0 2 0 2 0 0 0 0 0 0 0 0 0 2 0 2 0 2 0 0 0 0 0 0 0 0 0 2 0 2 0 0 0 0 0 2 0 1" + "\n");
      fw.write("1 0 3 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 3 0 1" + "\n");
      fw.write("1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1" + "\n");
      fw.write("1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1" + "\n");
      fw.write("1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1" + "\n");
      fw.write("1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1" + "\n");
      fw.write("1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1" + "\n");

      fw.close();
      fw2.write("41" + "\n");
      fw2.write("17" + "\n");
      fw2.write("3 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 3" + "\n");
      fw2.write("2 0 0 0 0 0 2 0 2 0 0 0 0 0 0 0 0 0 2 0 2 0 2 0 0 0 0 0 0 0 0 0 2 0 2 0 0 0 0 0 2" + "\n");
      fw2.write("2 0 2 2 2 2 2 0 2 0 1 1 1 1 1 1 1 0 2 0 2 0 2 0 1 1 1 1 1 1 1 0 2 0 2 2 2 2 2 0 2" + "\n");
      fw2.write("2 0 2 0 0 0 2 0 2 0 0 0 0 0 0 0 0 0 2 0 2 0 2 0 0 0 0 0 0 0 0 0 2 0 2 0 0 0 2 0 2" + "\n");
      fw2.write("2 0 2 0 1 0 2 0 2 2 2 2 2 2 2 2 2 2 2 0 2 0 2 2 2 2 2 2 2 2 2 2 2 0 2 0 1 0 2 0 2" + "\n");
      fw2.write("2 0 2 0 0 0 2 0 0 0 0 0 0 0 0 2 0 0 0 0 2 0 0 0 0 2 0 0 0 0 0 0 0 0 2 0 0 0 2 0 2" + "\n");
      fw2.write("2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2" + "\n");
      fw2.write("2 0 0 0 0 0 2 0 0 0 0 0 0 0 0 2 0 0 0 0 1 0 0 0 0 2 0 0 0 0 0 0 0 0 2 0 0 0 0 0 2" + "\n");
      fw2.write("2 2 2 2 2 2 2 0 1 1 1 1 1 1 0 2 0 1 1 1 1 1 1 1 0 2 0 1 1 1 1 1 1 0 2 2 2 2 2 2 2" + "\n");
      fw2.write("2 0 0 0 0 0 2 0 0 0 0 0 0 0 0 2 0 0 0 0 0 0 0 0 0 2 0 0 0 0 0 0 0 0 2 0 0 0 0 0 2" + "\n");
      fw2.write("2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2" + "\n");
      fw2.write("2 0 2 0 0 0 2 0 0 0 0 0 0 0 0 2 0 0 0 0 2 0 0 0 0 2 0 0 0 0 0 0 0 0 2 0 0 0 2 0 2" + "\n");
      fw2.write("2 0 2 0 1 0 2 0 2 2 2 2 2 2 2 2 2 2 2 0 2 0 2 2 2 2 2 2 2 2 2 2 2 0 2 0 1 0 2 0 2" + "\n");
      fw2.write("2 0 2 0 0 0 2 0 2 0 0 0 0 0 0 0 0 0 2 0 2 0 2 0 0 0 0 0 0 0 0 0 2 0 2 0 0 0 2 0 2" + "\n");
      fw2.write("2 0 2 2 2 2 2 0 2 0 1 1 1 1 1 1 1 0 2 0 2 0 2 0 1 1 1 1 1 1 1 0 2 0 2 2 2 2 2 0 2" + "\n");
      fw2.write("2 0 0 0 0 0 2 0 2 0 0 0 0 0 0 0 0 0 2 0 2 0 2 0 0 0 0 0 0 0 0 0 2 0 2 0 0 0 0 0 2" + "\n");
      fw2.write("3 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 2 3" + "\n");

      fw2.close();

      BufferedReader br = new BufferedReader(new FileReader(s));
      mapWidth = Integer.parseInt(br.readLine());
      mapHeight = Integer.parseInt(br.readLine());
      map = new int[mapHeight][mapWidth];

      String delimiters = " ";
      for(int row = 0; row < mapHeight; row++){
        String line = br.readLine();
        String[] tokens = line.split(delimiters);
        for(int col = 0; col < mapWidth; col++){
          map[row][col] = Integer.parseInt(tokens[col]);
        }
      }
    }catch(Exception e){}
  }

  public int getx(){return x;}
  public int gety(){return y;}

  public int getColTile(int x){
    return x / tileSize;
  }
  public int getRowTile(int y){
    return y / tileSize;
  }
  public int getTile(int row, int col){
    return map[row][col];
  }
  public int getTileSize(){
    return tileSize;
  }

  public void setx(int i){x = i;}
  public void sety(int i){y = i;}

  public void update(){
  }
  public void draw(Graphics2D g){
    try{
      BufferedReader br = new BufferedReader(new FileReader("testmap.txt"));
      mapWidth = Integer.parseInt(br.readLine());
      mapHeight = Integer.parseInt(br.readLine());
      map = new int[mapHeight][mapWidth];

      String delimiters = " ";
      for(int row = 0; row < mapHeight; row++){
        String line = br.readLine();
        String[] tokens = line.split(delimiters);
        for(int col = 0; col < mapWidth; col++){
          map[row][col] = Integer.parseInt(tokens[col]);
        }
      }
    }catch(Exception e){}

    for(int row = 0; row < mapHeight; row++){
      for(int col = 0; col < mapWidth; col++){
        int rc = map[row][col];

        if(rc == 0){
          // tileGrid.draw(x + col * (tileSize), y + row * (tileSize),panel,g, "tile2.png");
          g.setColor(new Color(90,90,90));
          g.fillRect(x + col * (tileSize), y + row * (tileSize), tileSize, tileSize);
        }
        if(rc == 1){
          g.setColor(Color.BLACK);
          g.fillRect(x + col * (tileSize), y + row * (tileSize), tileSize, tileSize);
        }

        if(rc == 2){
          g.setColor(Color.BLACK);
          g.fillRect(x + col * (tileSize), y + row * (tileSize), tileSize, tileSize);
          g.drawImage(new ImageIcon("images/powerUpSprite/dots.png").getImage(),(x + col * (tileSize))+11, (y + row * (tileSize))+11, 10, 10, null);

        }
        if(rc == 3){

          g.setColor(Color.BLACK);
          g.fillRect(x + col * (tileSize), y + row * (tileSize), tileSize, tileSize);
          g.drawImage(new ImageIcon("images/powerUpSprite/hehe.gif").getImage(),(x + col * (tileSize))+4, (y + row * (tileSize))+4, 20, 20, null);
        }
      }
    }
  }
}
import java.util.Scanner;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Player {
  protected SoundClip eatClip = new SoundClip("audio/gs_eatfruit.wav", 1);
	
  public static double x;
  public static double tempx;
  public static double y;
  public static double tempy;
  public static double dx;
  public double dy;

  private int width;
  private int height;
  public int lives = 3;
  public static volatile int counter = 0;
  public static volatile int score = 0;

  public static boolean left;
  public static boolean right;
  public static boolean up;
  public static boolean down;
  private Scanner input;
  private double moveSpeed;
  private double maxSpeed;
  private double maxFallingSpeed;
  private double stopSpeed;
  private double jumpStart;
  private double gravity;

  public static boolean checker = false;
  private boolean topLeft;
  private boolean topRight;
  private boolean bottomLeft;
  private boolean bottomRight;
  private File file;
  private int map[][];
  
  int leftTile;
  int rightTile;
  int topTile;
  int bottomTile;
  int z, a, tilex = 0, tiley = 420, tx, ty;
  static int count = 1;
  int row, col;
  StringBuilder sb;
  private Boolean change = true;;
  private int movement;
  private String moveSprite = "images/pacmanSprite/closed.png";
  private int ctr = 0;
  private int c = 0;
  private int move[] = new int[2];
  //private int counter = 0;
  private int mazeWidth;
  private int initial;
  private int mazeHeight;
  private int maze[][];
  private int xTile[] = {0,0,0,0,0,2,2,2,2,6,6,6,6,6,6,6,8,8,8,8,15,15,15,15,18,18,18,18,20,20,20,20,22,22,22,22,25,25,25,25,32,32,32,32,34,34,34,34,34,34,34,38,38,38,38,40,40,40,40,40, 20};
  private int yTile[] = {0,6,8,10,16,2,6,10,14,0,2,6,8,10,14,16,0,4,12,16,4,6,10,12,0,4,12,16,0,6,10,16,0,4,12,16,4,6,10,12,0,4,12,16,0,2,6,8,10,14,16,2,6,10,14,0,6,8,10,16, 8};
  private String[] store = new String[19];
  JLabel label = new JLabel(new ImageIcon("images/pacmanSprite/closed.png"));
  private TileMap tileMap;
  public Player(TileMap tm){
    input = new Scanner(System.in);
    tileMap = tm;

    width = 30;
    height = 30;

    moveSpeed = 5;
    maxSpeed = 5;
    maxFallingSpeed = 12;
    stopSpeed = 0.30;
    jumpStart = -11.0;
    try{
      BufferedReader br = new BufferedReader(new FileReader("inside.txt"));
      int mapWidth = Integer.parseInt(br.readLine());
      int mapHeight = Integer.parseInt(br.readLine());
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
    gravity = 64.0;
  }
  public void setLeft(boolean b){left = b;}
  public void setRight(boolean b){right = b;}
  public void setUp(boolean b){up = b;}
  public void setDown(boolean b){down = b;}
  public void setx(int i){x = i;}
  public void sety(int i){y = i;}


  public void update(){
	if(isAvailable(movement)){
      if(movement == 1 || movement == 2){
        if(isIntersection()){
          dx = 0;
        }
      }else if(movement == 3 || movement == 4){
        if(isIntersection()){
          dy = 0;
        }
      }
    }
    if(isIntersection()){
      if(movement == 1){
        moveSprite = "images/pacmanSprite/pakUp.gif";
      }
      if(movement == 2){
        moveSprite = "images/pacmanSprite/pakDown.gif";
      }
      if(movement == 3){
        if(dy > 0 ){
          moveSprite = "images/pacmanSprite/pakRight.gif";
        }
      }
      if(movement ==4){
        moveSprite = "images/pacmanSprite/pakLeft.gif";
      }
    }
	
	
    if(up){
      dy = -maxSpeed;
      movement = 1;
    }
    if(down){
      dy = maxSpeed;
      movement = 2;
    }
    if(right){
      dx = maxSpeed;
      movement = 3;
    }
    if(left){
      dx = -maxSpeed;
      movement = 4;
    }

    int currCol = tileMap.getColTile((int) x);
    int currRow = tileMap.getRowTile((int) y);

    double tox = x + dx;
    double toy = y + dy;

    tempx = x;
    tempy = y;

    calculateCorners(x, toy);
    if( dy < 0){
      if(topLeft || topRight){
       // dy = 0;
        tempy = currRow * tileMap.getTileSize() + height / 2;
      }else{
        tempy += dy;
		moveSprite = "images/pacmanSprite/pakUp.gif";
      }
    }
    if(dy > 0){
      if(bottomLeft || bottomRight){
        //dy = 0;
        tempy = (currRow + 1) * tileMap.getTileSize() - height/2;
      }else{
        tempy += dy;
		moveSprite = "images/pacmanSprite/pakDown.gif";
      }
    }

    try{
      input = new Scanner(new File("inside.txt"));
      for(int row = 0; row < 24; row++){
        store[row] = input.nextLine();
      }
    }catch(Exception e){}
      //System.out.println("old row: " + store[(tiley/30)+2]);
     // //System.out.println("char: " + store[(tiley/30)+2].charAt((tilex/30)*2));
      sb = new StringBuilder(store[(tiley/30)+2]);
      //sb.setCharAt(0, '3');
	  if(sb.charAt((tilex/30)*2) != '1' && sb.charAt((tilex/30) * 2) != '0'){
		if(sb.charAt((tilex/30)*2) == '3'){
			checker = true;
			Player2.checker = true;
			count = 0;
			Player2.count = 0;
			Ghost.makeVulnerable();
			Ghost1.makeVulnerable();
			Ghost2.makeVulnerable();
			Ghost3.makeVulnerable();
		}
		
		sb.setCharAt((tilex/30)*2, '1');
		counter += 1;
		score += 1;
		//System.out.println(" " +counter);
		eatClip.start();
		
		if(counter == 342){
			GamePanel.running = false;
			GamePanel.moveClip.stop();
			if(counter == 342){
				GamePanel.running = false;
				GamePanel.moveClip.stop();
				
				try{
					BufferedReader br = new BufferedReader(new FileReader("highscore.txt"));
					int highScore = Integer.parseInt(br.readLine());
					
					br.close();
					
					if(score > highScore){
						//FileWriter scoreWriter = new FileWriter("highscore.txt");
						FileWriter fileOut = new FileWriter("highscore.txt");
						fileOut.write(Integer.toString(score));
						fileOut.close();
					}
				}catch(Exception e){}
			}
		}
	}
	
	  if((tilex/30 == Ghost.tilex/30)&&(tiley/30 == Ghost.tiley/30)&& checker == true && Ghost.isVulnerable == true){
		score += 5;
		Ghost.x = 676;
		Ghost.y = 316;
		Ghost.makeActive();
	  }
	  else if((tilex/30 == Ghost.tilex/30)&&(tiley/30 == Ghost.tiley/30)&& checker == true && Ghost.isVulnerable == false){
		GamePanel.setPlayer(score);
	  }
	  if((tilex/30 == Ghost.tilex/30)&&(tiley/30 == Ghost.tiley/30)&& checker == false && Ghost.isVulnerable == false){
		GamePanel.setPlayer(score);
	  }
	  
	  if((tilex/30 == Ghost1.tilex/30)&&(tiley/30 == Ghost1.tiley/30)&& checker == true && Ghost1.isVulnerable == true){
		score += 5;
		Ghost1.x = 676;
		Ghost1.y = 316;
		Ghost1.makeActive();
	  }
	  else if((tilex/30 == Ghost1.tilex/30)&&(tiley/30 == Ghost1.tiley/30)&& checker == true && Ghost1.isVulnerable == false){
		GamePanel.setPlayer(score);
	  }
	  if((tilex/30 == Ghost1.tilex/30)&&(tiley/30 == Ghost1.tiley/30)&& checker == false && Ghost1.isVulnerable == false){
		GamePanel.setPlayer(score);
	  }
	  
	  if((tilex/30 == Ghost2.tilex/30)&&(tiley/30 == Ghost2.tiley/30)&& checker == true && Ghost2.isVulnerable == true){
		score += 5;
		Ghost2.x = 676;
		Ghost2.y = 316;
		Ghost2.makeActive();
	  }
	  else if((tilex/30 == Ghost2.tilex/30)&&(tiley/30 == Ghost2.tiley/30)&& checker == true && Ghost2.isVulnerable == false){
		GamePanel.setPlayer(score);
	  }
	  if((tilex/30 == Ghost2.tilex/30)&&(tiley/30 == Ghost2.tiley/30)&& checker == false && Ghost2.isVulnerable == false){
		GamePanel.setPlayer(score);
	  }
	  
	  if((tilex/30 == Ghost3.tilex/30)&&(tiley/30 == Ghost3.tiley/30)&& checker == true && Ghost3.isVulnerable == true){
		score += 5;
		Ghost3.x = 676;
		Ghost3.y = 316;
		Ghost3.makeActive();
	  }
	  else if((tilex/30 == Ghost3.tilex/30)&&(tiley/30 == Ghost3.tiley/30)&& checker == true && Ghost3.isVulnerable == false){
		GamePanel.setPlayer(score);
	  }
	  if((tilex/30 == Ghost3.tilex/30)&&(tiley/30 == Ghost3.tiley/30)&& checker == false && Ghost3.isVulnerable == false){
		GamePanel.setPlayer(score);
	  }
	  
	  count++;
	   System.out.println("Player1 counter: "+count);
	   
	  if(checker == true && count/150 == 1){
		if(Ghost.isVulnerable == true)
			Ghost.ghostName = "images/ghostSprite/endvulnerable.gif";
		if(Ghost1.isVulnerable == true)
			Ghost1.ghostName = "images/ghostSprite/endvulnerable.gif";
		if(Ghost2.isVulnerable == true)
			Ghost2.ghostName = "images/ghostSprite/endvulnerable.gif";
		if(Ghost3.isVulnerable == true)
			Ghost3.ghostName = "images/ghostSprite/endvulnerable.gif";
	  }
	  
	  if(checker == true && count/150 == 2){
		checker = false;
		Player2.checker = false;
		Ghost.makeActive();
		Ghost1.makeActive();
		Ghost2.makeActive();
		Ghost3.makeActive();
	  }
	  
	  ////System.out.println("changed to 1: " + (tilex/30)*2);
	  
      store[(tiley/30)+2] = sb.toString();
      ////System.out.println("new row: " + store[(tiley/30)+2]);

        try{
          FileWriter fw2 = new FileWriter("inside.txt");
          FileWriter fw = new FileWriter("testmap.txt");

           for(String s: store){
             fw2.write(s + "\n");
           }

           fw.write("45" + "\n");
           fw.write("24"+ "\n");
           fw.write("1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1"+ "\n");
           fw.write("1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1"+ "\n");

          for(int i = 2; i < 19; i++){
              fw.write("1 0 " + store[i] + " 0 1\n");
          }
          fw.write("1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1"+ "\n");
          fw.write("1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1"+ "\n");
          fw.write("1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1"+ "\n");
          fw.write("1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1"+ "\n");
          fw.write("1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1"+ "\n");

          fw.flush();
          fw.close();
          fw2.flush();
          fw2.close();

        }catch(Exception e){}

    calculateCorners(tox, y);
    if(dx < 0){
      if(topLeft || bottomLeft){
        //dx = 0;
        tempx = currCol * tileMap.getTileSize() + width / 2;
      }else{
        tempx += dx;
		moveSprite = "images/pacmanSprite/pakLeft.gif";
	  }
    }
    if(dx > 0){
      if(topRight || bottomRight){
        //dx = 0;
        tempx = (currCol + 1) * tileMap.getTileSize() - width / 2;
      }else{
        tempx += dx;
		moveSprite = "images/pacmanSprite/pakRight.gif";
      }
    }
    x = tempx;
    y = tempy;
	
  }
  private void calculateCorners(double x, double y){
	leftTile = tileMap.getColTile((int) (x - width/2));
	rightTile = tileMap.getColTile((int) (x + width/2)-1);
	topTile = tileMap.getRowTile((int) (y - height/2));
	bottomTile = tileMap.getRowTile((int) (y + height/2)-1);
	topLeft = tileMap.getTile(topTile, leftTile) == 0;
	topRight = tileMap.getTile(topTile, rightTile) == 0;
	bottomLeft = tileMap.getTile(bottomTile, leftTile) == 0;
	bottomRight = tileMap.getTile(bottomTile, rightTile) == 0;
  }
  
  public Boolean isAvailable(int a){
    if(a == 1){
      if(dx < 0){
        if(isUpAllowed(0));
          return true;
      }else if(dx > 0){
        if(dx > 0){
          if(isUpAllowed(29));
            return true;
        }
      }
    }
    if(a == 2){
      if(dx < 0){
        if(isDownAllowed(0));
          return true;
      }else if(dx > 0){
        if(dx > 0){
          if(isDownAllowed(29));
            return true;
        }
      }
    }
    if(a == 3){
      if(dy < 0){
        if(isRightAllowed(0));
          return true;
      }else if(dy > 0){
        if(isRightAllowed(29));
          return true;
      }
    }
    if(a == 4){
      if(dy < 0){
        if(isLeftAllowed(0));
          return true;
      }else if(dy > 0){
        if(isLeftAllowed(29));
          return true;
      }
    }
    return false;
  }

  public void openAndReadFile(){
	try{
		BufferedReader br = new BufferedReader(new FileReader("lol.txt"));
		int mazeWidth = Integer.parseInt(br.readLine());
		int mazeHeight = Integer.parseInt(br.readLine());
		maze = new int[mazeHeight][mazeWidth];

		String delimiters = " ";
		for(int row = 0; row < mazeHeight; row++){
		  String line = br.readLine();
		  String[] tokens = line.split(delimiters);
		  for(int col = 0; col < mazeWidth; col++){
			maze[row][col] = Integer.parseInt(tokens[col]);
		  }
		}
	}catch(Exception e){}
  }

	//copy adi na function
	public Boolean isRightAllowed(int a){
	  if(tilex + 30<= 1200){
		if(map[(tiley + a)/30][(tilex + 30)/30] == 1){
		  return true;
		}
	  }
	  return false;
	}
	//copy adi na function
	public Boolean isLeftAllowed(int a){
	  if(tilex >= 30){
		if(map[(tiley + a)/30][(tilex - 30)/30] == 1){
		  return true;
		}
	  }
	  return false;
	}

	//copy adi na function
	public Boolean isUpAllowed(int a){
	  if(tiley >= 30){
		if(map[(tiley - 30)/30][(tilex + a)/30] == 1){
		  return true;
		}
	  }
	  return false;
	}
	//copy adi na function
	public Boolean isDownAllowed(int a){
	  if(tiley + 30<= 480){
		if(map[(tiley + 30)/30][(tilex + a)/30] == 1){
		  return true;
		}
	  }
	  return false;
	}


	public Boolean isIntersection(){
	  for(int a = 0; a < 60; a++){
		if(tilex == xTile[a]*30 && tiley == yTile[a]*30){
		  return true;
		}
	  }
	  return false;
	}


  public void draw(Graphics2D g, String string){
	tx = tileMap.getx();
    ty = tileMap.getx();
    tilex = (int)(x-75);
    tiley = (int)(y-75);
    g.setColor(Color.BLACK);
    g.drawImage(new ImageIcon(moveSprite).getImage(), (int) (tx + x - width/2), (int) (ty + y - width/2), width, height, null);
	
  }
}
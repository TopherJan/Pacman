import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class Ghost3 {
  public static double x;
  public static double y;
  public static double dx;
  public static double dy;

  private int map[][];
  private int z, n, leftTile, rightTile;
  private int height, a, topTile, bottomTile;
  private int l, r, u, d;
  public static int tilex, tiley, width, tx, ty;
  public static boolean left;
  public static boolean right;
  public static boolean up;
  public static boolean down;

  public static double moveSpeed;
  public static double maxSpeed;
  private double maxFallingSpeed;
  private double stopSpeed;
  private double jumpStart;
  private double gravity;

  public static boolean imageBool = true;
  public static boolean isVulnerable;
  private boolean topLeft;
  private boolean topRight;
  private boolean bottomLeft;
  private boolean bottomRight;

  static String ghostName = "images/ghostSprite/orangeghost.png";
  Random rand = new Random();
  private int xTile[] = {0,0,0,0,0,2,2,2,2,6,6,6,6,6,6,6,8,8,8,8,15,15,15,15,18,18,18,18,20,20,20,20,22,22,22,22,25,25,25,25,32,32,32,32,34,34,34,34,34,34,34,38,38,38,38,40,40,40,40,40,20};
  private int yTile[] = {0,6,8,10,16,2,6,10,14,0,2,6,8,10,14,16,0,4,12,16,4,6,10,12,0,4,12,16,0,6,10,16,0,4,12,16,4,6,10,12,0,4,12,16,0,2,6,8,10,14,16,2,6,10,14,0,6,8,10,16,8};
  private TileMap tileMap;

  public Ghost3(TileMap tm){
    tileMap = tm;
	this.ghostName = ghostName;
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
  public static void setx(int i){x = i;}
  public static void sety(int i){y = i;}


  public void update(){
	for(int i = 0; i < 61; i++){
      if(tilex == xTile[i]*30 && tiley == yTile[i]*30){
        dy = 0;
        dx = 0;
        if(tilex < 30){
          tilex = 30;
		  n = rand.nextInt(4) + 1;
			if(n == 1){
				moveLeft();
			}else if (n == 2){
				moveRight();
			}else if (n == 3){
				moveUp();
			}else if (n == 4){
				moveDown();
			}
        }else if(tiley == 480){
          tiley = 480;
		  n = rand.nextInt(4) + 1;
			if(n == 1){
				moveLeft();
			}else if (n == 2){
				moveRight();
			}else if (n == 3){
				moveUp();
			}else if (n == 4){
				moveDown();
			}
        }else if(tilex == 1200){
          tilex = 1200;
		  n = rand.nextInt(4) + 1;
			if(n == 1){
				moveLeft();
			}else if (n == 2){
				moveRight();
			}else if (n == 3){
				moveUp();
			}else if (n == 4){
				moveDown();
			}
        } else if(tiley < 30){
          tiley = 30;
		  n = rand.nextInt(4) + 1;
			if(n == 1){
				moveLeft();
			}else if (n == 2){
				moveRight();
			}else if (n == 3){
				moveUp();
			}else if (n == 4){
				moveDown();
			}
        }else{
			l = map[tiley/30][(tilex-30)/30];
			r = map[tiley/30][(tilex+30)/30];
			u = map[(tiley-30)/30][tilex/30];
			d = map[(tiley+30)/30][tilex/30];

		  randomMove();
          //check top tile
        }

      }
    }

    if(up){
      moveUp();
    }
    if(down){
      moveDown();
    }
    if(right){
	  moveRight();
    }
    if(left){
      moveLeft();
    }

    //check collisions
    int currCol = tileMap.getColTile((int) x);
    int currRow = tileMap.getRowTile((int) y);

    double tox = x + dx;
    double toy = y + dy;

    double tempx = x;
    double tempy = y;

    calculateCorners(x, toy);
    if( dy < 0){
      if(topLeft || topRight){
        moveRight();
        tempy = currRow * tileMap.getTileSize() + height / 2;
      }else{
        tempy += dy;
      }
    }
    if(dy > 0){
      if(bottomLeft || bottomRight){
        moveLeft();
        tempy = (currRow + 1) * tileMap.getTileSize() - height/2;
      }else{
        tempy += dy;
      }
    }

    calculateCorners(tox, y);
    if(dx < 0){
      if(topLeft || bottomLeft){
		moveUp();
        tempx = currCol * tileMap.getTileSize() + width / 2;
      }else{
        tempx += dx;
      }
    }
    if(dx > 0){
      if(topRight || bottomRight){
        moveDown();
        tempx = (currCol + 1) * tileMap.getTileSize() - width / 2;
      }else{
        tempx += dx;
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
	  z = ((leftTile + rightTile)/2)-1;
      a = ((topTile + bottomTile)/2)-1;

      topLeft = tileMap.getTile(topTile, leftTile) == 0;
      topRight = tileMap.getTile(topTile, rightTile) == 0;
      bottomLeft = tileMap.getTile(bottomTile, leftTile) == 0;
      bottomRight = tileMap.getTile(bottomTile, rightTile) == 0;
	}
  public void right(){

  }

  public void draw(Graphics2D g, String string){
	tx = tileMap.getx();
    ty = tileMap.getx();
    tilex = (int)(x-75);
    tiley = (int)(y-75);

    //System.out.println("x: " + tilex);
    //System.out.println("y: " + tiley);

    g.drawImage(new ImageIcon(ghostName).getImage(), (int) (tx + x - width/2), (int) (ty + y - width/2), width, height, null);
    ////System.out.println("x: " + (int) (tx + x - width/2) + " y: " + (int) (ty + y - width/2));
  }

  public void moveUp(){
	if(imageBool)
	ghostName = "images/ghostSprite/upORANGE.png";

	dy -= moveSpeed;
	dy = -maxSpeed;
	dx = 0;
  }
  public void moveDown(){
	if(imageBool)
	ghostName = "images/ghostSprite/downORANGE.png";

	dy += moveSpeed;
    dy = maxSpeed;
    dx = 0;
  }
  public void moveRight(){
	if(imageBool)
	ghostName = "images/ghostSprite/rightORANGE.png";

	dx += moveSpeed;
    dx = maxSpeed;
    dy = 0;
  }
  public void moveLeft(){
	if(imageBool)
	ghostName = "images/ghostSprite/leftORANGE.png";

	dx -= moveSpeed;
    dx = -maxSpeed;
    dy = 0;
  }

  public static void makeVulnerable(){
	isVulnerable = true;
	imageBool = false;
	moveSpeed = 3.5;
	maxSpeed = 3.5;
	ghostName = "images/ghostSprite/vulnerable.png";
  }

  public static void makeActive(){
	isVulnerable = false;
	imageBool = true;
	moveSpeed = 5;
	maxSpeed = 5;
	ghostName = "images/ghostSprite/downORANGE.png";
  }

   public static void backToHome(){
	setx(646);
	sety(316);
  }

  public void randomMove(){
	if(l == 1 && r == 1 &&u == 1 && d == 1){
		n = rand.nextInt(4) + 1;
		if(n == 1){
			moveLeft();
		}else if (n == 2){
			moveRight();
		}else if (n == 3){
			moveUp();
		}else if (n == 4){
			moveDown();
		}
	}if(l == 1 && r == 1 && u == 1 && d != 1){
		n = rand.nextInt(3) + 1;
		if(n == 1){
			moveLeft();
		}else if (n == 2){
			moveRight();
		}else if (n == 3){
			moveUp();
		}
	}if(l == 1 && r == 1 && u != 1 && d == 1){
		n = rand.nextInt(3) + 1;
		if(n == 1){
			moveLeft();
		}else if (n == 2){
			moveRight();
		}else if (n == 3){
			moveDown();
		}
	}if(l == 1 && r != 1 && u == 1 && d == 1){
		n = rand.nextInt(3) + 1;
		if(n == 1){
			moveLeft();
		}else if (n == 2){
			moveDown();
		}else if (n == 3){
			moveUp();
		}
	}if(l == 1 && r == 1 &&u != 1 && d != 1){
		n = rand.nextInt(2) + 1;
		if(n == 1){
			moveLeft();
		}else if (n == 2){
			moveRight();
		}
	}if(l == 1 && r != 1 &&u == 1 && d != 1){
		n = rand.nextInt(2) + 1;
		if(n == 1){
			moveLeft();
		}else if (n == 2){
			moveUp();
		}
	}if(l == 1 && r != 1 &&u != 1 && d == 1){
		n = rand.nextInt(2) + 1;
		if(n == 1){
			moveLeft();
		}else if (n == 2){
			moveDown();
		}
	}if(l == 1 && r != 1 &&u != 1 && d != 1){
		moveLeft();
	}if(l != 1 && r == 1 &&u == 1 && d == 1){
		n = rand.nextInt(3) + 1;
		if(n == 1){
			moveDown();
		}else if (n == 2){
			moveRight();
		}else if (n == 3){
			moveUp();
		}
	}if(l != 1 && r == 1 &&u == 1 && d != 1){
		n = rand.nextInt(2) + 1;
		if(n == 1){
			moveUp();
		}else if (n == 2){
			moveRight();
		}
	}if(l != 1 && r == 1 &&u != 1 && d == 1){
		n = rand.nextInt(2) + 1;
		if(n == 1){
			moveDown();
		}else if (n == 2){
			moveRight();
		}
	}if(l != 1 && r == 1 &&u != 1 && d != 1){
		moveRight();
	}if(l != 1 && r != 1 &&u == 1 && d == 1){
		n = rand.nextInt(2) + 1;
		if(n == 1){
			moveDown();
		}else if (n == 2){
			moveUp();
		}
	}if(l != 1 && r != 1 &&u == 1 && d != 1){
		moveUp();
	}if(l != 1 && r != 1 &&u != 1 && d == 1){
		moveDown();
	}

	if(l == 2 && r == 2 && u == 2 && d == 2){
		n = rand.nextInt(4) + 1;
		if(n == 1){
			moveLeft();
		}else if (n == 2){
			moveRight();
		}else if (n == 3){
			moveUp();
		}else if (n == 4){
			moveDown();
		}
	}if(l == 2 && r == 2 && u == 2 && d != 2){
		n = rand.nextInt(3) + 1;
		if(n == 1){
			moveLeft();
		}else if (n == 2){
			moveRight();
		}else if (n == 3){
			moveUp();
		}
	}if(l == 2 && r == 2 && u != 2 && d == 2){
		n = rand.nextInt(3) + 1;
		if(n == 1){
			moveLeft();
		}else if (n == 2){
			moveRight();
		}else if (n == 3){
			moveDown();
		}
	}if(l == 2 && r != 2 && u == 2 && d == 2){
		n = rand.nextInt(3) + 1;
		if(n == 1){
			moveLeft();
		}else if (n == 2){
			moveDown();
		}else if (n == 3){
			moveUp();
		}
	}if(l == 2 && r == 2 &&u != 2 && d != 2){
		n = rand.nextInt(2) + 1;
		if(n == 1){
			moveLeft();
		}else if (n == 2){
			moveRight();
		}
	}if(l == 2 && r != 2 &&u == 2 && d != 2){
		n = rand.nextInt(2) + 1;
		if(n == 1){
			moveLeft();
		}else if (n == 2){
			moveUp();
		}
	}if(l == 2 && r != 2 &&u != 2 && d == 2){
		n = rand.nextInt(2) + 1;
		if(n == 1){
			moveLeft();
		}else if (n == 2){
			moveDown();
		}
	}if(l == 2 && r != 2 &&u != 2 && d != 2){
		moveLeft();
	}if(l != 2 && r == 2 &&u == 2 && d == 2){
		n = rand.nextInt(3) + 1;
		if(n == 1){
			moveDown();
		}else if (n == 2){
			moveRight();
		}else if (n == 3){
			moveUp();
		}
	}if(l != 2 && r == 2 &&u == 2 && d != 2){
		n = rand.nextInt(2) + 1;
		if(n == 1){
			moveUp();
		}else if (n == 2){
			moveRight();
		}
	}if(l != 2 && r == 2 &&u != 2 && d == 2){
		n = rand.nextInt(2) + 1;
		if(n == 1){
			moveDown();
		}else if (n == 2){
			moveRight();
		}
	}if(l != 2 && r == 2 &&u != 2 && d != 2){
		moveRight();
	}if(l != 2 && r != 2 &&u == 2 && d == 2){
		n = rand.nextInt(2) + 1;
		if(n == 1){
			moveDown();
		}else if (n == 2){
			moveUp();
		}
	}if(l != 2 && r != 2 &&u == 2 && d != 2){
		moveUp();
	}if(l != 2 && r != 2 &&u != 2 && d == 2){
		moveDown();
	}
  }
}

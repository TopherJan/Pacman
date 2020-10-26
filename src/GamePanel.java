import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;

public class GamePanel extends JPanel implements Runnable, KeyListener {
  public static final int WIDTH = 1366;
  public static final int HEIGHT = 768;

  public static Thread thread;
  public static boolean running;
  private BufferedImage image;
  private Graphics2D g;
  private int FPS = 30;
  private int targetTime = 1000/FPS;

  private TileMap tileMap;
  public static Player player;
  public static Player2 player2;

  private boolean up;
  private boolean down;
  private boolean left;
  private boolean right = true;
  private boolean up2;
  private boolean down2;
  private boolean left2;
  private boolean right2 = true;

  public static Ghost ghost;
  public static Ghost1 ghost2;
  public static Ghost2 ghost3;
  public static Ghost3 ghost4;

  private JFrame frame;
  private int identifier;
  public static String pacName;
  protected static SoundClip moveClip = new SoundClip("audio/pacchomp2.wav", 0);
  protected static SoundClip pacDieClip = new SoundClip("audio/pacdies.wav", 1);
  
  public GamePanel(){
    super();
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    setFocusable(true);
    requestFocus();
	addKeyListener(this);
  }
  
  public GamePanel(JFrame frame, int identifier){
    super();
	this.frame = frame;
	this.identifier = identifier;
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    frame.setFocusable(true);
    requestFocus();
	frame.addKeyListener(this);
  }

  public void addNotify(){
    super.addNotify();
    if(thread == null) {
      thread = new Thread(this);
      thread.start();
	  moveClip.start();
    }
	
  }

  public void run(){
    init();

    long startTime;
    long urdTime;
    long waitTime;

    while(running) {
      startTime = System.nanoTime();
      urdTime = (System.nanoTime() - startTime)/1000000;
      waitTime = targetTime - urdTime;

      try{
        Thread.sleep(waitTime);
      }catch(Exception e){

      }
        update();
        render();
        draw();
    }
  }

  private void init(){
    running = true;
    image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    g = (Graphics2D) image.getGraphics();
    tileMap = new TileMap("testmap.txt", 30, this);
    
	if(identifier == 1){
		player = new Player(tileMap);
		player.setx(75);
		player.sety(500);
    }
	if(identifier == 2){
		player = new Player(tileMap);
		player2 = new Player2(tileMap);
		player.setx(75);
		player.sety(500);
		player2.setx(75);
		player2.sety(500);
	}
    ghost = new Ghost(tileMap);
    ghost2 = new Ghost1(tileMap);
    ghost3 = new Ghost2(tileMap);
    ghost4 = new Ghost3(tileMap);

    ghost.setx(676);
    ghost.sety(316);
    ghost2.setx(646);
    ghost2.sety(316);
    ghost3.setx(616);
    ghost3.sety(316);
    ghost4.setx(706);
    ghost4.sety(316);

  }

  private void update(){
    ghost.update();
    ghost2.update();
    ghost3.update();
    ghost4.update();

    tileMap.update();
	if(identifier == 1)
    player.update();
    
	if(identifier == 2){
	  player.update();
	  player2.update();
	}
  }

  private void render(){
    tileMap.draw(g);
  
  if(identifier == 1){
	if(up){
		pacName = "images/pacmanSprite/pakUp.gif";
		player.draw(g, pacName);
	}
	if(down){
		pacName = "images/pacmanSprite/pakDown.gif";
		player.draw(g, pacName);
	}
	if(left){
		pacName = "images/pacmanSprite/pakLeft.gif";
		player.draw(g, pacName);
	}
	if(right){
		pacName = "images/pacmanSprite/pakRight.gif";
		player.draw(g, pacName);
	}
  }
  
  
  if(identifier == 2){
	if(up){
		pacName = "images/pacmanSprite/pakUp.gif";
		player.draw(g, pacName);
	}
	if(down){
		pacName = "images/pacmanSprite/pakDown.gif";
		player.draw(g, pacName);
	}
	if(left){
		pacName = "images/pacmanSprite/pakLeft.gif";
		player.draw(g, pacName);
	}
	if(right){
		pacName = "images/pacmanSprite/pakRight.gif";
		player.draw(g, pacName);
	}
	 
	if(up2){
		pacName = "images/pacmanSprite/ganernUp.gif";
		player2.draw(g, pacName);
	}
	if(down2){
		pacName = "images/pacmanSprite/ganernDown.gif";
		player2.draw(g, pacName);
	}
	if(left2){
		pacName = "images/pacmanSprite/ganernLeft.gif";
		player2.draw(g, pacName);
	}
	if(right2){
		pacName = "images/pacmanSprite/ganernRight.gif";
		player2.draw(g, pacName);
	}
  }
	ghost.draw(g, "images/ghostSprite/downRED.png");
	ghost2.draw(g, "images/ghostSprite/downBLUE.png");
	ghost3.draw(g, "images/ghostSprite/downORANGE.png");
	ghost4.draw(g, "images/ghostSprite/downGREEN.png");
  }
  
  private void draw(){
    Graphics g2 = getGraphics();
    g2.drawImage(image, 0,0,null);
    g2.dispose();
  }
  
  public void keyTyped(KeyEvent key){}
  public void keyPressed(KeyEvent key){
    int code = key.getKeyCode();

    if(code == KeyEvent.VK_UP){
      player.setUp(true);
      setPakMovement(true, false, false, false);
    }
    if(code == KeyEvent.VK_LEFT){
        player.setLeft(true);
        setPakMovement(false, false, true, false);
    }
    if(code == KeyEvent.VK_RIGHT){
      player.setRight(true);
      setPakMovement(false, false, false, true);
    }
    if(code == KeyEvent.VK_DOWN){
      player.setDown(true);
      setPakMovement(false, true, false, false);
    }


    if(code == KeyEvent.VK_W){
      player2.setUp(true);
      setGanernmanMovement(true, false, false, false);
    }
    if(code == KeyEvent.VK_A){
      player2.setLeft(true);
      setGanernmanMovement(false, false, true, false);
    }
    if(code == KeyEvent.VK_D){
      player2.setRight(true);
      setGanernmanMovement(false, false, false, true);
    }
    if(code == KeyEvent.VK_S){
      player2.setDown(true);
      setGanernmanMovement(false, true, false, false);
    }

  }
  public void keyReleased(KeyEvent key){
    int code = key.getKeyCode();

    if(code == KeyEvent.VK_LEFT){
      player.setLeft(false);
    }
    if(code == KeyEvent.VK_RIGHT){
      player.setRight(false);
    }
    if(code == KeyEvent.VK_UP){
      player.setUp(false);
    }
    if(code == KeyEvent.VK_DOWN){
      player.setDown(false);
    }

    if(code == KeyEvent.VK_A){
      player2.setLeft(false);
    }
    if(code == KeyEvent.VK_D){
      player2.setRight(false);
    }
    if(code == KeyEvent.VK_W){
      player2.setUp(false);
    }
    if(code == KeyEvent.VK_S){
      player2.setDown(false);
    }
  }
  private void setPakMovement(boolean PAK_UP, boolean PAK_DOWN, boolean PAK_LEFT, boolean PAK_RIGHT){
      this.up = PAK_UP;
      this.down = PAK_DOWN;
      this.left = PAK_LEFT;
      this.right = PAK_RIGHT;
    }
    private void setGanernmanMovement(boolean PAK_UP, boolean PAK_DOWN, boolean PAK_LEFT, boolean PAK_RIGHT){
        this.up2 = PAK_UP;
        this.down2 = PAK_DOWN;
        this.left2 = PAK_LEFT;
        this.right2 = PAK_RIGHT;
    }
	
	public static void setPlayer(int counter){
		pacName = "images/pacmanSprite/deadPacman2.gif";
		pacDieClip.start();
		try {
			moveClip.stop();
			thread.sleep(1000);
		} catch (InterruptedException ex) {
			thread.start();
		}
		
		player.tempx = 75;
		player.tempy = 500;
		player.lives -= 1;
		Ghost.backToHome();
		Ghost1.backToHome();
		Ghost2.backToHome();
		Ghost3.backToHome();
		Ghost.makeActive();
		Ghost1.makeActive();
		Ghost2.makeActive();
		Ghost3.makeActive();
		moveClip.start();
		
		if(player.lives == 0){
			running = false;
			moveClip.stop();
			setHighScore(counter);
		}
		
		if(Player.counter == 342){
			running = false;
			moveClip.stop();
			setHighScore(counter);
		}
	}
	
	public static void setPlayer2(int counter){
		pacName = "images/pacmanSprite/deadPacman2.gif";
		pacDieClip.start();
		
		try{
			thread.sleep(1000);
			moveClip.stop();	
		} catch (InterruptedException ex) {
			thread.start();
		}
		
		player2.tempx = 75;
		player2.tempy = 500;
		player2.lives -= 1;
		Ghost.backToHome();
		Ghost1.backToHome();
		Ghost2.backToHome();
		Ghost3.backToHome();
		Ghost.makeActive();
		Ghost1.makeActive();
		Ghost2.makeActive();
		Ghost3.makeActive();
		moveClip.start();
		
		if(player2.lives == 0){
			running = false;
			moveClip.stop();
			
			if(counter > Player.counter){	
				setHighScore(counter);
			}else{
				setHighScore(Player.counter);
			}
		}
		
		if(Player.counter + Player2.counter == 342){
			running = false;
			moveClip.stop();
			
			if(counter > Player.counter){	
				setHighScore(counter);
			}else{
				setHighScore(Player.counter);
			}
		}
	}
	
	public static void setHighScore(int counter){	
		try{
			BufferedReader br = new BufferedReader(new FileReader("highscore.txt"));
			int highScore = Integer.parseInt(br.readLine());
			
			br.close();
			
			if(counter > highScore){
				//FileWriter scoreWriter = new FileWriter("highscore.txt");
				FileWriter fileOut = new FileWriter("highscore.txt");
				fileOut.write(Integer.toString(counter));
				fileOut.close();
			}
		}catch(Exception e){}		
	}
}
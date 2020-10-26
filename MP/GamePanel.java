import javax.swing.JPanel;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.*;

public class GamePanel extends JPanel implements Runnable, KeyListener {
  protected static final int WIDTH = 1366;
  protected static final int HEIGHT = 768;

  protected volatile Thread thread;
  protected static boolean running;
  private boolean displayWin = false;
  private BufferedImage image;
  private Graphics2D g;
  private int FPS = 30;
  private int targetTime = 1000/FPS;

  private TileMap tileMap;
  protected static Player player;
  protected static Player2 player2;

  private boolean up;
  private boolean down;
  private boolean left;
  private boolean right = true;
  private boolean up2;
  private boolean down2;
  private boolean left2;
  private boolean right2 = true;

  protected static Ghost ghost;
  protected static Ghost1 ghost2;
  protected static Ghost2 ghost3;
  protected static Ghost3 ghost4;

  private JFrame frame;
  protected static int identifier;
  protected static String pacName;
  protected static String ganernName;
  protected static SoundClip pacDieClip = new SoundClip("audio/pacdies.wav", 1);
  protected Window window = new Window(null);
  protected Window window2 = new Window(null);

  protected Popup popup;
  protected PopupFactory factory = PopupFactory.getSharedInstance();
  private volatile Frame frames = new Frame();
  private JPanel menuPanel = new JPanel();
  private JLabel pakWins = new JLabel(new ImageIcon("images/menu/pakWins.jpg"));
  private JLabel ganernWins = new JLabel(new ImageIcon("images/menu/ganernWins.jpg"));
  private JLabel loseLabel = new JLabel(new ImageIcon("images/menu/lose.jpg"));
  private JLabel winLabel = new JLabel(new ImageIcon("images/menu/won.jpg"));
  private JButton resumeButton = new JButton("RESUME");
  private JButton restartButton = new JButton("RESTART");
  private JButton backToMenuButton = new JButton("MENU");
  private int alternate = 0;
  public GamePanel(){
    super();
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    setFocusable(true);
    requestFocusInWindow();
	addKeyListener(this);
  }

  public GamePanel(JFrame frame, int identifier){
    super();
	this.frame = frame;
	this.identifier = identifier;
    setPreferredSize(new Dimension(WIDTH, HEIGHT));
    frame.setFocusable(true);
	frame.requestFocusInWindow();
	frame.addKeyListener(this);
  }

  public void addNotify(){
    super.addNotify();
    if(thread == null) {
      thread = new Thread(this);
      thread.start();
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
		player.setx(1275);
		player.sety(375);
    }
	if(identifier == 2){
		player = new Player(tileMap);
		player2 = new Player2(tileMap);
		player.setx(1275);
		player.sety(375);
		player2.setx(75);
		player2.sety(375);
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

	if(identifier == 1 && player.lives != 0){
		player.update();
	}	
	else if(identifier == 2){
	  if(player.lives != 0)
		player.update();
	  if(player2.lives != 0)
		player2.update();
	}
  }

  private void render(){
    tileMap.draw(g);
  if(identifier == 1 && Player.lives >= 1){
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
	if(Player.lives >= 1){
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

	if(Player2.lives >= 1){
		if(up2){
			ganernName = "images/pacmanSprite/ganernUp.gif";
			player2.draw(g, ganernName);
		}
		if(down2){
			ganernName = "images/pacmanSprite/ganernDown.gif";
			player2.draw(g, ganernName);
		}
		if(left2){
			ganernName = "images/pacmanSprite/ganernLeft.gif";
			player2.draw(g, ganernName);
		}
		if(right2){
			ganernName = "images/pacmanSprite/ganernRight.gif";
			player2.draw(g, ganernName);
		}
	}
  }
	ghost.draw(g, "images/ghostSprite/downRED.png");
	ghost2.draw(g, "images/ghostSprite/downBLUE.png");
	ghost3.draw(g, "images/ghostSprite/downORANGE.png");
	ghost4.draw(g, "images/ghostSprite/downGREEN.png");

	if((Player.lives == 0 || Player2.lives == 0) && identifier == 2){
		displayWin = true;
		if(Player2.lives  == 0){
			popup = factory.getPopup(null, pakWins, 0, 0);
			popup.show();
		}else{
			popup = factory.getPopup(null, ganernWins, 0, 0);
			popup.show();
		}
		thread.suspend();
	}

	if(player.lives == 0 && identifier == 1){
		displayWin = true;
		popup = factory.getPopup(null, loseLabel, 0, 0);
		popup.show();
		thread.suspend();
	}

	if(player.counter == 342 && identifier == 1){
		displayWin = true;
		popup = factory.getPopup(null, winLabel, 0, 0);
		popup.show();
		thread.suspend();
	}

	if(Player.counter + Player2.counter == 342 && identifier == 2){
		displayWin = true;
		if(Player.score > Player2.score){
			popup = factory.getPopup(null, pakWins, 0, 0);
			popup.show();
		}else{
			popup = factory.getPopup(null, ganernWins, 0, 0);
			popup.show();
		}
		thread.suspend();
	}
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

	if(identifier == 2){
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

	if(code == KeyEvent.VK_ESCAPE){
		thread.suspend();

		popMenu();
	}
	
	if(displayWin == true){
		if(code == KeyEvent.VK_SPACE){
			if(player.lives == 0 && identifier == 1){
				popup.hide();
				resumeButton.setEnabled(false);
			}

			else if(player.counter == 342 && identifier == 1){
				popup.hide();
				resumeButton.setEnabled(false);
			}

			if(Player.counter + Player2.counter == 342 && identifier == 2){
				popup.hide();
				resumeButton.setEnabled(false);
			}

			if((Player.lives == 0 || Player2.lives == 0) && identifier == 2){
				popup.hide();
				resumeButton.setEnabled(false);
			}
			
			resumeButton.setEnabled(false);
			window.setVisible(true);
			window2.setVisible(true);
			popMenu();
			//thread.suspend();
		}
	}

  }

  public void keyReleased(KeyEvent key){
    int code = key.getKeyCode();

    if(code == KeyEvent.VK_LEFT){
      player.setLeft(false);
      player.setC(0);
    }
    if(code == KeyEvent.VK_RIGHT){
      player.setRight(false);
      player.setC(0);
    }
    if(code == KeyEvent.VK_UP){
      player.setUp(false);
      player.setC(0);
    }
    if(code == KeyEvent.VK_DOWN){
      player.setDown(false);
      player.setC(0);
    }
	
	if(identifier == 2){
		if(code == KeyEvent.VK_A){
		  player2.setLeft(false);
		  player2.setC(0);
		}
		if(code == KeyEvent.VK_D){
		  player2.setRight(false);
		  player2.setC(0);
		}
		if(code == KeyEvent.VK_W){
		  player2.setUp(false);
		  player2.setC(0);
		}
		if(code == KeyEvent.VK_S){
		  player2.setDown(false);
		  player2.setC(0);
		}
	}

	if(displayWin == true){
		if(code == KeyEvent.VK_SPACE){
		  displayWin = false;
		}
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

	public void setPlayer(int counter){
		pacName = "images/pacmanSprite/deadPacman2.gif";
		pacDieClip.start();

		try {
			thread.sleep(1000);
		} catch (InterruptedException ex) {
			thread.start();
		}

		player.tempx = 1275;
		player.tempy = 375;
		player.lives -= 1;
		ghost.backToHome();
		ghost2.backToHome();
		ghost3.backToHome();
		ghost4.backToHome();
		ghost.makeActive();
		ghost2.makeActive();
		ghost3.makeActive();
		ghost4.makeActive();


	}

	public void setPlayer2(int counter){
		ganernName = "images/pacmanSprite/deadPacman2.gif";
		pacDieClip.start();

		try{
			thread.sleep(1000);

		} catch (InterruptedException ex) {
			thread.start();
		}

		player2.tempx = 75;
		player2.tempy = 375;
		player2.lives -= 1;

		ghost.backToHome();
		ghost2.backToHome();
		ghost3.backToHome();
		ghost4.backToHome();

		ghost.makeActive();
		ghost2.makeActive();
		ghost3.makeActive();
		ghost4.makeActive();

	}

	public void restartGame(){
		frame.repaint();
		frame.revalidate();
		tileMap = new TileMap("testmap.txt", 30, GamePanel.this);
		
		player.x = 1275;
		player.tilex = 615;
		player.y = 375;
		player.tiley = 255;
		player.tempx = 1275;
		player.tempy = 375;
		player.lives = 3;
		player.count = 1;
		player.counter = 0;
		player.score = 0;

		if(identifier == 2){
			player2.x = 75;
			player2.tilex = 615;
			player2.y = 375;
			player2.tiley = 255;
			player2.tempx = 75;
			player2.tempy = 375;
			player2.lives = 3;
			player2.count = 1;
			player2.counter = 0;
			player2.score = 0;
		}

		ghost.backToHome();
		ghost2.backToHome();
		ghost3.backToHome();
		ghost4.backToHome();

		ghost.makeActive();
		ghost2.makeActive();
		ghost3.makeActive();
		ghost4.makeActive();
	}

	public void removePanel(){
		thread = null;

		restartGame();
	}
	
	public void popMenu(){
		window2.setVisible(true);
		window2.setSize(1366,768);
		window2.setBackground(new Color(0,0,0,210));

		window.setVisible(true);
		window.setSize(800,100);
		window.setLocation(300,678);

		menuPanel.setBounds(600,600, 100,100);
		menuPanel.setPreferredSize(new Dimension(300,300));

		resumeButton.setFont(new Font("Impact", Font.PLAIN, 30));
		restartButton.setFont(new Font("Impact", Font.PLAIN, 30));
		backToMenuButton.setFont(new Font("Impact", Font.PLAIN, 30));

		resumeButton.setForeground(Color.DARK_GRAY);
		restartButton.setForeground(Color.DARK_GRAY);
		backToMenuButton.setForeground(Color.DARK_GRAY);

		resumeButton.setPreferredSize(new Dimension(150,70));
		restartButton.setPreferredSize(new Dimension(150,70));
		backToMenuButton.setPreferredSize(new Dimension(150,70));

		window.add(menuPanel);
		menuPanel.add(restartButton);
		menuPanel.add(resumeButton);
		menuPanel.add(backToMenuButton);

		resumeButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent event){
			thread.resume();
			window.setVisible(false);
			window2.setVisible(false);
		}
		});

		restartButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent event){
			resumeButton.setEnabled(true);
			window.setVisible(false);
			window2.setVisible(false);
			//tileMap = new TileMap("testmap.txt", 30, GamePanel.this);
			restartGame();
			thread.resume();
		}
		});

		backToMenuButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent event){
			try{
				removePanel();
				window.setVisible(false);
				window2.setVisible(false);
				frame.dispose();
				frames.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frames.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frames.setVisible(true);
				frames.displayMenu();
				frames.repaint();
				frames.revalidate();
			}catch(IllegalComponentStateException ex){
				ex.printStackTrace();
			}
		}
		});

	  frames.setUndecorated(true);
	  alternate++;
	}
}

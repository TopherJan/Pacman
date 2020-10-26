import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Font;
import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.GradientPaint;
import java.awt.BasicStroke;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.IOException;
import javax.swing.Icon;
import java.awt.Component;
import java.awt.BorderLayout;
import sun.audio.*;
import java.io.*;
import javax.sound.sampled.*;
import java.awt.*;

public class Frame extends JFrame {
  private int identifier;
  public static boolean checker = true;

  private ChangeBackgroundPanel background1;
  private ChangeBackgroundPanel background2;
  private ChangeBackgroundPanel background3;
  private ChangeBackgroundPanel background4;
  private ChangeBackgroundPanel background5;
  private ChangeBackgroundPanel background6;

  private JLabel redGhost;
  private JLabel blueGhost;
  private JLabel greenGhost;
  private JLabel orangeGhost;
  private JLabel redRect;
  private JLabel blueRect;
  private JLabel greenRect;
  private JLabel orangeRect;

  private Toolkit toolkit = Toolkit.getDefaultToolkit();

  private JLabel maze1= new JLabel(new ImageIcon("images/maze1.png"));
  private JLabel maze2= new JLabel(new ImageIcon("images/maze2.png"));
  private JLabel maze3= new JLabel(new ImageIcon("images/maze3.png"));
  private JLabel maze4= new JLabel(new ImageIcon("images/maze4.png"));

  private JTextField player1TextField;
  private JTextField player2TextField;

  private JPanel subPanel;
  private JPanel pan;
  private JPanel mapPanel;

  private volatile JButton backButton;
  private Listener listener;

  protected SoundClip backstoryClip = new SoundClip("audio/backstoryaudio1.wav", 0);
  protected SoundClip menuClip = new SoundClip("audio/remix.wav", 0);
  protected SoundClip mapClip = new SoundClip("audio/remix.wav", 0);
  protected SoundClip clickClip = new SoundClip("audio/gs_eatghost.wav", 1);

  private LabelHandler labelHandler = new LabelHandler();

  private PacmanStart pacman;
  private GamePanel gamePan;
  
  private Image image;
  private Cursor c;
  
  public Frame() {
    super("PAKMAN VS. GANERNMAN");

    subPanel = new JPanel();
    subPanel.setLayout(new BorderLayout());
    add(subPanel);

	 if(checker == false){
	  requestFocus();
	  checker = true;
	  subPanel.removeAll();
	  subPanel.repaint();
	  subPanel.revalidate();
	  
	  background2 = new ChangeBackgroundPanel("images/backstory.gif");
	  background2.addCenterLabel("PRESS ENTER TO SKIP", "Early GameBoy", 40, Color.WHITE);
	  backstoryClip.start();
	  subPanel.add(background2, BorderLayout.CENTER);

	  listener = new Listener();
	  Timer timer = new Timer(1000, listener);
	  timer.start();
	}
	
    this.addKeyListener(new KeyAdapter(){
      public void keyPressed(KeyEvent event){
        if(checker == true){
          if(event.getKeyCode() == KeyEvent.VK_ENTER){
            listener.isTrue(false);
			backstoryClip.stop();
            //setFocusable(false);
            displayMenu(subPanel);
			menuClip.start();
          }
        }
      }
    });
  }

  public void displayCredits(){
	subPanel.removeAll();
	subPanel.repaint();
	subPanel.revalidate();

	background5 = new ChangeBackgroundPanel("images/credits.jpg");
	backToMenu(background5);
  }

  public void displayNewGame(){
	subPanel.removeAll();
	subPanel.repaint();
	subPanel.revalidate();

	background4 = new ChangeBackgroundPanel("images/newGame3.jpg");

	player1TextField = new JTextField(30);
	player2TextField = new JTextField(30);
	Font font = new Font("IMPACT", Font.BOLD,30);
	player1TextField.setForeground(Color.DARK_GRAY);
	player2TextField.setForeground(Color.DARK_GRAY);
	player1TextField.setHorizontalAlignment(JTextField.CENTER);
	player2TextField.setHorizontalAlignment(JTextField.CENTER);
	player1TextField.setFont(font);
	player2TextField.setFont(font);
	player1TextField.setBounds(170, 600, 409, 50);
	player2TextField.setBounds(780, 600, 409, 50);
	background4.add(player1TextField);
	background4.add(player2TextField);

	backToMenu(background4);
	continueMenu(background4);
	subPanel.add(background4);
  }

  public void backToMenu(ChangeBackgroundPanel background){
	backButton = new JButton(new ImageIcon("images/left.png"));
	backButton.setOpaque(false);
	backButton.setContentAreaFilled(false);
	backButton.setBorderPainted(false);
	backButton.setFocusPainted(false);
	backButton.setBounds(10, 690, 70, 70);

	backButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent event){
			clickClip.start();
			if(background == background5 || background == background6)
			displayMenu(subPanel);

			if(background == background4)
			displayMaps();
		}
	});

	background.add(backButton);
	subPanel.add(background);
  }

  public void continueMenu(ChangeBackgroundPanel background){
	JButton cont = new JButton(new ImageIcon("images/right.png"));
	cont.setOpaque(false);
	cont.setContentAreaFilled(false);
	cont.setBorderPainted(false);
	cont.setFocusPainted(false);
	cont.setBounds(1285, 690, 70, 70);

	cont.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent event){
			clickClip.start();
			if(background == background4 && identifier == 3)
			changeMap(3);

			if(background == background4 && identifier == 4)
			changeMap(4);
		}
	});

	background.add(cont);
	subPanel.add(background);
  }

  public void displayMenu(JPanel subPanels){
    subPanels.removeAll();
    subPanels.repaint();
    subPanels.revalidate();
    repaint();
    revalidate();

    setLayout(null);
    redRect = new JLabel(new ImageIcon("images/rect1.png"));
    redRect.setBounds(424,320,500,79);

    redGhost = new JLabel(new ImageIcon("images/ghostSprite/redghost.png"));
    redGhost.setBounds(251, 253, 146,151);

    blueRect = new JLabel(new ImageIcon("images/blue.png"));
    blueRect.setBounds(356,415,610,79);

    blueGhost = new JLabel(new ImageIcon("images/ghostSprite/blueghost.png"));
    blueGhost.setBounds(931, 353, 146,151);

    greenRect = new JLabel(new ImageIcon("images/greenrect1.png"));
    greenRect.setBounds(375,518,550,70);

    greenGhost = new JLabel(new ImageIcon("images/ghostSprite/greenghost.png"));
    greenGhost.setBounds(251, 448, 146,151);

    orangeRect = new JLabel(new ImageIcon("images/orangerect1.png"));
    orangeRect.setBounds(272,613,772,80);

    orangeGhost = new JLabel(new ImageIcon("images/ghostSprite/orangeghost.png"));
    orangeGhost.setBounds(934, 553, 146,151);

    background3 = new ChangeBackgroundPanel("images/pesteiroy1.png");

    background3.add(redGhost);
    background3.add(blueGhost);
    background3.add(greenGhost);
    background3.add(orangeGhost);

    background3.add(redRect);
    background3.add(blueRect);
    background3.add(greenRect);
    background3.add(orangeRect);

    redGhost.addMouseListener(labelHandler);
    redRect.addMouseListener(labelHandler);
    blueGhost.addMouseListener(labelHandler);
    blueRect.addMouseListener(labelHandler);
    greenGhost.addMouseListener(labelHandler);
    greenRect.addMouseListener(labelHandler);
    orangeGhost.addMouseListener(labelHandler);
    orangeRect.addMouseListener(labelHandler);

    subPanels.add(background3);

	image = toolkit.getImage("images/pacmanSprite/rightSemiopen.png");
	c = toolkit.createCustomCursor(image , new Point(subPanels.getX(), subPanel.getY()), "img");
    subPanels.setCursor(c);

  }

  public void displayMaps(){
	subPanel.removeAll();
	subPanel.repaint();
	subPanel.revalidate();
	repaint();
	revalidate();

    pan = new MyRectangleJPanel();
    pan.setPreferredSize(new Dimension(500,500));
	pan.setBackground(new Color(0,0,0,0));

	maze1.setIcon(new ImageIcon("images/maze/1player.jpg"));
	maze1.setBounds(180, 150, 500, 315);

	maze2.setIcon(new ImageIcon("images/maze/2players.jpg"));
	maze2.setBounds(670, 150, 500, 315);

	maze3.setIcon(new ImageIcon("images/maze/invisible.jpg"));
	maze3.setBounds(180, 420, 500, 315);

	maze4.setIcon(new ImageIcon("images/maze/combat.jpg"));
	maze4.setBounds(670, 420, 500, 315);

	background6 = new ChangeBackgroundPanel("images/choosemode.jpg");

	maze1.addMouseListener(labelHandler);
	maze2.addMouseListener(labelHandler);
	maze3.addMouseListener(labelHandler);
	maze4.addMouseListener(labelHandler);

	background6.add(maze1);
	background6.add(maze2);
	background6.add(maze3);
	background6.add(maze4);
	subPanel.add(background6);
	backToMenu(background6);
  }

  public void changeMap(int identifier){
	this.identifier = identifier;
	subPanel.removeAll();
	subPanel.repaint();
	subPanel.revalidate();
	
	image = toolkit.getImage("hideCursor.png");
    c = toolkit.createCustomCursor(image , new Point(subPanel.getX(), subPanel.getY()), "img");
	
	if(identifier == 1){
		gamePan = new GamePanel(this, 1);
		subPanel.add(gamePan);
	}
	if(identifier == 2){
		subPanel.add(new GamePanel(this, 2));
	}
	subPanel.setCursor(c);
  }

  private class Listener implements ActionListener {
	private JPanel panel;
	private int count = 0;
	private boolean wew = true;

	public void actionPerformed(ActionEvent event){
		count++;

		if(count == 40 && wew == true){
		  backstoryClip.stop();
		  displayMenu(subPanel);
		  wew = false;
		}
	}

	public void isTrue(boolean wew){
		this.wew = wew;
	}
  }

  private class LabelHandler extends MouseAdapter{
    public void mouseEntered(MouseEvent event){

      if(event.getSource() == redGhost || event.getSource() == redRect){
        redRect.setBounds(424,320,500,80);
        redRect.setIcon(new ImageIcon("images/rect2.png"));
        redGhost.setIcon(new ImageIcon("images/ghostSprite/deadghost.png"));
      }
      if(event.getSource() == blueGhost || event.getSource() == blueRect){
        blueRect.setBounds(356,415,610,79);
        blueRect.setIcon(new ImageIcon("images/bluerect2.png"));
        blueGhost.setIcon(new ImageIcon("images/ghostSprite/deadghost.png"));
      }
      if(event.getSource() == greenGhost || event.getSource() == greenRect){
        greenRect.setBounds(375,518,550,70);
        greenRect.setIcon(new ImageIcon("images/greenrect2.png"));
        greenGhost.setIcon(new ImageIcon("images/ghostSprite/deadghost.png"));
      }
      if(event.getSource() == orangeGhost || event.getSource() == orangeRect){
        orangeRect.setBounds(272,613,772,80);
        orangeRect.setIcon(new ImageIcon("images/orangerect2.png"));
        orangeGhost.setIcon(new ImageIcon("images/ghostSprite/deadghost.png"));
        orangeGhost.setBounds(934, 553, 146,151);
      }


	  if(event.getSource() == maze1){
		pan.setBounds(180, 150,500,500);
		background6.add(pan);
	  }
	  if(event.getSource() == maze2){
		pan.setBounds(670, 150,500,500);
		background6.add(pan);
	  }
	  if(event.getSource() == maze3){
		pan.setBounds(180, 420,500,500);
		background6.add(pan);
	  }
	  if(event.getSource() == maze4){
		pan.setBounds(670, 420,500,500);
		background6.add(pan);
	  }
      repaint();
      revalidate();
    }

    public void mouseExited(MouseEvent event){
      if(event.getSource() == redGhost || event.getSource() == redRect){
        redRect.setIcon(new ImageIcon("images/rect.png"));
        redGhost.setIcon(new ImageIcon("images/ghostSprite/redghost"));
      }
      if(event.getSource() == blueGhost || event.getSource() == blueRect){
        blueRect.setIcon(new ImageIcon("images/blue.png"));
        blueGhost.setIcon(new ImageIcon("images/ghostSprite/redghost"));
      }
      if(event.getSource() == greenGhost || event.getSource() == greenRect){
        greenRect.setIcon(new ImageIcon("images/greenrect1.png"));
        greenGhost.setIcon(new ImageIcon("images/ghostSprite/greenghost"));
      }
      if(event.getSource() == orangeGhost || event.getSource() == orangeRect){
        orangeRect.setIcon(new ImageIcon("images/orangerect1.png"));
        orangeGhost.setIcon(new ImageIcon("images/ghostSprite/orangeghost"));
      }
	  if(event.getSource() == maze1){
		maze1.setIcon(new ImageIcon("images/maze/1player.jpg"));
		background6.remove(pan);
	  }
	  if(event.getSource() == maze2){
		maze2.setIcon(new ImageIcon("images/maze/2players.jpg"));
		background6.remove(pan);
	  }
	  if(event.getSource() == maze3){
		maze3.setIcon(new ImageIcon("images/maze/invisible.jpg"));
		background6.remove(pan);
	  }
	  if(event.getSource() == maze4){
		maze4.setIcon(new ImageIcon("images/maze/combat.jpg"));
		background6.remove(pan);
	  }

      repaint();
      revalidate();
    }

    public void mouseClicked(MouseEvent event){
      if(event.getSource() == redGhost || event.getSource() == redRect){
		clickClip.start();
		displayMaps();
      }
      if(event.getSource() == greenGhost || event.getSource() == greenRect){
		clickClip.start();
		displayCredits();
      }
      if(event.getSource() == orangeGhost || event.getSource() == orangeRect){
        clickClip.start();
		System.exit(0);
      }
	  if(event.getSource() == maze1){
		menuClip.stop();
		clickClip.start();
		changeMap(1);
	  }
	  if(event.getSource() == maze2){
		menuClip.stop();
		clickClip.start();
		changeMap(2);
	  }
	  if(event.getSource() == maze3){
		menuClip.stop();
		clickClip.start();
		displayNewGame();
		identifier = 3;
	  }

	  if(event.getSource() == maze4){
		menuClip.stop();
		clickClip.start();
		displayNewGame();
		identifier = 4;
	  }
    }
  }
  class MyRectangleJPanel extends JPanel {
    //@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
        g2.setStroke(new java.awt.BasicStroke(8)); // thickness of 3.0f

        g2.setColor(new Color(218,218,218));
        g2.drawRect(20,33, 460, 250);

    }
}
}

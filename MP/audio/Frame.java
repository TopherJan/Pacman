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
  protected static boolean checker = true;

  private ChangeBackgroundPanel background2;
  private ChangeBackgroundPanel background3;
  private ChangeBackgroundPanel background4;
  private ChangeBackgroundPanel background5;
  private ChangeBackgroundPanel background6;
  private ChangeBackgroundPanel background7;

  private JLabel redGhost;
  private JLabel blueGhost;
  private JLabel greenGhost;
  private JLabel orangeGhost;
  private JLabel redRect;
  private JLabel blueRect;
  private JLabel greenRect;
  private JLabel orangeRect;

  private Toolkit toolkit = Toolkit.getDefaultToolkit();
  private JLabel maze1= new JLabel(new ImageIcon("images/menu/onePlayer.png"));
  private JLabel maze2= new JLabel(new ImageIcon("images/menu/twoPlayer.png"));
  private JLabel player1= new JLabel(new ImageIcon("images/menu/onePlayer.png"));
  private JLabel player2= new JLabel(new ImageIcon("images/menu/twoPlayer.png"));

  protected JPanel subPanel;
  private JPanel pan;
  private JPanel mapPanel;

  private volatile JButton backButton;
  private Listener listener;

  protected SoundClip backstoryClip = new SoundClip("audio/backstoryaudio1.wav", 0);
  protected SoundClip menuClip = new SoundClip("audio/remix.wav", 0);
  protected SoundClip mapClip = new SoundClip("audio/remix.wav", 0);
  protected SoundClip clickClip = new SoundClip("audio/gs_eatghost.wav", 1);

  private LabelHandler labelHandler = new LabelHandler();
  protected GamePanel gamePan;
  private Image image;
  private Cursor c;
  private Window window;
  private JButton yesButton, noButton;

  public Frame() {
    super("PAKMAN VS. GANERNMAN");

    subPanel = new JPanel();
    subPanel.setLayout(new BorderLayout());
    add(subPanel);

	 if(checker == false){
	  this.setFocusable(true);
	  checker = true;
	  subPanel.removeAll();
	  subPanel.repaint();
	  subPanel.revalidate();

	  background2 = new ChangeBackgroundPanel("images/menu/backstory.gif");
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
            setFocusable(false);
            displayMenu();
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

	background5 = new ChangeBackgroundPanel("images/menu/credits.jpg");
	backToMenu(background5);
  }

  public void displayIns(String instructions){
	subPanel.removeAll();
	subPanel.repaint();
	subPanel.revalidate();

	background7 = new ChangeBackgroundPanel(instructions);
	backToMenu(background7);
  }

  public void backToMenu(ChangeBackgroundPanel background){
	ChangeBackgroundPanel backgrounds = background;
	backButton = new JButton(new ImageIcon("images/menu/left.png"));
	backButton.setOpaque(false);
	backButton.setContentAreaFilled(false);
	backButton.setBorderPainted(false);
	backButton.setFocusPainted(false);
	backButton.setBounds(10, 690, 70, 70);

	backButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent event){
			clickClip.start();
			if(backgrounds == background4 || backgrounds == background5 || backgrounds == background6)
			displayMenu();

			if(backgrounds == background7)
			displayInstructions();
		}
	});

	background.add(backButton);
	subPanel.add(background);
  }

  public void displayMenu(){
    subPanel.removeAll();
    subPanel.repaint();
    subPanel.revalidate();
    repaint();
    revalidate();

    setLayout(null);
    redRect = new JLabel(new ImageIcon("images/menu/rect1.png"));
    redRect.setBounds(424,320,500,79);

    redGhost = new JLabel(new ImageIcon("images/ghostSprite/redghost.png"));
    redGhost.setBounds(251, 253, 146,151);

    blueRect = new JLabel(new ImageIcon("images/menu/blue.png"));
    blueRect.setBounds(356,415,610,79);

    blueGhost = new JLabel(new ImageIcon("images/ghostSprite/blueghost.png"));
    blueGhost.setBounds(931, 353, 146,151);

    greenRect = new JLabel(new ImageIcon("images/menu/greenrect1.png"));
    greenRect.setBounds(375,518,550,70);

    greenGhost = new JLabel(new ImageIcon("images/ghostSprite/greenghost.png"));
    greenGhost.setBounds(251, 448, 146,151);

    orangeRect = new JLabel(new ImageIcon("images/menu/orangerect1.png"));
    orangeRect.setBounds(272,613,772,80);

    orangeGhost = new JLabel(new ImageIcon("images/ghostSprite/orangeghost.png"));
    orangeGhost.setBounds(934, 553, 146,151);

    background3 = new ChangeBackgroundPanel("images/menu/menu.png");

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

    subPanel.add(background3);
	image = toolkit.getImage("images/pacmanSprite/rightSemiopen.png");
	c = toolkit.createCustomCursor(image , new Point(subPanel.getX(), subPanel.getY()), "img");
    subPanel.setCursor(c);
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

	maze1.setIcon(new ImageIcon("images/menu/onePlayer.png"));
	maze1.setBounds(160, 250, 500, 315);

	maze2.setIcon(new ImageIcon("images/menu/twoPlayer.png"));
	maze2.setBounds(690, 250, 500, 315);

	background6 = new ChangeBackgroundPanel("images/menu/choosemode.jpg");

	maze1.addMouseListener(labelHandler);
	maze2.addMouseListener(labelHandler);

	background6.add(maze1);
	background6.add(maze2);
	subPanel.add(background6);
	backToMenu(background6);
  }

  public void displayInstructions(){
	subPanel.removeAll();
	subPanel.repaint();
	subPanel.revalidate();
	repaint();
	revalidate();

	pan = new MyRectangleJPanel();
	pan.setPreferredSize(new Dimension(500,500));
	pan.setBackground(new Color(0,0,0,0));

	player1.setIcon(new ImageIcon("images/menu/onePlayer.png"));
	player1.setBounds(160, 250, 500, 315);

	player2.setIcon(new ImageIcon("images/menu/twoPlayer.png"));
	player2.setBounds(690, 250, 500, 315);

	background4 = new ChangeBackgroundPanel("images/menu/instruct.jpg");

	player1.addMouseListener(labelHandler);
	player2.addMouseListener(labelHandler);

	background4.add(player1);
	background4.add(player2);
	subPanel.add(background4);
	backToMenu(background4);
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
		gamePan.thread = null;
		subPanel.add(gamePan);
	}
	else if(identifier == 2){
		gamePan = new GamePanel(this, 2);
		gamePan.thread = null;
		subPanel.add(gamePan);
	}

	repaint();
	revalidate();
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
		  menuClip.start();
		  displayMenu();
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
        redRect.setIcon(new ImageIcon("images/menu/rect2.png"));
        redGhost.setIcon(new ImageIcon("images/ghostSprite/deadghost.png"));
      }
      if(event.getSource() == blueGhost || event.getSource() == blueRect){
        blueRect.setBounds(356,415,610,79);
        blueRect.setIcon(new ImageIcon("images/menu/bluerect2.png"));
        blueGhost.setIcon(new ImageIcon("images/ghostSprite/deadghost.png"));
      }
      if(event.getSource() == greenGhost || event.getSource() == greenRect){
        greenRect.setBounds(375,518,550,70);
        greenRect.setIcon(new ImageIcon("images/menu/greenrect2.png"));
        greenGhost.setIcon(new ImageIcon("images/ghostSprite/deadghost.png"));
      }
      if(event.getSource() == orangeGhost || event.getSource() == orangeRect){
        orangeRect.setBounds(272,613,772,80);
        orangeRect.setIcon(new ImageIcon("images/menu/orangerect2.png"));
        orangeGhost.setIcon(new ImageIcon("images/ghostSprite/deadghost.png"));
        orangeGhost.setBounds(934, 553, 146,151);
      }

	  if(event.getSource() == maze1){
		maze1.setIcon(new ImageIcon("images/menu/onePlayer2.png"));
	  }
	  if(event.getSource() == maze2){
		maze2.setIcon(new ImageIcon("images/menu/twoPlayer2.png"));
	  }
	  if(event.getSource() == player1){
		player1.setIcon(new ImageIcon("images/menu/onePlayer2.png"));
	  }
	  if(event.getSource() == player2){
		player2.setIcon(new ImageIcon("images/menu/twoPlayer2.png"));
	  }

      repaint();
      revalidate();
    }

    public void mouseExited(MouseEvent event){
      if(event.getSource() == redGhost || event.getSource() == redRect){
        redRect.setIcon(new ImageIcon("images/menu/rect.png"));
        redGhost.setIcon(new ImageIcon("images/ghostSprite/redghost"));
      }
      if(event.getSource() == blueGhost || event.getSource() == blueRect){
        blueRect.setIcon(new ImageIcon("images/menu/blue.png"));
        blueGhost.setIcon(new ImageIcon("images/ghostSprite/redghost"));
      }
      if(event.getSource() == greenGhost || event.getSource() == greenRect){
        greenRect.setIcon(new ImageIcon("images/menu/greenrect1.png"));
        greenGhost.setIcon(new ImageIcon("images/ghostSprite/greenghost"));
      }
      if(event.getSource() == orangeGhost || event.getSource() == orangeRect){
        orangeRect.setIcon(new ImageIcon("images/menu/orangerect1.png"));
        orangeGhost.setIcon(new ImageIcon("images/ghostSprite/orangeghost"));
      }
	  if(event.getSource() == maze1){
		maze1.setIcon(new ImageIcon("images/menu/onePlayer.png"));
		background6.remove(pan);
	  }
	  if(event.getSource() == maze2){
		maze2.setIcon(new ImageIcon("images/menu/twoPlayer.png"));
		background6.remove(pan);
	  }
	  if(event.getSource() == player1){
		player1.setIcon(new ImageIcon("images/menu/onePlayer.png"));
		background4.remove(pan);
	  }
	  if(event.getSource() == player2){
		player2.setIcon(new ImageIcon("images/menu/twoPlayer.png"));
		background4.remove(pan);
	  }
      repaint();
      revalidate();
    }

    public void mouseClicked(MouseEvent event){
      if(event.getSource() == redGhost || event.getSource() == redRect){
		clickClip.start();
		displayMaps();
      }
	  if(event.getSource() == blueGhost || event.getSource() == blueRect){
		clickClip.start();
		displayInstructions();
      }
      if(event.getSource() == greenGhost || event.getSource() == greenRect){
		clickClip.start();
		displayCredits();
      }
      if(event.getSource() == orangeGhost || event.getSource() == orangeRect){
        clickClip.start();
		window = new Window(null);
		window.setVisible(true);
		window.setLocation(0,0);
		window.setSize(1366,768);

		JPanel p = new JPanel(null);
		p.setBackground(new Color(30,30,34));
		window.add(p);
		p.setBounds(0,0,1366,768);

		JLabel l = new JLabel("ARE YOU SURE?");
		l.setFont(new Font("Impact", Font.PLAIN, 150));
		l.setForeground(Color.WHITE);
		l.setBounds(230,200,1366,150);
		p.setFocusable(true);
		p.requestFocusInWindow();
		p.add(l);

		yesButton = new JButton("YES");
		yesButton.setFont(new Font("Impact", Font.PLAIN, 70));
		yesButton.setForeground(new Color(30,30,34));

		yesButton.setBounds(450,400,200,100);
		yesButton.setFocusable(true);
		p.add(yesButton);

		noButton = new JButton("NO");
		noButton.setFont(new Font("Impact", Font.PLAIN, 70));
		noButton.setForeground(new Color(30,30,34));
		noButton.setBounds(680,400,200,100);
		noButton.setFocusable(true);
		p.add(noButton);

		yesButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent event){

		System.exit(0);
		}
		});
		noButton.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent event){

		window.setVisible(false);
		}
		});
      }
	  if(event.getSource() == maze1){
		clickClip.start();
		changeMap(1);
	  }
	  if(event.getSource() == maze2){
		clickClip.start();
		changeMap(2);
	  }
	  if(event.getSource() == player1){
		clickClip.start();
		displayIns("images/menu/1p.png");
	  }
	  if(event.getSource() == player2){
		clickClip.start();
		displayIns("images/menu/2p.png");
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

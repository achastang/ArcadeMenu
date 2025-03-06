import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.Random;



public class GamePanelSnake extends JPanel implements ActionListener 
{
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
	static final int DELAY = 75;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 1;
	int applesEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	boolean paused = false;
	Timer timer;
	Random random;
	
	JFrame homeFrame;
	JFrame gameFrame;
	String currentUser;
	int HighScore;
	JFrame gameMenu;
	
	GamePanelSnake(JFrame homeFrame, JFrame gameFrame, String currentUser, int HighScore, JFrame gameMenu)
	{
		this.homeFrame = homeFrame;
		this.gameFrame = gameFrame;
		this.currentUser = currentUser;
		this.HighScore = HighScore;
		this.gameMenu = gameMenu;
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void startGame()
	{
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	public void resetGame()
	{
		bodyParts = 1;
		applesEaten = 0;
		direction = 'R';
		for (int i = 0; i < GAME_UNITS; i++) {
			x[i] = 0;
			y[i] = 0;
		}
		startGame();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g)
	{	
		if(!paused)
		{
			g.setColor(Color.WHITE);
			g.drawRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
			if(running)
			{
				g.setColor(Color.red);
				g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
				
				for(int i = 0;i<bodyParts; i++)
				{
					if(i==0)
					{
						g.setColor(Color.green);
						g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
					}
					else
					{
						g.setColor(new Color(45,180,0));
						g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
					}
				}
				g.setColor(Color.red);
				g.setFont(new Font("Press Start 2P",Font.PLAIN,25));
				g.drawString(""+applesEaten, 20, 40);
			}
			else
			{
				gameOver(g);
			}
		}
		else
		{
			g.setColor(Color.red);
			g.setFont(new Font("Press Start 2P",Font.PLAIN,45));
			g.drawString("Paused", 200, 275);
			g.drawString("Q to Quit", 175, 350);
			g.setFont(new Font("Press Start 2P",Font.PLAIN,32));
			g.drawString("B for Game Menu", 100, 400);
		}
	}
	
	public void newApple()
	{
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
	}
	
	public void move()
	{
		for(int i=bodyParts;i>0;i--)
		{
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direction)
		{
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
	}
	
	public void checkApple()
	{
		if((x[0] == appleX)&&(y[0] == appleY))
		{
			bodyParts++;
			applesEaten++;
			newApple();
		}
	}
	
	public void checkCollisions()
	{
		//check for snake on snake
		for(int i=bodyParts;i>0;i--)
		{
			if((x[0]==x[i])&&(y[0]==y[i]))
			{
				running = false;
				repaint();
			}
		}
		
		//check for snake on edge
		if(x[0]<0)
		{
			running = false;
			repaint();
		}
		
		if(x[0]>=SCREEN_WIDTH)
		{
			running = false;
			repaint();
		}
		
		if(y[0]<0)
		{
			running = false;
			repaint();
		}
		
		if(y[0]>=SCREEN_HEIGHT)
		{
			running = false;
			repaint();
		}
		
		if(!running)
		{
			if(applesEaten>HighScore)
			{
				HighScore = applesEaten;
				updateHighScore();
			}
			timer.stop();
		}
	}
	
	public void gameOver(Graphics g)
	{
		g.setColor(Color.red);
		g.setFont(new Font("Press Start 2P",Font.PLAIN,50));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
		g.setFont(new Font("Press Start 2P",Font.PLAIN,25));
		g.drawString("Score: "+applesEaten, (SCREEN_WIDTH/2)-75, (SCREEN_HEIGHT/2)+50);
		g.setFont(new Font("Press Start 2P", Font.PLAIN, 17));
		g.drawString("Press ENTER to Restart", (SCREEN_WIDTH / 2) - 155, (SCREEN_HEIGHT / 2) + 100);
		g.drawString("Q to Quit", 200, 450);
		g.drawString("B for Game Menu", 175, 500);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(running && !paused)
		{
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	public void updateHighScore()
	{
		LeaderboardManager.updateHighScore(currentUser, HighScore, "src/SnakeLeaderboard.txt");
	}
	
	public class MyKeyAdapter extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_LEFT:
				if(direction != 'R')
				{
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L')
				{
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D')
				{
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U')
				{
					direction = 'D';
				}
				break;
			case KeyEvent.VK_ENTER:
				if(!running)
				{
					if(applesEaten == HighScore)
					{
						gameFrame.setVisible(false);
						GameFrameSnake s1 = new GameFrameSnake(homeFrame, currentUser, HighScore, gameMenu);
					}
					else
					{
						resetGame();
					}
				}
				break;
			case KeyEvent.VK_P:
				paused = !paused;
				break;
			case KeyEvent.VK_Q:
            {
            	if (paused == true || running == false)
            	{
	            	gameFrame.setVisible(false);
	            	homeFrame.setVisible(true);
            	}
            	break;
            }
			case KeyEvent.VK_B:
				if (paused == true || running == false)
				{
					gameFrame.setVisible(false);
					gameMenu.setVisible(true);
				}
			}
		}
	}
}

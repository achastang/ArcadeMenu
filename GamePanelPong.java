import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanelPong extends JPanel implements Runnable
{
	static final int GAME_WIDTH = 1000;
	static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
	static final int BALL_DIAMETER = 20;
	static final int PADDLE_WIDTH = 25;
	static final int PADDLE_HEIGHT = 100;
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Padel paddle1;
	Padel paddle2;
	Ball ball;
	Score score;
	
	private int AI_SPEED = 500;
	private Integer rallyLength = 0;
	private boolean Paused = false;
	
	int highScore;
	String currentUser;
	JFrame homeFrame;
	JFrame gameFrame;
	JFrame gameMenu;
	
	GamePanelPong(JFrame homeFrame, JFrame gameFrame, String currentUser, int highScore, JFrame gameMenu)
	{
		this.homeFrame = homeFrame;
		this.gameFrame = gameFrame;
		this.currentUser = currentUser;
		this.highScore = highScore;
		this.gameMenu = gameMenu;
		newPadels();
		newBall();
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(SCREEN_SIZE);
		this.setBackground(Color.black);
		
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	
	public void newBall()
	{
		//random = new Random();
		ball = new Ball(((GAME_WIDTH / 2)-(BALL_DIAMETER / 2)),((GAME_HEIGHT / 2)-(BALL_DIAMETER / 2)),BALL_DIAMETER,BALL_DIAMETER);
		
	}
	
	public void newPadels()
	{
		paddle1 = new Padel(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
		paddle2 = new Padel(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if (!Paused) {
            Graphics2D g2 = (Graphics2D) g;
            draw(g2);
        } else {
            g.setColor(Color.white);
            g.setFont(new Font("Press Start 2P", Font.PLAIN, 35));
            g.drawString("Paused", GAME_WIDTH / 2 - 100, GAME_HEIGHT / 2);
            g.drawString("Q to Quit", GAME_WIDTH / 2 - 200, GAME_HEIGHT / 2 + 100);
            g.drawString("B for Game Menu", GAME_WIDTH / 2 - 250, GAME_HEIGHT / 2 + 50);
        }
	}
	
	public void draw(Graphics2D g2)
	{
		paddle1.draw(g2);
		paddle2.draw(g2);
		ball.draw(g2);
		
		g2.drawLine(GAME_WIDTH/2, 0, GAME_WIDTH/2, GAME_HEIGHT);
		g2.setColor(Color.white);
		g2.setFont(new Font("Press Start 2P", Font.PLAIN, 24));
		g2.drawString(rallyLength.toString(), (GAME_WIDTH/2)-50, 40);
		g2.setStroke(new BasicStroke(4f));
		g2.drawRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
	}
	
	public void move() 
	{
		paddle1.move();
		paddle2.move();
		ball.move();
	}
	
	public void checkCollision()
	{
		//bouncing ball off panels
		if(ball.intersects(paddle1))
		{
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++;
			if(ball.yVelocity > 0)
			{
				ball.yVelocity++;
			}
			else
			{
				ball.yVelocity--;
			}
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
			
			rallyLength++;
		}
		if(ball.intersects(paddle2))
		{
			ball.xVelocity = Math.abs(ball.xVelocity);
			ball.xVelocity++;
			if(ball.yVelocity > 0)
			{
				ball.yVelocity+=0.5;
			}
			else
			{
				ball.yVelocity-=0.5;
			}
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
			
			rallyLength++;
		}
		
		//ball bouncing off edges
		if(ball.y <=0)
		{
			ball.setYDirection(-ball.yVelocity);
		}
		if(ball.y >= (GAME_HEIGHT-BALL_DIAMETER))
		{
			ball.setYDirection(-ball.yVelocity);
		}
		
		//stops paddles going off
		if(paddle1.y<=0)
		{
			paddle1.y=0;
		}
		if(paddle1.y>=(GAME_HEIGHT-PADDLE_HEIGHT))
		{
			paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;
		}
		if(paddle2.y<=0)
		{
			paddle2.y=0;
		}
		if(paddle2.y>=(GAME_HEIGHT-PADDLE_HEIGHT))
		{
			paddle2.y = GAME_HEIGHT-PADDLE_HEIGHT;
		}
		
		if(ball.x < 0)
		{
			if(rallyLength>highScore)
			{
				highScore = rallyLength;
				updateHighScore();
				gameFrame.setVisible(false);
				gameFrame.dispose();
				GameFramePong p1 = new GameFramePong(homeFrame, currentUser, highScore, gameMenu);
			}
			else
			{
				newPadels();
				newBall();
				
				rallyLength = 0;
			}
		}
		if(ball.x > (GAME_WIDTH-BALL_DIAMETER))
		{
			if(rallyLength>highScore)
			{
				highScore = rallyLength;
				updateHighScore();
				gameFrame.setVisible(false);
				gameFrame.dispose();
				GameFramePong p1 = new GameFramePong(homeFrame, currentUser, highScore, gameMenu);
			}
			else
			{
				newPadels();
				newBall();
				
				rallyLength = 0;
			}
		}
	}
	
	public void run() {
	    long lastTime = System.nanoTime();
	    double amountOfTicks = 60.0;
	    double ns = 1000000000 / amountOfTicks;
	    double delta = 0;

	    while (true) {
	        long now = System.nanoTime();
	        delta += (now - lastTime) / ns;
	        lastTime = now;

	        if (delta >= 1) {
	            if (!Paused) {
	                move();
	                checkCollision();
	                aiMovement();
	                repaint();
	            }
	            delta--;
	        }
	    }
	}
	
	private void aiMovement()
	{
	    int targetY = (int)ball.y + (BALL_DIAMETER / 2) - (PADDLE_HEIGHT / 2);
	    int deltaY = targetY - paddle1.y;
	    
	    if (Math.abs(deltaY) > AI_SPEED)
	    {
	        paddle1.y += (deltaY > 0) ? AI_SPEED : -AI_SPEED;
	    }
	    else
	    {
	        paddle1.y = targetY;
	    }
	}
	
	public void updateHighScore()
	{
		LeaderboardManager.updateHighScore(currentUser, highScore, "src/PongLeaderboard.txt");
	}
	
	public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_P) {
                Paused = !Paused;
                repaint();
            }
            if (!Paused) {
                paddle2.keyPressed(e);
            }
            if ((Paused) && e.getKeyCode() == KeyEvent.VK_Q)
            {
            	gameFrame.setVisible(false);
            	homeFrame.setVisible(true);
            }
            if ((Paused) && e.getKeyCode() == KeyEvent.VK_B)
            {
            	gameFrame.setVisible(false);
            	gameMenu.setVisible(true);
            }
        }

        public void keyReleased(KeyEvent e) {
            if (!Paused) {
                paddle2.keyReleased(e);
            }
        }
    }
}

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener
{
	int boardWidth = 600;
	int boardHeight = 800;
	
	Image backgroundImg;
	Image birdImg;
	Image topPipeImg;
	Image bottomPipeImg;
	
	JFrame gameFrame;
	JFrame homeFrame;
	String currentUser;
	int highScore;
	JFrame gameMenu;
	
	boolean running = true;
	
	int birdX = boardWidth / 8;
	int birdY = boardHeight / 2;
	int birdWidth = 48;
	int birdHeight = 32;
	
	class Bird
	{
		int x = birdX;
		int y = birdY;
		int width = birdWidth;
		int height = birdHeight;
		Image img;
		
		Bird(Image img)
		{
			this.img = img;
		}
	}
	
	int pipeX = boardWidth;
	int pipeY = 0;
	int pipeWidth = 88;
	int pipeHeight = 640;
	
	class Pipe
	{
		int x = pipeX;
		int y = pipeY;
		int width = pipeWidth;
		int height = pipeHeight;
		Image img;
		boolean passed = false;
		
		Pipe(Image img)
		{
			this.img = img;
		}
	}
	
	Bird bird;
	int baseVelocityX = -4;
	double velocityX = baseVelocityX;
	int velocityY = 0;
	int gravity = 1;
	
	ArrayList<Pipe> pipes;
	Random random = new Random();
	
	Timer gameLoop;
	Timer placePipesTimer;
	int basePipeDelay = 1500;
	int pipeDelay = basePipeDelay;
	boolean gameOver = false;
	double score = 0;
	
	FlappyBird(JFrame homeFrame, JFrame gameFrame, String currentUser, int highScore, JFrame gameMenu)
	{
		this.homeFrame = homeFrame;
		this.gameFrame = gameFrame;
		this.currentUser = currentUser;
		this.highScore = highScore;
		this.gameMenu = gameMenu;
		
		setPreferredSize(new Dimension(boardWidth, boardHeight));
		setFocusable(true);
		addKeyListener(this);
		
		backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
		birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
		topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
		bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();
		
		bird = new Bird(birdImg);
		pipes = new ArrayList<Pipe>();
		
		placePipesTimer = new Timer(pipeDelay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(running)
				{
					placePipes();
				}
			}
		});
		
		placePipesTimer.start();
		
		gameLoop = new Timer(1000/60, this);
		gameLoop.start();
	}
	
	public void placePipes()
	{
		int randomPipeY = (int)(pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
		int openingSpace = boardHeight / 4;
		Pipe topPipe = new Pipe(topPipeImg);
		topPipe.y = randomPipeY;
		pipes.add(topPipe);
		
		Pipe bottomPipe = new Pipe(bottomPipeImg);
		bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
		pipes.add(bottomPipe);		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g)
	{
		g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);
		g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
		
		
		for(int i=0;i<pipes.size();i++)
		{
			Pipe pipe = pipes.get(i);
			g.drawImage(pipe.img, pipe.x , pipe.y , pipe.width, pipe.height,null);
		}
		
		g.setColor(Color.RED);
		g.setFont(new Font("Press Start 2P",Font.PLAIN, 32));
		
		if (!running)
		{
			g.drawString("Paused", 200, 400); 
			g.drawString("Q to Quit", 125, 450);
			g.drawString("B for Game Menu", 100, 500);
		}
		else
		{
			if (gameOver)
			{
				g.drawString("Game Over: " + String.valueOf((int) score), 75,  350);
				g.drawString("Enter to restart", 60, 400);
				g.drawString("Q to Quit", 90, 450);
				g.drawString("B for Game Menu", 60, 500);
			}
			else
			{
				g.setColor(Color.white);
				g.drawString(String.valueOf((int) score), 10, 35);
			}
		}
	}
	
	public void move()
	{
		velocityY += gravity;
		bird.y += velocityY;
		bird.y = Math.max(bird.y, 0);
		

		for(int i=0;i<pipes.size();i++)
		{
			Pipe pipe = pipes.get(i);
			pipe.x += velocityX;
			
			if(!pipe.passed && bird.x > pipe.x + pipe.width)
			{
				pipe.passed = true;
				score += 0.5;
			}
			
			if(collision(bird,pipe))
			{
				gameOver = true;
			}
		}
		
		if (bird.y > boardHeight)
		{
			gameOver = true;
		}
		
		if ((int)score % 10 == 0 && score != 0)
		{
			velocityX = baseVelocityX - (score / 10);
			placePipesTimer.setDelay((int) Math.min(2500, 1500 + ((score / 10) * 100)));
		}
	}
	
	public boolean collision(Bird a, Pipe b)
	{
		return a.x < b.x + b.width && 
			   a.x + a.width > b.x &&
			   a.y < b.y + b.height &&
			   a.y + a.height > b.y;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(running)
		{
			move();
			repaint();
		}
		if (gameOver)
		{
			if (score>highScore)
			{
				highScore = (int)score;
				updateHighScore();
			}
			placePipesTimer.stop();
			gameLoop.stop();
		}
		
	}
	
	public void updateHighScore()
	{
		LeaderboardManager.updateHighScore(currentUser, highScore, "src/FlappybirdLeaderboard.txt");
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			velocityY = -12;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_P)
		{
			running = !running;
			if (!running) {
                placePipesTimer.stop();
            } else {
                placePipesTimer.start();
            }
			repaint();
		}
		
		if(gameOver)
		{
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				if(highScore == score)
				{
					gameFrame.setVisible(false);
					FlappyBirdFrame s1 = new FlappyBirdFrame(homeFrame, currentUser, highScore, gameMenu);
				}
				bird.y = birdY;
				velocityY= 0;
				velocityX = baseVelocityX;
				pipes.clear();
				score = 0;
				gameOver = false;
				gameLoop.start();
				placePipesTimer.start();
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_Q)
		{
			if(gameOver || !running)
			{
				homeFrame.setVisible(true);
				gameFrame.setVisible(false);
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_B)
		{
			if(gameOver || !running)
			{
				gameMenu.setVisible(true);
				gameFrame.setVisible(false);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
		
	}
}

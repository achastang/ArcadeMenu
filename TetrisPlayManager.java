import java.awt.*;
import java.util.*;

import javax.swing.JFrame;

public class TetrisPlayManager 
{
	public TetrisPanel tetrisPanel;
	
	final int width = 360;
	final int height = 600;
	public static int leftX;
	public static int rightX;
	public static int topY;
	public static int bottomY;
	
	TetrisMino currentMino;
	final int minoStartX;
	final int minoStartY;
	TetrisMino nextMino;
	final int nextMinoX;
	final int nextMinoY;
	public static ArrayList<TetrisBlock> staticBlocks = new ArrayList<>();
	
	public static int dropInterval = 60;
	
	boolean effectCounterOn;
	int effectCounter;
	ArrayList<Integer> effectY = new ArrayList<>();
	
	public boolean gameOver = false;
	
	int level = 1;
	int lines;
	int score;
	
	JFrame homeFrame;
	JFrame gameFrame;
	String currentUser;
	int highScore;
	JFrame gameMenu;
	
	KeyHandler keyHandler;
	
	
	TetrisPlayManager(JFrame homeFrame, JFrame gameFrame, String currentUser, int highScore, TetrisPanel tetrisPanel, JFrame gameMenu)
	{
		this.homeFrame = homeFrame;
		this.gameFrame = gameFrame;
		this.currentUser = currentUser;
		this.highScore = highScore;
		this.tetrisPanel = tetrisPanel;
		this.gameMenu = gameMenu;
		
		this.keyHandler = new KeyHandler();
        this.gameFrame.addKeyListener(keyHandler);
		
		leftX = 540;
		rightX = leftX + width;
		topY = 50;
		bottomY = topY + height;
		
		minoStartX = leftX + (width/2) - TetrisBlock.size;
		minoStartY = topY + TetrisBlock.size;
		
		nextMinoX = rightX+165;
		nextMinoY = topY + 485;
		
		currentMino = pickMino();
		currentMino.setXY(minoStartX, minoStartY);
		nextMino = pickMino();
		nextMino.setXY(nextMinoX, nextMinoY);
	}
	
	private TetrisMino pickMino()
	{
		TetrisMino mino = null;
		int i = new Random().nextInt(7);
		
		switch(i)
		{
		case 0: mino = new MinoL1(); break;
		case 1: mino = new MinoL2(); break;
		case 2: mino = new MinoSquare(); break;
		case 3: mino = new MinoBar(); break;
		case 4: mino = new MinoT(); break;
		case 5: mino = new MinoZ1(); break;
		case 6: mino = new MinoZ2(); break;
		}
		return mino;
	}
	
	public void update()
	{
		if (currentMino.active == false)
		{
			staticBlocks.add(currentMino.b[0]);
			staticBlocks.add(currentMino.b[1]);
			staticBlocks.add(currentMino.b[2]);
			staticBlocks.add(currentMino.b[3]);
			
			if(currentMino.b[0].x == minoStartX && currentMino.b[0].y == minoStartY)
			{
				gameOver = true;
			}
			
			currentMino.deactivating = false;
			
			currentMino = nextMino;
			currentMino.setXY(minoStartX, minoStartY);
			nextMino = pickMino();
			nextMino.setXY(nextMinoX, nextMinoY);
			
			checkDelete();
		}
		else
		{
			currentMino.update();
		}
	}
	
	private void checkDelete()
	{
		int x = leftX;
		int y = topY;
		int blockCount = 0;
		int lineCount = 0;
		
		while(x<rightX && y<bottomY)
		{
			for(int i=0;i<staticBlocks.size();i++)
			{
				if(staticBlocks.get(i).x == x && staticBlocks.get(i).y == y)
				{
					blockCount++;
				}
			}
			
			x += TetrisBlock.size;
			
			if(x==rightX)
			{
				if(blockCount == 12)
				{
					effectCounterOn = true;
					effectY.add(y);
					
					for(int i = staticBlocks.size()-1;i>-1;i--)
					{
						if(staticBlocks.get(i).y == y)
						{
							staticBlocks.remove(i);
						}
					}
					
					lineCount++;
					lines++;
					
					if (lines % 10 == 0 && dropInterval >1)
					{
						level++;
						if (dropInterval > 10)
						{
							dropInterval -= 10;
						}
						else
						{
							dropInterval -= 1;
						}
					}
					
					for (int i=0;i<staticBlocks.size();i++)
					{
						if (staticBlocks.get(i).y < y)
						{
							staticBlocks.get(i).y += TetrisBlock.size;
						}
					}
				}
				
				blockCount = 0;
				x = leftX;
				y+= TetrisBlock.size;
			}
		}
		
		if (lineCount > 0) {
			int singleLineScore = 10 * level;
			score += singleLineScore * lineCount;
		}
	}

	
	public void draw(Graphics2D g2)
	{
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(4f));
		g2.drawRect(leftX-4, topY-4, width+8, height+8);
		
		int x = rightX +80;
		int y = bottomY -200;
		g2.drawRect(x, y, 200, 200);
		g2.setFont(new Font("Press Start 2P", Font.PLAIN,18));
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.drawString("Next", x+70, y+40);
		
		g2.drawRect(x, topY, 200, 325);
		x += 40;
		y = topY + 90;
		g2.drawString("Level: " + level, x, y); y+=70;
		g2.drawString("Lines: " + lines, x, y); y+=70;
		g2.drawString("Score: " + score, x, y); y+=70;
		
		if(currentMino != null)
		{
			currentMino.draw(g2);
		}
		
		nextMino.draw(g2);
		
		for(int i=0;i<staticBlocks.size();i++)
		{
			staticBlocks.get(i).draw(g2);
		}
		
		if(effectCounterOn)
		{
			effectCounter++;
			g2.setColor(Color.red);
			for(int i = 0;i< effectY.size();i++)
			{
				g2.fillRect(leftX, effectY.get(i), width, TetrisBlock.size);
			}
			
			if(effectCounter == 10)
			{
				effectCounterOn = false;
				effectCounter = 0;
				effectY.clear();
			}	
		}
		
		if(gameOver)
		{
			if (lines > highScore)
			{
				highScore = lines;
				updateHighScore();
			}
			g2.setColor(Color.red);
			g2.setFont(g2.getFont().deriveFont(50f));
			x = leftX + 40;
			y = topY+320;
			g2.drawString("GAME OVER", x, y);
		}
		
		g2.setColor(Color.red);
		g2.setFont(new Font("Press Start 2P", Font.PLAIN, 32));
		
		if(KeyHandler.pausePressed)
		{
			x = leftX + 70;
			y = topY +320;
			g2.drawString("PAUSED", x, y);
			g2.drawString("Q to Quit", 575, 450);
			g2.drawString("B for Game Menu", 525, 500);
		}
		if(KeyHandler.qPressed)
		{
			if ((KeyHandler.pausePressed) || (gameOver == true))
			{
				tetrisPanel.stopGame();
				removeKeyListeners();
				reset();
				gameFrame.repaint();
				gameFrame.setVisible(false);
				gameFrame.dispose();
				GameMenuFrame g1 = new GameMenuFrame();
				KeyHandler.pausePressed = false;
			}
			KeyHandler.qPressed = false;
			
		}
		if (KeyHandler.enterPressed && gameOver) {
			if(highScore == lines)
			{
				gameFrame.setVisible(false);
				TetrisGameMenu t1 = new TetrisGameMenu(homeFrame, currentUser, highScore, gameMenu);
			}
			else
			{
				reset();
				gameFrame.repaint();
			}
			KeyHandler.enterPressed = false;
        }
		if ((KeyHandler.bPressed) && (KeyHandler.pausePressed || gameOver))
		{
			gameFrame.setVisible(false);
			gameMenu.setVisible(true);
			tetrisPanel.stopGame();
			removeKeyListeners();
			reset();
			gameFrame.repaint();
			gameFrame.setVisible(false);
			gameFrame.dispose();
			KeyHandler.bPressed = false;
			KeyHandler.pausePressed = false;
			
		}
	}
	
	public void removeKeyListeners() {
	    gameFrame.removeKeyListener(keyHandler);
	}
	
	public void reset() {
       
        gameOver = false;
        level = 1;
        lines = 0;
        score = 0;
        dropInterval = 60;
        staticBlocks.clear();
        effectCounterOn = false;
        effectCounter = 0;
        effectY.clear();

        
        currentMino = pickMino();
        currentMino.setXY(minoStartX, minoStartY);
        nextMino = pickMino();
        nextMino.setXY(nextMinoX, nextMinoY);
        
        gameFrame.repaint();
    }
	
	public void updateHighScore()
	{
		LeaderboardManager.updateHighScore(currentUser, highScore, "src/TetrisLeaderboard.txt");
	}
}

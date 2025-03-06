import java.awt.*;	
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Score extends Rectangle
{
	static int GAME_WIDTH;
	static int GAME_HEIGHT;
	int player;
		
	Score(int GAME_WIDTH, int GAME_HEIGHT)
	{
		Score.GAME_WIDTH = GAME_WIDTH;
		Score.GAME_HEIGHT = GAME_HEIGHT;
		player = 0;
	}
		
	public void increase()
	{
		player++;
	}
		
	public void draw(Graphics g)
	{
		g.setColor(Color.white);
		g.drawLine(GAME_WIDTH / 2, 0, GAME_WIDTH / 2, GAME_HEIGHT);
		g.setFont(new Font("Press Start 2P",Font.PLAIN,30));
		g.drawString("Score: "+player, 25, 600);		
	}
}
	
	

import java.awt.*;


public class TetrisBlock extends Rectangle
{
	public int x, y;
	public static final int size = 30;
	public Color c;
	
	public TetrisBlock(Color c)
	{
		this.c = c;
	}
	
	public void draw(Graphics2D g2)
	{
		int margin = 1;
		g2.setColor(c);
		g2.fillRect(x+margin, y+margin, size-(margin*2), size-(margin*2));
	}
}

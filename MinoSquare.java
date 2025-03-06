import java.awt.Color;

public class MinoSquare extends TetrisMino
{
	public MinoSquare()
	{
		create(Color.yellow);
	}
	
	public void setXY(int x, int y)
	{
		b[0].x = x;
		b[0].y = y;
		b[1].x = b[0].x;
		b[1].y = b[0].y + TetrisBlock.size;
		b[2].x = b[0].x + TetrisBlock.size;
		b[2].y = b[0].y;
		b[3].x = b[0].x + TetrisBlock.size;
		b[3].y = b[0].y + TetrisBlock.size;
	}
	
	public void getDirection1()
	{
	}
	
	public void getDirection2()
	{
	}
		
	public void getDirection3()
	{
	}
	
	public void getDirection4()
	{
	}
}

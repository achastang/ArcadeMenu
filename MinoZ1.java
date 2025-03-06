import java.awt.Color;

public class MinoZ1 extends TetrisMino
{
	public MinoZ1()
	{
		create(Color.red);
	}
	
	public void setXY(int x, int y)
	{
		b[0].x = x;
		b[0].y = y;
		b[1].x = b[0].x;
		b[1].y = b[0].y - TetrisBlock.size;
		b[2].x = b[0].x - TetrisBlock.size;
		b[2].y = b[0].y;
		b[3].x = b[0].x - TetrisBlock.size;
		b[3].y = b[0].y + TetrisBlock.size;
	}
	
	public void getDirection1()
	{
		tempB[0].x = b[0].x;
		tempB[0].y = b[0].y;
		tempB[1].x = b[0].x;
		tempB[1].y = b[0].y - TetrisBlock.size;
		tempB[2].x = b[0].x - TetrisBlock.size;
		tempB[2].y = b[0].y;
		tempB[3].x = b[0].x - TetrisBlock.size;
		tempB[3].y = b[0].y + TetrisBlock.size;
		
		updateXY(1);
	}
	
	public void getDirection2()
	{
		tempB[0].x = b[0].x;
		tempB[0].y = b[0].y;
		tempB[1].x = b[0].x + TetrisBlock.size;
		tempB[1].y = b[0].y;
		tempB[2].x = b[0].x;
		tempB[2].y = b[0].y - TetrisBlock.size;
		tempB[3].x = b[0].x - TetrisBlock.size;
		tempB[3].y = b[0].y - TetrisBlock.size;
		
		updateXY(2);
	}
		
	public void getDirection3()
	{
		getDirection1();
	}
	
	public void getDirection4()
	{
		getDirection2();
	}
}

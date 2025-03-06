import java.awt.*;

public class TetrisMino 
{
	public TetrisBlock b[] = new TetrisBlock[4];
	public TetrisBlock tempB[] = new TetrisBlock[4];
	int autoDropCounter = 0;
	public int direction = 1;
	boolean leftCollision;
	boolean rightCollision;
	boolean bottomCollision;
	public boolean active = true;
	public boolean deactivating = true;
	int deactivateCounter = 0;
	
	public void create(Color c)
	{
		b[0] = new TetrisBlock(c);
		b[1] = new TetrisBlock(c);
		b[2] = new TetrisBlock(c);
		b[3] = new TetrisBlock(c);
		tempB[0] = new TetrisBlock(c);
		tempB[1] = new TetrisBlock(c);
		tempB[2] = new TetrisBlock(c);
		tempB[3] = new TetrisBlock(c);
	}
	
	public void setXY(int x, int y)
	{
		
	}
	
	public void updateXY(int direction)
	{
		checkRotationCollision();
	
		if (leftCollision == false && rightCollision == false && bottomCollision == false)
		{
			this.direction = direction;
			b[0].x = tempB[0].x;
			b[0].y = tempB[0].y;
			b[1].x = tempB[1].x;
			b[1].y = tempB[1].y;
			b[2].x = tempB[2].x;
			b[2].y = tempB[2].y;
			b[3].x = tempB[3].x;
			b[3].y = tempB[3].y;
		}
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
	
	public void checkMovementCollision()
	{
		leftCollision = false;
		rightCollision = false;
		bottomCollision = false;
		
		checkStaticBlockCollision();
		
		for(int i=0;i<b.length;i++)
		{
			if(b[i].x == TetrisPlayManager.leftX)
			{
				leftCollision = true;
			}
		}
		
		for(int i=0;i<b.length;i++)
		{
			if((b[i].x + TetrisBlock.size) == TetrisPlayManager.rightX)
			{
				rightCollision = true;
			}
		}
		
		for(int i=0;i<b.length;i++)
		{
			if((b[i].y + TetrisBlock.size) == TetrisPlayManager.bottomY)
			{
				bottomCollision = true;
			}
		}
	}
	
	public void checkRotationCollision()
	{
		leftCollision = false;
		rightCollision = false;
		bottomCollision = false;
		
		checkStaticBlockCollision();
		
		for(int i=0;i<b.length;i++)
		{
			if(tempB[i].x < TetrisPlayManager.leftX)
			{
				leftCollision = true;
			}
		}
		
		for(int i=0;i<b.length;i++)
		{
			if((tempB[i].x + TetrisBlock.size) > TetrisPlayManager.rightX)
			{
				rightCollision = true;
			}
		}
		
		for(int i=0;i<b.length;i++)
		{
			if((tempB[i].y + TetrisBlock.size) > TetrisPlayManager.bottomY)
			{
				bottomCollision = true;
			}
		}
	}
	
	private void checkStaticBlockCollision()
	{
		for(int i=0;i<TetrisPlayManager.staticBlocks.size();i++)
		{
			int targetX = TetrisPlayManager.staticBlocks.get(i).x;
			int targetY = TetrisPlayManager.staticBlocks.get(i).y;
			
			for(int j=0;j<b.length;j++)
			{
				if((b[j].y + TetrisBlock.size) == targetY && b[j].x == targetX)
				{
					bottomCollision = true;
				}
			}
			
			for(int j=0;j<b.length;j++)
			{
				if((b[j].x - TetrisBlock.size) == targetX && b[j].y == targetY)
				{
					leftCollision = true;
				}
			}
			
			for(int j=0;j<b.length;j++)
			{
				if((b[j].x + TetrisBlock.size) == targetX && b[j].y == targetY)
				{
					rightCollision = true;
				}
			}
		}
	}
	
	public void update()
	{
		if (deactivating)
		{
			deactivating();
		}
		if(KeyHandler.rPressed)
		{
			switch(direction)
			{
			case 1: getDirection2(); break;
			case 2: getDirection3(); break;
			case 3: getDirection4(); break;
			case 4: getDirection1(); break;
			}
			KeyHandler.rPressed = false;
		}
		
		checkMovementCollision();
		
		if(KeyHandler.downPressed)
		{
			if (bottomCollision == false)
			{
				b[0].y += TetrisBlock.size;
				b[1].y += TetrisBlock.size;
				b[2].y += TetrisBlock.size;
				b[3].y += TetrisBlock.size;
				
				autoDropCounter = 0;
			}
			
			KeyHandler.downPressed = false;
		}
		if(KeyHandler.leftPressed)
		{
			if (leftCollision == false)
			{
				b[0].x -= TetrisBlock.size;
				b[1].x -= TetrisBlock.size;
				b[2].x -= TetrisBlock.size;
				b[3].x -= TetrisBlock.size;
			}
			
			KeyHandler.leftPressed = false;
		}
		if(KeyHandler.rightPressed)
		{
			if (rightCollision == false)
			{
				b[0].x += TetrisBlock.size;
				b[1].x += TetrisBlock.size;
				b[2].x += TetrisBlock.size;
				b[3].x += TetrisBlock.size;
			}
			
			KeyHandler.rightPressed = false;
		}
		
		if(bottomCollision)
		{
			deactivating = true;
		}
		else
		{
			autoDropCounter++;
			
			if(autoDropCounter == TetrisPlayManager.dropInterval)
			{
				b[0].y += TetrisBlock.size;
				b[1].y += TetrisBlock.size;
				b[2].y += TetrisBlock.size;
				b[3].y += TetrisBlock.size;
				autoDropCounter = 0;
			}
		}
	}
	
	private void deactivating()
	{
		deactivateCounter++;
		
		if(deactivateCounter == 45)
		{
			deactivateCounter = 0;
			checkMovementCollision();
			
			if(bottomCollision) {
				active = false;
			}
		}
	}
	
	public void draw(Graphics2D g2)
	{
		int margin = 1;
		g2.setColor(b[0].c);
		g2.fillRect(b[0].x+margin, b[0].y+margin, TetrisBlock.size-(margin*2), TetrisBlock.size-(margin*2));
		g2.fillRect(b[1].x+margin, b[1].y+margin, TetrisBlock.size-(margin*2), TetrisBlock.size-(margin*2));
		g2.fillRect(b[2].x+margin, b[2].y+margin, TetrisBlock.size-(margin*2), TetrisBlock.size-(margin*2));
		g2.fillRect(b[3].x+margin, b[3].y+margin, TetrisBlock.size-(margin*2), TetrisBlock.size-(margin*2));
	}
}

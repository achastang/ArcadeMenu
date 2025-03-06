import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener
{
	
	public static boolean rPressed, downPressed, leftPressed, rightPressed, pausePressed, qPressed, enterPressed, bPressed;

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_LEFT)
		{
			leftPressed = true;
		}
		if(code == KeyEvent.VK_RIGHT)
		{
			rightPressed = true;
		}
		if(code == KeyEvent.VK_R)
		{
			rPressed = true;
		}
		if(code == KeyEvent.VK_DOWN)
		{
			downPressed = true;
		}
		if(code == KeyEvent.VK_P)
		{
			if(pausePressed)
			{
				pausePressed = false;
			}
			else
			{
				pausePressed = true;
			}
		}
		if (code == KeyEvent.VK_Q)
		{
			qPressed = true;
		}
		if (code == KeyEvent.VK_ENTER)
		{
			enterPressed = true;
		}
		if (code == KeyEvent.VK_B)
		{
			bPressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();
        if (code == KeyEvent.VK_DOWN) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_R) {
            rPressed = false;
        }
        if (code == KeyEvent.VK_LEFT) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_RIGHT) {
            rightPressed = false;
        }
	}

}

import javax.swing.*;

public class GameMenuFrame 
{
	public JFrame frame;
	
	public GameMenuFrame()
	{
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		GameMenuPanel panel = new GameMenuPanel(frame);
		frame.add(panel);
		frame.pack();
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
        new GameMenuFrame();
    }
}

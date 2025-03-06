import java.awt.Color;
import java.awt.Font;

import javax.swing.*;


public class TetrisGameMenu 
{
	String currentUser;
	JFrame homeFrame;
	int highScore;
	JFrame frame = new JFrame();
	JTextArea user = new JTextArea();
	JTextArea HighScore = new JTextArea();
	JFrame gameMenu;
	
	TetrisGameMenu(JFrame homeFrame, String currentUser, int highScore, JFrame gameMenu)
	{
		this.homeFrame = homeFrame;
		this.currentUser = currentUser;
		this.highScore = highScore;
		frame.setLayout(null);
		this.gameMenu = gameMenu;
	
		frame.setTitle("Tetris");
		frame.setResizable(false);
		frame.setSize(1300,800);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		TetrisPanel panel = new TetrisPanel(homeFrame, frame, currentUser, highScore, gameMenu);
		panel.setBounds(0,0,1300,800);
		panel.setBackground(Color.BLACK);
		frame.add(panel);
		
		user.setEditable(false);
        user.setBounds(25, 200, 400, 50);
        user.setBackground(Color.black);
        user.setForeground(Color.white);
        user.setFont(new Font("Press Start 2P", Font.PLAIN, 26));
        user.setText("User: "+currentUser);
        panel.add(user);
        HighScore.setEditable(false);
        HighScore.setBounds(25, 400, 400, 50);
        HighScore.setBackground(Color.black);
        HighScore.setForeground(Color.white);
        HighScore.setFont(new Font("Press Start 2P", Font.PLAIN, 26));
        HighScore.setText("High Score: "+ highScore);
        panel.add(HighScore);
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		panel.lanchGame();
	}
}

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GameFramePong extends JFrame
{
	
	GamePanelPong panel;
	JFrame Frame = new JFrame();
	JFrame homeFrame;
	JTextArea user = new JTextArea();
	JTextArea highScore = new JTextArea();
	String currentUser;
	int HighScore;
	JFrame gameMenu;
	
	GameFramePong(JFrame homeFrame, String currentUser, int HighScore, JFrame gameMenu)
	{
		this.homeFrame = homeFrame;
		this.currentUser = currentUser;
		this.HighScore = HighScore;
		this.gameMenu = gameMenu;
		Frame.setLayout(null);
		panel = new GamePanelPong(homeFrame, Frame, currentUser, HighScore, gameMenu);
		panel.setBounds(150,100,1000,GamePanelPong.GAME_HEIGHT);
		panel.setBackground(Color.BLACK);
		
		user.setEditable(false);
        user.setBounds(20, 20, 250, 50);
        user.setBackground(Color.black);
        user.setForeground(Color.white);
        user.setFont(new Font("Press Start 2P", Font.PLAIN, 15));
        user.setText("User: "+currentUser);
        Frame.add(user);
        highScore.setEditable(false);
        highScore.setBounds(400, 20, 250, 50);
        highScore.setBackground(Color.black);
        highScore.setForeground(Color.white);
        highScore.setFont(new Font("Press Start 2P", Font.PLAIN, 15));
        highScore.setText("High Score: "+ HighScore);
        Frame.add(highScore);
		
		Frame.add(panel);
		Frame.setTitle("Pong");
		Frame.setResizable(false);
		Frame.setSize(1300,800);
		Frame.getContentPane().setBackground(Color.BLACK);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		Frame.setVisible(true);
		Frame.setLocationRelativeTo(null);
		
		panel.requestFocusInWindow();
	}
}

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class FlappyBirdFrame extends JFrame
{
	
	FlappyBird panel;
	JFrame Frame = new JFrame();
	JFrame homeFrame;
	JTextArea user = new JTextArea();
	JTextArea highScore = new JTextArea();
	String currentUser;
	int HighScore;
	JFrame gameMenu;
	
	FlappyBirdFrame(JFrame homeFrame, String currentUser, int HighScore, JFrame gameMenu)
	{
		this.homeFrame = homeFrame;
		this.currentUser = currentUser;
		this.HighScore = HighScore;
		Frame.setLayout(null);
		panel = new FlappyBird(homeFrame, Frame, currentUser, HighScore, gameMenu);
		panel.setBounds(600,0,600,800);
		panel.setBackground(Color.BLACK);
		
		user.setEditable(false);
        user.setBounds(25, 200, 400, 50);
        user.setBackground(Color.black);
        user.setForeground(Color.white);
        user.setFont(new Font("Press Start 2P", Font.PLAIN, 26));
        user.setText("User: "+currentUser);
        Frame.add(user);
        highScore.setEditable(false);
        highScore.setBounds(25, 400, 400, 50);
        highScore.setBackground(Color.black);
        highScore.setForeground(Color.white);
        highScore.setFont(new Font("Press Start 2P", Font.PLAIN, 26));
        highScore.setText("High Score: "+ HighScore);
        Frame.add(highScore);
		
		Frame.add(panel);
		Frame.setTitle("FlappyBird");
		Frame.setResizable(false);
		Frame.setSize(1300,800);
		Frame.getContentPane().setBackground(Color.BLACK);
		Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		Frame.setVisible(true);
		Frame.setLocationRelativeTo(null);
		
		panel.requestFocusInWindow();
	}
}

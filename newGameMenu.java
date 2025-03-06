import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class newGameMenu 
{
	private JFrame parentFrame;
    private JFrame ngFrame;
    
	public newGameMenu(JFrame parentFrame)
	{
		this.parentFrame = parentFrame;
		
		this.ngFrame = new JFrame();
        ngFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ngFrame.setResizable(false);
        ngFrame.setSize(1300, 800);
        ngFrame.setLocationRelativeTo(null);
        
        JPanel p1 = new JPanel();
        p1.setBounds(0, 0, 1300, 800);
        p1.setBackground(Color.black);
        p1.setLayout(null);
        
        JPanel backButton = makeButton("Back", 30, 50, 150, 75, 24);
        p1.add(backButton);
        JPanel loginButton = makeButton("Login", 100, 150, 500, 400, 60);
        p1.add(loginButton);
        JPanel newButton = makeButton("New User", 700, 150, 500, 400, 60);
        p1.add(newButton);
        
        ngFrame.add(p1);
        
        ngFrame.setVisible(true);
	}
	
	public JPanel makeButton(String buttonLabel, int x, int y, int width, int height, int fontSize) {
        CustomButtonPanel buttonPanel = new CustomButtonPanel(buttonLabel, width, height, fontSize);
        buttonPanel.setBounds(x, y, width, height);
        buttonPanel.setBackground(new Color(0, 0, 0, 0));

        buttonPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                buttonPanel.setHover(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                buttonPanel.setHover(false);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
					handleButtonClick(buttonLabel);
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
            }
        });

        return buttonPanel;
    }
	
	public void handleButtonClick(String buttonLabel) throws Exception {
    	switch (buttonLabel) {
        case "Back":
            ngFrame.setVisible(false);
            parentFrame.setVisible(true);
            break;
        case "Login":
        	ngFrame.setVisible(false);
        	loginFrame lgFrame = new loginFrame(ngFrame,parentFrame);
        	break;
        case "New User":
        	ngFrame.setVisible(false);
        	newUserFrame nuFrame = new newUserFrame(ngFrame);
        	break;
    	}
    }
}

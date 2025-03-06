import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class newUserFrame 
{
	private JFrame ngFrame;
	private JFrame signUpFrame = new JFrame();
	private JTextArea textArea1 = new JTextArea();
	private JTextArea textArea2 = new JTextArea();
	private JTextArea textArea3 = new JTextArea();
	private JTextArea textArea4 = new JTextArea();
	private List<userData> users = new ArrayList<>();
	
	newUserFrame(JFrame ngFrame)
	{
		this.ngFrame = ngFrame;
		signUpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		signUpFrame.setResizable(false);
		signUpFrame.setSize(1300, 800);
		signUpFrame.setLocationRelativeTo(null);
		
		CustomPanel p1 = new CustomPanel();
        p1.setBounds(0, 0, 1300, 800);
        p1.setBackground(Color.black);
        p1.setLayout(null);
        
        textArea1.setEditable(true);
        textArea1.setBounds(285, 165, 735, 80);
        textArea1.setBackground(Color.black);
        textArea1.setForeground(Color.white);
        textArea1.setFont(new Font("Press Start 2P", Font.PLAIN, 56));
       
        textArea2.setEditable(false);
        textArea2.setBounds(275, 100, 740, 45);
        textArea2.setBackground(Color.black);
        textArea2.setForeground(Color.gray);
        textArea2.setFont(new Font("Press Start 2P", Font.PLAIN, 44));
        textArea2.setText("//Username");
        
        textArea3.setEditable(true);
        textArea3.setBounds(285, 415, 735, 80);
        textArea3.setBackground(Color.black);
        textArea3.setForeground(Color.white);
        textArea3.setFont(new Font("Press Start 2P", Font.PLAIN, 56));
       
        textArea4.setEditable(false);
        textArea4.setBounds(275, 350, 740, 45);
        textArea4.setBackground(Color.black);
        textArea4.setForeground(Color.gray);
        textArea4.setFont(new Font("Press Start 2P", Font.PLAIN, 44));
        textArea4.setText("//Password");
        
        JPanel backButton = makeButton("Back", 30, 50, 150, 75, 24);
        p1.add(backButton);
        JPanel createUserButton = makeButton("Create User", 300, 575, 700, 150, 60);
        p1.add(createUserButton);
        
        p1.add(textArea1);
        p1.add(textArea2);
        p1.add(textArea3);
        p1.add(textArea4);
        
        signUpFrame.add(p1);
        signUpFrame.setVisible(true);
	}
	
	private void loadUsersFromFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                if (parts.length >= 2) {
                    String name = parts[0];
                    String password = parts[1];
                    users.add(new userData(name, password));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
	
	private void handleRegistration() {
        String newUsername = textArea1.getText();
        String newPassword = textArea3.getText();

        if (newUsername.isEmpty() || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(signUpFrame, "Username and password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (userData user : users) {
            if (user.getName().equals(newUsername)) {
                JOptionPane.showMessageDialog(signUpFrame, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        userData newUser = new userData(newUsername, newPassword);
        users.add(newUser);
        appendUserToFile(newUser);
        JOptionPane.showMessageDialog(signUpFrame, "Registration successful!\n"
        		+ "Go Back to Login");
    }

    private void appendUserToFile(userData user) {
        try (FileWriter writer = new FileWriter("src/userInfo.txt", true)) {
            writer.write(user.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            signUpFrame.setVisible(false);
            ngFrame.setVisible(true);
            break;
        case "Create User":
        	loadUsersFromFile("src/userInfo.txt");
        	handleRegistration();
    	}
    }
	
	class CustomPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            draw(g2);
        }

        public void draw(Graphics2D g2) {
        	g2.setStroke(new BasicStroke(4f));
            g2.setColor(Color.orange);
            g2.drawRect(250, 50, 800, 700);
            g2.setColor(Color.red);
            g2.drawRect(247, 47, 800, 700);
            g2.setStroke(new BasicStroke(2f));
            g2.setColor(Color.white);
            g2.drawRect(275, 150, 750, 100);
            g2.drawRect(275, 400, 750, 100);
        }
	}
}

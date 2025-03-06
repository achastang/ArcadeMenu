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

public class leaderboardMenu
{
	private JFrame parentFrame;
    private JFrame lFrame;
    public JTextArea textArea = new JTextArea();
    private Map<String, Integer> pongLeaderboard = new HashMap<>();
    private Map<String, Integer> snakeLeaderboard = new HashMap<>();
    private Map<String, Integer> flappyLeaderboard = new HashMap<>();
    private Map<String, Integer> tetrisLeaderboard = new HashMap<>();
    private Map<String, Integer> totalLeaderboard = new HashMap<>();
    
	public leaderboardMenu(JFrame parentFrame)
	{
		this.parentFrame = parentFrame;

        if (parentFrame != null) {
            parentFrame.setVisible(false);
            parentFrame.dispose();
        }

        this.lFrame = new JFrame();
        lFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        lFrame.setResizable(false);
        lFrame.setSize(1300, 800);
        lFrame.setLocationRelativeTo(null);

        CustomPanel p1 = new CustomPanel();
        p1.setBounds(0, 0, 1300, 800);
        p1.setBackground(Color.black);
        p1.setLayout(null);
        
        textArea.setEditable(false);

        textArea.setBounds(225, 160, 960, 525);
        textArea.setBackground(Color.black);
        textArea.setForeground(Color.white);
        textArea.setFont(new Font("Press Start 2P", Font.PLAIN, 24));
        p1.add(textArea);
        
        JPanel backButton = makeButton("Back", 30, 50, 150, 75, 24);
        p1.add(backButton);
        JPanel totalLeaderboardButton = makeButton("Total", 225, 50, 175, 75, 24);
        p1.add(totalLeaderboardButton);
        JPanel pongLeaderboardButton = makeButton("Pong", 425, 50, 175, 75, 24);
        p1.add(pongLeaderboardButton);
        JPanel snakeLeaderboardButton = makeButton("Snake", 625, 50, 175, 75, 24);
        p1.add(snakeLeaderboardButton);
        JPanel flappyLeaderboardButton = makeButton("Flappy", 825, 50, 175, 75, 24);
        p1.add(flappyLeaderboardButton);
        JPanel tetrisLeaderboardButton = makeButton("Tetris", 1025, 50, 175, 75, 24);
        p1.add(tetrisLeaderboardButton);       
        
        lFrame.add(p1);
        
        

        lFrame.setVisible(true);
	}
	
	public void loadAllLeaderboards() throws FileNotFoundException {
	    loadLeaderboardFromFile("src/PongLeaderboard.txt", pongLeaderboard);
	    loadLeaderboardFromFile("src/SnakeLeaderboard.txt", snakeLeaderboard);
	    loadLeaderboardFromFile("src/FlappybirdLeaderboard.txt", flappyLeaderboard);
	    loadLeaderboardFromFile("src/TetrisLeaderboard.txt", tetrisLeaderboard);
	}
	
	public void loadLeaderboardFromFile(String fileName, Map<String, Integer> leaderboard) throws FileNotFoundException {
	    File inputFile = new File(fileName);
	    Scanner fileReader = new Scanner(inputFile);
	    
	    while (fileReader.hasNextLine()) {
	        String line = fileReader.nextLine();
	        String[] parts = line.split(" ");
	        if (parts.length == 2) {
	            String name = parts[0];
	            int score = Integer.parseInt(parts[1]);
	            leaderboard.put(name, score);
	        }
	    }
	    
	    fileReader.close();
	}
	
	public List<LeaderboardEntry> storeArrayFromFile(String file) throws FileNotFoundException {
        File inputFile = new File(file);
        Scanner fileReader = new Scanner(inputFile);
        List<LeaderboardEntry> entries = new ArrayList<>();

        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            String[] parts = line.split(" ");
            String name = parts[0];
            int score = Integer.parseInt(parts[1]);
            entries.add(new LeaderboardEntry(name, score));
        }
        fileReader.close();

        Collections.sort(entries, new Comparator<LeaderboardEntry>() {
            @Override
            public int compare(LeaderboardEntry o1, LeaderboardEntry o2) {
                return Integer.compare(o2.getScore(), o1.getScore());
            }
        });

        return entries;
    }
	
	public void calculateTotalLeaderboard() {
	    Map<String, Integer> combinedScores = new HashMap<>();
	    for (Map<String, Integer> gameLeaderboard : Arrays.asList(pongLeaderboard, snakeLeaderboard, flappyLeaderboard, tetrisLeaderboard)) {
	        for (Map.Entry<String, Integer> entry : gameLeaderboard.entrySet()) {
	            String name = entry.getKey();
	            int score = entry.getValue();
	            combinedScores.merge(name, score, Integer::sum);
	        }
	    }

	    totalLeaderboard.clear();
	    totalLeaderboard.putAll(combinedScores);
	}
	
	public void saveTotalLeaderboard(String fileName) {
	    try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
	        List<Map.Entry<String, Integer>> sortedEntries = totalLeaderboard.entrySet().stream()
	                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
	                .collect(Collectors.toList());

	        for (Map.Entry<String, Integer> entry : sortedEntries) {
	            writer.println(entry.getKey() + " " + entry.getValue());
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	 public void leaderboardWriter(List<LeaderboardEntry> leaderboard) {
	        textArea.setText("");
	        for (int i = 0; i < Math.min(8, leaderboard.size()); i++) {
	            LeaderboardEntry entry = leaderboard.get(i);
	            textArea.append((i + 1) + ". " + entry.getName() + ": " + entry.getScore() + "\n\n\n");
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
	            lFrame.setVisible(false);
	            parentFrame.setVisible(true);
	            break;
	        case "Total":
	        	loadAllLeaderboards();
	        	calculateTotalLeaderboard();
	        	saveTotalLeaderboard("src/TotalLeaderboard.txt");
	        	List<LeaderboardEntry> totals = storeArrayFromFile("src/TotalLeaderboard.txt");
	        	leaderboardWriter(totals);
	        	break;
	        case "Pong":
	        	List<LeaderboardEntry> pongs = storeArrayFromFile("src/PongLeaderboard.txt");
	        	leaderboardWriter(pongs);
	        	break;
	        case "Snake":
	        	List<LeaderboardEntry> snakes = storeArrayFromFile("src/SnakeLeaderboard.txt");
	        	leaderboardWriter(snakes);
	        	break;
	        case "Flappy":
	        	List<LeaderboardEntry> flaps = storeArrayFromFile("src/FlappybirdLeaderboard.txt");
	        	leaderboardWriter(flaps);
	        	break;
	        case "Tetris":
	        	List<LeaderboardEntry> tets = storeArrayFromFile("src/TetrisLeaderboard.txt");
	        	leaderboardWriter(tets);
	        	break;
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
            g2.drawRect(200, 150, 1050, 600);
            g2.setColor(Color.red);
            g2.drawRect(197, 147, 1050, 600);
        }
    }
}

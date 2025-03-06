import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class gameSelection {
    private JFrame homeFrame;
    private JFrame gameFrame = new JFrame();
    private JTextArea textArea1 = new JTextArea();
    String currentUserName;
    int highScore;
    Image PongImg;
    Image SnakeImg;
    Image FlappyImg;
    Image TetrisImg;

    public gameSelection(JFrame homeFrame, String currentUserName) {
        this.homeFrame = homeFrame;
        this.currentUserName = currentUserName;

        PongImg = new ImageIcon(getClass().getResource("/Pong.png")).getImage();
        SnakeImg = new ImageIcon(getClass().getResource("/Snake.png")).getImage();
        FlappyImg = new ImageIcon(getClass().getResource("/FlappyBirdGame.png")).getImage();
        TetrisImg = new ImageIcon(getClass().getResource("/Tetris.png")).getImage();

        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        gameFrame.setSize(1300, 800);
        gameFrame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(Color.black);

        textArea1.setEditable(false);
        textArea1.setBounds(25, 25, 400, 30);
        textArea1.setBackground(Color.black);
        textArea1.setForeground(Color.white);
        textArea1.setFont(new Font("Press Start 2P", Font.PLAIN, 24));
        textArea1.setText("User: " + currentUserName);
        mainPanel.add(textArea1);

        JPanel backButton = makeButton("Home", 800, 25, 175, 75, 24);
        mainPanel.add(backButton);
        JPanel controlsButton = makeButton("Controls", 1025, 25, 250, 75, 24);
        mainPanel.add(controlsButton);
        JPanel pongButton = makeButton("Pong", 200, 125, 300, 75, 40);
        mainPanel.add(pongButton);
        JPanel snakeButton = makeButton("Snake", 800, 125, 300, 75, 40);
        mainPanel.add(snakeButton);
        JPanel flappyButton = makeButton("Flappy", 200, 450, 300, 75, 40);
        mainPanel.add(flappyButton);
        JPanel tetrisButton = makeButton("Tetris", 800, 450, 300, 75, 40);
        mainPanel.add(tetrisButton);

        CustomPanel pongImagePanel = new CustomPanel(PongImg);
        pongImagePanel.setBounds(200, 200, 300, 200);
        mainPanel.add(pongImagePanel);

        CustomPanel snakeImagePanel = new CustomPanel(SnakeImg);
        snakeImagePanel.setBounds(800, 200, 300, 200);
        mainPanel.add(snakeImagePanel);

        CustomPanel flappyImagePanel = new CustomPanel(FlappyImg);
        flappyImagePanel.setBounds(200, 525, 300, 200);
        mainPanel.add(flappyImagePanel);

        CustomPanel tetrisImagePanel = new CustomPanel(TetrisImg);
        tetrisImagePanel.setBounds(800, 525, 300, 200);
        mainPanel.add(tetrisImagePanel);

        gameFrame.add(mainPanel);
        gameFrame.setVisible(true);
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
            case "Home":
                gameFrame.setVisible(false);
                homeFrame.setVisible(true);
                break;
            case "Controls":
                controlsMenu c = new controlsMenu(gameFrame);
                gameFrame.setVisible(false);
                break;
            case "Pong":
                gameFrame.setVisible(false);
                highScore = LeaderboardManager.getHighScoreForUser(currentUserName, "src/PongLeaderboard.txt");
                GameFramePong p = new GameFramePong(homeFrame, currentUserName, highScore, gameFrame);
                break;
            case "Snake":
                gameFrame.setVisible(false);
                highScore = LeaderboardManager.getHighScoreForUser(currentUserName, "src/SnakeLeaderboard.txt");
                GameFrameSnake s = new GameFrameSnake(homeFrame, currentUserName, highScore, gameFrame);
                break;
            case "Flappy":
                gameFrame.setVisible(false);
                highScore = LeaderboardManager.getHighScoreForUser(currentUserName, "src/FlappybirdLeaderboard.txt");
                FlappyBirdFrame f = new FlappyBirdFrame(homeFrame, currentUserName, highScore, gameFrame);
                break;
            case "Tetris":
                gameFrame.setVisible(false);
                highScore = LeaderboardManager.getHighScoreForUser(currentUserName, "src/TetrisLeaderboard.txt");
                TetrisGameMenu t = new TetrisGameMenu(homeFrame, currentUserName, highScore, gameFrame);
        }
    }

    class CustomPanel extends JPanel {
    	private Image image;

        public CustomPanel(Image image) {
            this.image = image;
        }
    	
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            draw(g2);
        }

        public void draw(Graphics2D g2) {
            if (image != null) {
                g2.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            } else {
                g2.setColor(Color.RED);
                g2.setFont(new Font("Press Start 2P", Font.PLAIN, 24));
                g2.drawString("Image not found!", 10, 25);
            }
        }
    }
}

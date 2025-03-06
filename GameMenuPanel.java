import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameMenuPanel extends JPanel {
    static final int screenWidth = 1300;
    static final int screenHeight = 800;
    static final int DELAY = 50;
    static final int maxStars = 125;
    int starXVelocity = -1;
    int starYVelocity = 1;

    public List<Point> stars;
    public Random random;
    Timer timer;

    Image uranus;
    Image planet;
    private static JFrame parentFrame;

    public GameMenuPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.setLayout(null);

        uranus = new ImageIcon(getClass().getResource("./uranus.png")).getImage();
        planet = new ImageIcon(getClass().getResource("./planet.png")).getImage();

        stars = new ArrayList<>();
        random = new Random();

        createStars();

        timer = new Timer(DELAY, e -> {
            moveStars();
            repaint();
        });
        timer.start();

        JPanel newGameButton = makeButton("New Game", 230, 350, 375, 150, 44);
        add(newGameButton);
        JPanel leaderboardButton = makeButton("Leaderboard", 650, 350, 375, 150, 32);
        add(leaderboardButton);
    }

    public void createStars() {
        stars.clear();
        for (int i = 0; i < maxStars; i++) {
            int x = random.nextInt(screenWidth);
            int y = random.nextInt(screenHeight);
            stars.add(new Point(x, y));
        }
    }

    public void moveStars() {
        for (int i = 0; i < stars.size(); i++) {
            Point star = stars.get(i);
            star.x += starXVelocity;
            star.y += starYVelocity;

            if (star.x < 0 || star.y > screenHeight) {
                stars.remove(i);
                i--;
            }

            while (stars.size() < maxStars) {
                int x;
                int y;

                if (random.nextDouble() < 0.25) {
                    x = screenWidth - 1;
                    y = random.nextInt(screenHeight);
                } else {
                    x = random.nextInt(screenWidth);
                    y = 0;
                }
                stars.add(new Point(x, y));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        draw(g2);
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);
        for (Point star : stars) {
            g2.fillRect(star.x, star.y, 10, 10);
        }
        g2.drawImage(uranus, 150, 600, 100, 100, null);
        g2.drawImage(planet, 1050, 150, 85, 85, null);
        g2.setStroke(new BasicStroke(8f));
        g2.setColor(Color.orange);
        g2.drawRect(322, 97, 650, 200);
        g2.setColor(Color.red);
        g2.drawRect(325, 100, 650, 200);
        Font font = new Font("Press Start 2P", Font.PLAIN, 70);
        g2.setFont(font);
        g2.setColor(Color.cyan);
        g2.drawString("1P-ARCADE", 333, 278);
        g2.setColor(Color.blue);
        g2.drawString("1P-ARCADE", 330, 275);
    }

    public static JPanel makeButton(String buttonLabel, int x, int y, int width, int height, int fontSize) {
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
                handleButtonClick(buttonLabel);
            }
        });

        return buttonPanel;
    }
    
    public static void handleButtonClick(String buttonLabel) {
    	switch (buttonLabel) {
        case "New Game":
            newGameMenu n1 = new newGameMenu(parentFrame);
            parentFrame.setVisible(false);
            break;
        case "Leaderboard":
        	leaderboardMenu lb = new leaderboardMenu(parentFrame);
        	parentFrame.setVisible(false);
            break;
        default:
        	System.out.println("It was this all along");
    	}
    }
}
    
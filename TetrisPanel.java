import java.awt.*;
import javax.swing.*;

public class TetrisPanel extends JPanel implements Runnable {
    public static final int screenWidth = 1300;
    public static final int screenHeight = 800;
    final int FPS = 60;
    Thread gameThread;
    TetrisPlayManager tpm;
    
    JFrame homeFrame;
	JFrame gameFrame;
	String currentUser;
	int highScore;
	public static boolean gameOver;
	JFrame gameMenu;

    public TetrisPanel(JFrame homeFrame, JFrame gameFrame, String currentUser, int highScore, JFrame gameMenu) {
    	
    	this.homeFrame = homeFrame;
		this.gameFrame = gameFrame;
		this.currentUser = currentUser;
		this.highScore = highScore;
		this.gameMenu = gameMenu;
		
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setLayout(null);
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);

        tpm = new TetrisPlayManager(homeFrame, gameFrame, currentUser, highScore, this, gameMenu);
    }

    public void lanchGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }
    
    public void stopGame() {
        // Stop the game thread
        if (gameThread != null) {
            gameThread = null;  // Stop the thread loop
        }
    }

    private void update() {
        if (!KeyHandler.pausePressed && !tpm.gameOver) {
            tpm.update();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        tpm.draw(g2);
    }
}
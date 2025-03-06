import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class controlsMenu {
    private JFrame parentFrame;
    private JFrame cFrame;
    String controls = "Pong: Up and Down arrows to move red \n\npadel \n\n\n\n"
    		+ "Snake: Arrows to move and collect apples\n\n\n\n"
    		+ "FlappyBird: Space to jump\n\n\n\n"
    		+ "Tetris: Arrows to move minos and R to \n\nrotate minos\n\n\n\n"
    		+ "P to pause, Enter to reset on all games";
    public JTextArea textArea = new JTextArea();

    public controlsMenu(JFrame parentFrame) {
        this.parentFrame = parentFrame;

        if (parentFrame != null) {
            parentFrame.setVisible(false);
            parentFrame.dispose();
        }

        this.cFrame = new JFrame();
        cFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cFrame.setResizable(false);
        cFrame.setSize(1300, 800);
        cFrame.setLocationRelativeTo(null);

        CustomPanel p1 = new CustomPanel();
        p1.setBounds(0, 0, 1300, 800);
        p1.setBackground(Color.black);
        
        textArea.setEditable(false);
        textArea.setBounds(225, 75, 960, 600);
        textArea.setBackground(Color.black);
        textArea.setForeground(Color.white);
        textArea.setFont(new Font("Press Start 2P", Font.PLAIN, 24));
        p1.add(textArea);

        JPanel backButton = makeButton("Back", 30, 50, 150, 75, 24);
        p1.setLayout(null);
        p1.add(backButton);
        cFrame.add(p1);

        cFrame.setVisible(true);
        
        startTextAnimation();
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
                handleButtonClick(buttonLabel);
            }
        });

        return buttonPanel;
    }

    public void handleButtonClick(String buttonLabel) {
    	switch (buttonLabel) {
        case "Back":
            cFrame.setVisible(false);
            parentFrame.setVisible(true);
            break;
    	}
    }
    
    private void startTextAnimation() {
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                StringBuilder displayedText = new StringBuilder();
                for (char ch : controls.toCharArray()) {
                    displayedText.append(ch);
                    publish(displayedText.toString());
                    Thread.sleep(10);  // Adjust delay as needed
                }
                return null;
            }

            @Override
            protected void process(java.util.List<String> chunks) {
                textArea.setText(chunks.get(chunks.size() - 1));
            }
        };
        worker.execute();
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
            g2.drawRect(200, 50, 1050, 700);
            g2.setColor(Color.red);
            g2.drawRect(197, 47, 1050, 700);
        }
    }
}

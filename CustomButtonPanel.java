import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

class CustomButtonPanel extends JPanel 
{
    private String buttonLabel;
    private int width;
    private int height;
    private int fontSize;
    private boolean hover;

    public CustomButtonPanel(String buttonLabel, int width, int height, int fontSize) 
	{
        this.buttonLabel = buttonLabel;
        this.width = width;
        this.height = height;
        this.fontSize = fontSize;
        this.hover = false;
        setOpaque(false);
    }

    public void setHover(boolean hover) {
        this.hover = hover;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(hover ? Color.orange : Color.cyan);
        g2.setFont(new Font("Press Start 2P", Font.PLAIN, fontSize));
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(buttonLabel);
        int textHeight = fm.getAscent();
        g2.drawString(buttonLabel, (width - textWidth) / 2, (height + textHeight) / 2 - 5);

        g2.setStroke(new BasicStroke(4f));
        g2.setColor(Color.orange);
        g2.drawRect(0, 0, width - 1, height - 1);
        g2.setColor(Color.red);
        g2.drawRect(3, 3, width - 7, height - 7);
    }
}
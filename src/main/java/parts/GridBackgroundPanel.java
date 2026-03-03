package parts;

import javax.swing.*;
import java.awt.*;

public class GridBackgroundPanel extends JPanel {
    private final int cellSize;

    public GridBackgroundPanel(int cellSize) {
        this.cellSize = cellSize;
        setOpaque(true);
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
    }

    private void drawGrid(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        g.setColor(Color.LIGHT_GRAY);

        for (int x = 0; x <= width; x += cellSize) {
            g.drawLine(x, 0, x, height);
        }

        for (int y = 0; y <= height; y += cellSize) {
            g.drawLine(0, y, width, y);
        }

    }
}

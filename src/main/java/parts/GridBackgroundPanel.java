package parts;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GridBackgroundPanel extends JPanel {
    private final int cellSize;
    private final List<Point> points = new ArrayList<>();

    public GridBackgroundPanel(int cellSize) {
        this.cellSize = cellSize;
        setBackground(Color.WHITE);
    }

    public void drawPoint(int x, int y) {
        points.add(new Point(x,y));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawPoints(g);
    }

    private void drawPoints(Graphics g) {
        g.setColor(Color.BLACK);
        for (Point p : points) {
            g.fillOval(p.x - 1, p.y - 1, 3, 3);
        }
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

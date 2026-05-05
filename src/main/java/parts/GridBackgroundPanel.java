package parts;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GridBackgroundPanel extends JPanel {
    private static final int PADDING = 20;

    private final int cellSize;
    private BoundingBoxView boundingBox;
    private final List<ColoredPoint> points = new ArrayList<>();
    private final List<ColoredText> texts = new ArrayList<>();

    public GridBackgroundPanel(int cellSize) {
        this.cellSize = cellSize;
        setBackground(Color.WHITE);
    }

    public void clearDrawing() {
        points.clear();
        texts.clear();
        boundingBox = null;
        repaint();
    }

    public void setBoundingBox(BoundingBoxView boundingBox) {
        this.boundingBox = boundingBox;
        repaint();
    }

    public void setPoints(List<ColoredPoint> newPoints) {
        points.clear();
        points.addAll(newPoints);
        repaint();
    }

    public void setTexts(List<ColoredText> newTexts) {
        texts.clear();
        texts.addAll(newTexts);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        drawBoundingBox(g);
        drawPoints(g);
        drawTexts(g);
    }

    private void drawBoundingBox(Graphics g) {
        if (boundingBox == null) return;

        Graphics2D g2 = (Graphics2D) g.create();

        float[] dash = {3f, 3f};
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(
                1f,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10f,
                dash,
                0f
        ));

        int x = toScreenX(boundingBox.x1());
        int y = toScreenY(boundingBox.y2());

        int width = boundingBox.x2() - boundingBox.x1();
        int height = boundingBox.y2() - boundingBox.y1();

        g2.drawRect(x, y, width, height);
        g2.dispose();
    }

    private void drawPoints(Graphics g) {
        g.setColor(Color.BLACK);

        for (ColoredPoint p : points) {
            g.setColor(p.color());

            int screenX = toScreenX(p.x());
            int screenY = toScreenY(p.y());

            g.fillOval(screenX - 1, screenY - 1, 3, 3);
        }
    }

    private void drawTexts(Graphics g) {
        for (ColoredText text : texts) {
            g.setColor(text.color());
            g.drawString(text.text(), toScreenX(text.x()), toScreenY(text.y()));
        }
    }

    private void drawGrid(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        g.setColor(Color.LIGHT_GRAY);

        for (int x = PADDING; x <= width; x += cellSize) {
            g.drawLine(x, 0, x, height);
        }

        for (int y = PADDING; y <= height; y += cellSize) {
            g.drawLine(0, y, width, y);
        }
    }

    private int toScreenX(int x) {
        return PADDING + x;
    }

    private int toScreenY(int y) {
        if (boundingBox == null) {
            return PADDING + y;
        }

        return PADDING + (boundingBox.y2() - y);
    }
}

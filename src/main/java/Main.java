import parts.ColoredPoint;
import parts.ColoredText;
import parts.GridBackgroundPanel;
import types.DrawingEngine;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Grid Background");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1400, 900);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            GridBackgroundPanel gridPanel = new GridBackgroundPanel(20);

            JTextArea editor = new JTextArea("""
            (BOUNDING-BOX (0 0) (320 240))
            (FILL yellow (CIRCLE (180 130) 70))
            (DRAW black
              (CIRCLE (180 130) 70)
              (LINE (180 130) (250 130))
              (LINE (180 130) (130 180))
            )
            (TEXT-AT (20 25) "Demo text + fill")
            """);

/*
            JTextArea editor = new JTextArea("""
            (BOUNDING-BOX (0 0) (900 500))
            (DRAW black
              (RECTANGLE (420 180) (820 330))
              (LINE (420 180) (820 180))
              (LINE (420 180) (420 330))
            )

            (FILL gray
              (RECTANGLE (420 285) (780 310))
            )

            (FILL gray
              (RECTANGLE (420 240) (620 265))
            )

            (FILL gray
              (RECTANGLE (420 195) (540 220))
            )

            (DRAW black
              (RECTANGLE (420 285) (780 310))
              (RECTANGLE (420 240) (620 265))
              (RECTANGLE (420 195) (540 220))

              (TEXT-AT (355 295) "Prolog")
              (TEXT-AT (365 250) "Slang")
              (TEXT-AT (365 205) "Scala")

              (TEXT-AT (790 292) "16")
              (TEXT-AT (630 247) "9")
              (TEXT-AT (550 202) "5")

              (TEXT-AT (415 150) "0")
              (TEXT-AT (535 150) "5")
              (TEXT-AT (660 150) "10")
              (TEXT-AT (785 150) "15")

              (TEXT-AT (480 90) "What do you expect from life?")
            )
            """);
*/
/*
            JTextArea editor = new JTextArea("""
            (BOUNDING-BOX (0 0) (900 500))
            (DRAW black
              (CIRCLE (220 280) 120)

              (LINE (220 280) (340 280))
              (LINE (220 280) (257 394))
              (LINE (220 280) (228 400))
              (LINE (220 280) (149 377))
              (LINE (220 280) (285 195))

              (TEXT-AT (355 300) "Scala")
              (TEXT-AT (260 420) "Lisp")
              (TEXT-AT (120 410) "Haskell")
              (TEXT-AT (35 220) "Prolog")
              (TEXT-AT (340 190) "other")

              (TEXT-AT (280 325) "20%")
              (TEXT-AT (242 375) "4%")
              (TEXT-AT (165 360) "11%")
              (TEXT-AT (160 245) "49%")
              (TEXT-AT (275 220) "16%")

              (TEXT-AT (70 80) "Popularity of Programming Languages")
            )
            """);
*/

            JButton drawButton = new JButton("Draw");

            JPanel rightPanel = new JPanel(new BorderLayout());
            rightPanel.add(new JScrollPane(editor), BorderLayout.CENTER);
            rightPanel.add(drawButton, BorderLayout.SOUTH);

            JSplitPane splitPane = new JSplitPane(
                    JSplitPane.HORIZONTAL_SPLIT,
                    gridPanel,
                    rightPanel
            );

            splitPane.setResizeWeight(0.7);
            splitPane.setDividerLocation(0.7);
            frame.add(splitPane);

            drawButton.addActionListener(e -> {
                gridPanel.clearDrawing();

                var boundingBox = DrawingEngine.getBoundingBox(editor.getText());

                boundingBox.ifPresent(box ->
                        gridPanel.setBoundingBox(
                                new parts.BoundingBoxView(
                                        box.x1(),
                                        box.y1(),
                                        box.x2(),
                                        box.y2()
                                )
                        )
                );

                var drawablePoints = DrawingEngine.interpret(editor.getText());
                var drawableTexts = DrawingEngine.interpretTexts(editor.getText());

                var coloredPoints = new ArrayList<ColoredPoint>();
                var coloredTexts = new ArrayList<ColoredText>();

                drawablePoints.forEach(p ->
                        coloredPoints.add(
                                new ColoredPoint(
                                        p.x(),
                                        p.y(),
                                        toJavaColor(p.color())
                                )
                        )
                );

                drawableTexts.forEach(t ->
                        coloredTexts.add(
                                new ColoredText(
                                        t.x(),
                                        t.y(),
                                        t.text(),
                                        toJavaColor(t.color())
                                )
                        )
                );

                gridPanel.setPoints(coloredPoints);
                gridPanel.setTexts(coloredTexts);
            });

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static Color toJavaColor(String color) {
        return switch (color.toLowerCase()) {
            case "red" -> Color.RED;
            case "green" -> Color.GREEN;
            case "blue" -> Color.BLUE;
            case "yellow" -> Color.YELLOW;
            case "gray", "grey" -> Color.GRAY;
            case "orange" -> Color.ORANGE;
            case "pink" -> Color.PINK;
            default -> Color.BLACK;
        };
    }
}

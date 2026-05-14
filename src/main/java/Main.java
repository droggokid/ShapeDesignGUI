import parts.ColoredPoint;
import parts.ColoredText;
import parts.GridBackgroundPanel;
import types.DrawingEngine;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
    private static final String DEFAULT_PIE_EXAMPLE = """
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
            """;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Grid Background");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1400, 900);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

            GridBackgroundPanel gridPanel = new GridBackgroundPanel(20);

            JTextArea editor = new JTextArea(DEFAULT_PIE_EXAMPLE);
            JTextArea errorArea = new JTextArea();
            errorArea.setEditable(false);
            errorArea.setRows(4);
            errorArea.setForeground(Color.RED.darker());
            errorArea.setText("Ready.");

            JButton drawButton = new JButton("Draw");
            JButton loadPieExampleButton = new JButton("Load Pie Example");
            JButton loadBarExampleButton = new JButton("Load Bar Example");

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            buttonPanel.add(drawButton);
            buttonPanel.add(loadPieExampleButton);
            buttonPanel.add(loadBarExampleButton);

            JPanel rightPanel = new JPanel(new BorderLayout());
            rightPanel.add(buttonPanel, BorderLayout.NORTH);
            rightPanel.add(new JScrollPane(editor), BorderLayout.CENTER);
            rightPanel.add(new JScrollPane(errorArea), BorderLayout.SOUTH);

            JSplitPane splitPane = new JSplitPane(
                    JSplitPane.HORIZONTAL_SPLIT,
                    gridPanel,
                    rightPanel
            );

            splitPane.setResizeWeight(0.7);
            splitPane.setDividerLocation(0.7);
            frame.add(splitPane);

            drawButton.addActionListener(e -> {
                String program = editor.getText();
                gridPanel.clearDrawing();
                errorArea.setText("");

                try {
                    var boundingBox = DrawingEngine.getBoundingBox(program);
                    var drawablePoints = DrawingEngine.interpret(program);
                    var drawableTexts = DrawingEngine.interpretTexts(program);

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
                    errorArea.setText("Draw successful.");
                } catch (RuntimeException ex) {
                    errorArea.setText("Error:\n" + ex.getMessage());
                }
            });

            loadPieExampleButton.addActionListener(e -> {
                loadExample(editor, errorArea, "pie-chart.shape", DEFAULT_PIE_EXAMPLE, "pie chart");
            });

            loadBarExampleButton.addActionListener(e -> {
                loadExample(editor, errorArea, "bar-chart.shape", DEFAULT_BAR_EXAMPLE, "bar chart");
            });

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static final String DEFAULT_BAR_EXAMPLE = """
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
            """;

    private static void loadExample(
            JTextArea editor,
            JTextArea errorArea,
            String fileName,
            String fallback,
            String label
    ) {
        Path path = Path.of("docs", "examples", fileName);

        try {
            editor.setText(Files.readString(path));
            errorArea.setText("Loaded " + label + " example.");
        } catch (IOException ex) {
            errorArea.setText("Could not read " + path + ":\n" + ex.getMessage() + "\nUsing bundled example.");
            editor.setText(fallback);
        }
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

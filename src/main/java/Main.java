import parts.GridBackgroundPanel;
import types.Hello;
import types.Draw;
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Grid Background");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            GridBackgroundPanel gridBackgroundPanel = new GridBackgroundPanel(20);
            gridBackgroundPanel.setLayout(new BorderLayout());
            frame.add(gridBackgroundPanel);

            JLabel label = new JLabel(Hello.greet(), SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 24));
            //gridBackgroundPanel.add(label, BorderLayout.CENTER);


            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            //gridBackgroundPanel.drawPoint(100, 100);
            //gridBackgroundPanel.drawPoint(300, 300);

            var points = Draw.drawLine(100,100,300,300);

            points.forEach(x -> gridBackgroundPanel.drawPoint((Integer) x._1, (Integer)x._2));
        });
    }
}
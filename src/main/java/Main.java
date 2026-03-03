import parts.GridBackgroundPanel;
import types.Hello;
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
            label.setOpaque(false);
            label.setForeground(Color.BLACK);
            gridBackgroundPanel.add(label, BorderLayout.CENTER);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
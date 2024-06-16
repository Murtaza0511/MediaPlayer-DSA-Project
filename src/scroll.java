import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class scroll {
    public static void main(String[] args) {
//
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Scroll to Bottom Example");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setSize(300, 200);
//
//            JTextArea textArea = new JTextArea();
//            JScrollPane scrollPane = new JScrollPane(textArea);
//
//            JButton addButton = new JButton("Add Text");
//            addButton.addActionListener(e -> {
//                textArea.append("This is a new line.\n");
//                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
//            });
//
//            frame.setLayout(new BorderLayout());
//            frame.add(scrollPane, BorderLayout.CENTER);
//            frame.add(addButton, BorderLayout.SOUTH);
//
//            frame.setVisible(true);
//        });
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Scroll Panel with Buttons Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);

            JPanel buttonPanel = createButtonPanel();

            JScrollPane scrollPane = new JScrollPane(buttonPanel);

            frame.setLayout(new FlowLayout());
            frame.add(scrollPane, BorderLayout.CENTER);

            frame.setVisible(true);

            // Scroll down programmatically after a delay (e.g., 2 seconds)
            Timer timer = new Timer(2000, e -> {
                JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
                verticalScrollBar.setValue(verticalScrollBar.getMaximum());
            });
            timer.setRepeats(false); // Execute only once
            timer.start();
        });
    }
    private static JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 3));

        for (int i = 1; i <= 15; i++) {
            JButton button = new JButton("Button " + i);
            panel.add(button);
        }

        return panel;
    }
}


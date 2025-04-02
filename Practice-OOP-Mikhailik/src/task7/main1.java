package task7;

import javax.swing.*;
import java.awt.*;

public class main1 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI(args));
    }

    private static void createAndShowGUI(String[] args) {
        JFrame frame = new JFrame("Аргументи командного рядка");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);

        if (args.length == 0) {
            textArea.setText("Аргументи командного рядка не передані.");
        } else {
            StringBuilder sb = new StringBuilder("Передані аргументи:\n");
            for (int i = 0; i < args.length; i++) {
                sb.append((i + 1)).append(": ").append(args[i]).append("\n");
            }
            textArea.setText(sb.toString());
        }

        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.setVisible(true);
    }
}


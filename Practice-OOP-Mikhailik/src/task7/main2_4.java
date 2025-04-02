package task7;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Клас main2_4 для підрахунку голосних літер у рядку.
 */
public class main2_4 {

    /**
     * Підраховує кількість кожної голосної літери у рядку.
     *
     * @param input рядок для аналізу
     * @return мапа з кількістю кожної голосної літери
     */
    public static Map<Character, Integer> countVowels(String input) {
        // Створюємо мапу для збереження кількості кожної голосної літери
        Map<Character, Integer> vowelCount = new HashMap<>();

        // Переводимо рядок до нижнього регістру для зручності порівняння
        input = input.toLowerCase();

        // Проходимо по кожному символу в рядку
        for (char c : input.toCharArray()) {
            // Перевіряємо, чи є символ голосною літерою
            if (isVowel(c)) {
                // Якщо голосна, збільшуємо її кількість у мапі
                vowelCount.put(c, vowelCount.getOrDefault(c, 0) + 1);
            }
        }

        return vowelCount;
    }

    /**
     * Перевіряє, чи є символ голосною літерою.
     *
     * @param c символ для перевірки
     * @return true, якщо символ голосний, інакше false
     */
    private static boolean isVowel(char c) {
        return "aeiou".indexOf(c) != -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(main2_4::createAndShowGUI);
    }

    /**
     * Створює та показує графічний інтерфейс для введення рядка та відображення результатів.
     */
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Підрахунок голосних літер");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 1, 5, 5));

        JLabel inputLabel = new JLabel("Введіть рядок:");
        JTextField inputField = new JTextField();
        JButton countButton = new JButton("Підрахувати");
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);

        countButton.addActionListener(e -> {
            String input = inputField.getText();
            Map<Character, Integer> result = countVowels(input);

            // Виведення результатів підрахунку голосних літер
            StringBuilder resultText = new StringBuilder();
            for (Map.Entry<Character, Integer> entry : result.entrySet()) {
                resultText.append("Голосна '").append(entry.getKey()).append("' зустрічається ").append(entry.getValue()).append(" разів.\n");
            }

            // Виведення результату в TextArea
            if (resultText.length() == 0) {
                resultText.append("У рядку немає голосних літер.");
            }
            resultArea.setText(resultText.toString());
        });

        // Додавання компонентів на панель
        frame.add(inputLabel);
        frame.add(inputField);
        frame.add(countButton);
        frame.add(new JScrollPane(resultArea));

        frame.setVisible(true);
    }
}

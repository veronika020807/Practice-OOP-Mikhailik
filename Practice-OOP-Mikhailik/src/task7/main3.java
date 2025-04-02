package task7;

import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Інтерфейс для відображуваних об'єктів.
 */
interface Displayable {
    void display();
}

/**
 * Клас для підрахунку голосних у рядку.
 */
class VowelCounter implements Displayable {
    private final String input;
    private final Map<Character, Integer> vowelCount;

    public VowelCounter(String input) {
        this.input = input.toLowerCase();
        this.vowelCount = countVowels(this.input);
    }

    /**
     * Підраховує кількість кожної голосної літери у рядку.
     */
    private Map<Character, Integer> countVowels(String input) {
        Map<Character, Integer> vowelCount = new HashMap<>();
        for (char c : input.toCharArray()) {
            if (isVowel(c)) {
                vowelCount.put(c, vowelCount.getOrDefault(c, 0) + 1);
            }
        }
        return vowelCount;
    }

    /**
     * Перевіряє, чи є символ голосною літерою.
     */
    private boolean isVowel(char c) {
        return "аеєиіїоуюя".indexOf(c) != -1;
    }

    /**
     * Виводить результати підрахунку.
     */
    @Override
    public void display() {
        StringBuilder result = new StringBuilder("Результати підрахунку голосних:\n");
        for (Map.Entry<Character, Integer> entry : vowelCount.entrySet()) {
            result.append("Голосна '").append(entry.getKey())
                    .append("' зустрічається ").append(entry.getValue())
                    .append(" разів.\n");
        }
        JOptionPane.showMessageDialog(null, result.toString(), "Результати", JOptionPane.INFORMATION_MESSAGE);
    }
}

/**
 * Фабрика для створення об'єктів VowelCounter.
 */
class VowelCounterFactory {
    public static Displayable createVowelCounter(String input) {
        return new VowelCounter(input);
    }
}

/**
 * Основний клас програми для графічного інтерфейсу.
 */
public class main3 {
    public static void main(String[] args) {
        // Створення вікна програми
        JFrame frame = new JFrame("Підрахунок голосних");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());

        // Поле для введення тексту
        JTextField inputField = new JTextField();
        frame.add(inputField, BorderLayout.NORTH);

        // Кнопка для обробки введеного тексту
        JButton processButton = new JButton("Підрахувати голосні");
        frame.add(processButton, BorderLayout.CENTER);

        // Панель для відображення результатів
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        frame.add(scrollPane, BorderLayout.SOUTH);

        // Додавання функціональності для кнопки
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                if (!input.isEmpty()) {
                    // Використання фабричного методу
                    Displayable counter = VowelCounterFactory.createVowelCounter(input);
                    counter.display();
                } else {
                    JOptionPane.showMessageDialog(frame, "Будь ласка, введіть текст.", "Помилка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Показуємо вікно
        frame.setVisible(true);
    }
}

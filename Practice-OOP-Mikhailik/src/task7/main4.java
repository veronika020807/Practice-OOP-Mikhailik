package task7;


import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Основний клас програми для графічного інтерфейсу.
 */
public class main4 {
    public static void main(String[] args) {
        // Створення вікна програми
        JFrame frame = new JFrame("Підрахунок голосних");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 250);
        frame.setLayout(new BorderLayout());

        // Панель для введення тексту
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel inputLabel = new JLabel("Введіть рядок для аналізу:");
        JTextField inputField = new JTextField();
        JLabel tableLabel = new JLabel("Відобразити у вигляді таблиці? (true/false):");
        JTextField tableField = new JTextField("false");
        JLabel columnWidthLabel = new JLabel("Ширина стовпців:");
        JTextField columnWidthField = new JTextField("10");

        panel.add(inputLabel);
        panel.add(inputField);
        panel.add(tableLabel);
        panel.add(tableField);
        panel.add(columnWidthLabel);
        panel.add(columnWidthField);

        frame.add(panel, BorderLayout.CENTER);

        // Кнопка для обробки введеного тексту
        JButton processButton = new JButton("Підрахувати голосні");
        frame.add(processButton, BorderLayout.SOUTH);

        // Додавання функціональності для кнопки
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                String tableText = tableField.getText();
                int columnWidth = Integer.parseInt(columnWidthField.getText());

                boolean asTable = tableText.equalsIgnoreCase("true");

                if (!input.isEmpty()) {
                    // Використання фабричного методу
                    Displayable counter = VowelCounterFactory.createVowelCounter(input, asTable, columnWidth);
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

/**
 * Інтерфейс для відображуваних об'єктів.
 */
interface Displayable {
    void display();
}

/**
 * Базовий клас для підрахунку голосних у рядку.
 */
class SimpleVowelCounter implements Displayable {
    protected final String input;
    protected final Map<Character, Integer> vowelCount;

    public SimpleVowelCounter(String input) {
        this.input = input.toLowerCase();
        this.vowelCount = countVowels(this.input);
    }

    /**
     * Підраховує кількість кожної голосної літери у рядку.
     */
    protected Map<Character, Integer> countVowels(String input) {
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
    protected boolean isVowel(char c) {
        return "аеєиіїоуюя".indexOf(c) != -1;
    }

    /**
     * Виводить результати підрахунку у звичайному текстовому вигляді.
     */
    @Override
    public void display() {
        System.out.println("Результати підрахунку голосних:");
        for (Map.Entry<Character, Integer> entry : vowelCount.entrySet()) {
            System.out.println("Голосна '" + entry.getKey() + "' зустрічається " + entry.getValue() + " разів.");
        }
    }
}

/**
 * Похідний клас для відображення результатів у вигляді таблиці.
 */
class TableVowelCounter extends SimpleVowelCounter {
    private final int columnWidth;

    public TableVowelCounter(String input, int columnWidth) {
        super(input);
        this.columnWidth = columnWidth;
    }

    /**
     * Відображає результати у форматі таблиці.
     */
    @Override
    public void display() {
        System.out.println("+" + "-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+");
        System.out.printf("| %" + (columnWidth - 2) + "s | %" + (columnWidth - 2) + "s |%n", "Голосна", "Кількість");
        System.out.println("+" + "-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+");
        for (Map.Entry<Character, Integer> entry : vowelCount.entrySet()) {
            System.out.printf("| %" + (columnWidth - 2) + "s | %" + (columnWidth - 2) + "d |%n", entry.getKey(), entry.getValue());
        }
        System.out.println("+" + "-".repeat(columnWidth) + "+" + "-".repeat(columnWidth) + "+");
    }
}

/**
 * Фабрика для створення об'єктів VowelCounter.
 */
class VowelCounterFactory {
    public static Displayable createVowelCounter(String input, boolean asTable, int columnWidth) {
        return asTable ? new TableVowelCounter(input, columnWidth) : new SimpleVowelCounter(input);
    }
}

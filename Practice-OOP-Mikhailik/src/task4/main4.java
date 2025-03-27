package task4;

import java.util.*;

/**
 * Інтерфейс для відображуваних об'єктів.
 */
interface Displayable {
    void display();
}

/**
 * Базовий клас для підрахунку голосних у рядку.
 */
class VowelCounter implements Displayable {
    protected final String input;
    protected final Map<Character, Integer> vowelCount;

    public VowelCounter(String input) {
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
class TableVowelCounter extends VowelCounter {
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
        return asTable ? new TableVowelCounter(input, columnWidth) : new VowelCounter(input);
    }
}

/**
 * Основний клас програми.
 */
public class main4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть рядок для аналізу:");
        String input = scanner.nextLine();

        System.out.println("Відобразити у вигляді таблиці? (true/false):");
        boolean asTable = scanner.nextBoolean();

        int columnWidth = 10;
        if (asTable) {
            System.out.println("Введіть ширину стовпців таблиці:");
            columnWidth = scanner.nextInt();
        }
        scanner.close();

        // Використання фабричного методу
        Displayable counter = VowelCounterFactory.createVowelCounter(input, asTable, columnWidth);
        counter.display();
    }
}

/**
 * Клас для тестування функціональності.
 */
class VowelCounterTest {
    public static void main(String[] args) {
        System.out.println("Тест 1: Звичайний режим");
        Displayable test1 = new VowelCounter("Привіт, як справи?");
        test1.display();

        System.out.println("\nТест 2: Табличний режим");
        Displayable test2 = new TableVowelCounter("Оце так новина!", 12);
        test2.display();
    }
}

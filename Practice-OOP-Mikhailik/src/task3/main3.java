package task3;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
        System.out.println("Результати підрахунку голосних:");
        for (Map.Entry<Character, Integer> entry : vowelCount.entrySet()) {
            System.out.println("Голосна '" + entry.getKey() + "' зустрічається " + entry.getValue() + " разів.");
        }
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
 * Основний клас програми.
 */
public class main3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введіть рядок для аналізу:");
        String input = scanner.nextLine();
        scanner.close();

        // Використання фабричного методу
        Displayable counter = VowelCounterFactory.createVowelCounter(input);
        counter.display();
    }
}

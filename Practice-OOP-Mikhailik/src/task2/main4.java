package task2;

import java.util.HashMap;
import java.util.Map;

public class main4 {

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

    /**
     * Основний метод для тестування підрахунку голосних літер.
     *
     * @param args аргументи командного рядка
     */
    public static void main(String[] args) {
        String input = "Example string with vowels";

        Map<Character, Integer> result = countVowels(input);

        // Виводимо кількість кожної голосної літери
        for (Map.Entry<Character, Integer> entry : result.entrySet()) {
            System.out.println("Голосна '" + entry.getKey() + "' зустрічається " + entry.getValue() + " разів.");
        }
    }
}

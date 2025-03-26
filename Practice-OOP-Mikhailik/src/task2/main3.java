package task2;

import java.io.*;

/**
 * Клас UserCalculationData зберігає параметри та результати обчислень.
 * Реалізує інтерфейс {@link Serializable} для підтримки серіалізації.
 */
class UserCalculationData implements Serializable {
    private static final long serialVersionUID = 1L;

    private double input1;
    private double input2;
    private double result;

    /**
     * Конструктор для ініціалізації вхідних даних.
     *
     * @param input1 перше число
     * @param input2 друге число
     */
    public UserCalculationData(double input1, double input2) {
        this.input1 = input1;
        this.input2 = input2;
    }

    /**
     * Виконує обчислення та зберігає результат.
     */
    public void compute() {
        this.result = input1 + input2; // Наприклад, сума двох чисел
    }

    /**
     * Повертає результат обчислення.
     *
     * @return значення результату
     */
    public double getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "UserCalculationData{input1=" + input1 + ", input2=" + input2 + ", result=" + result + "}";
    }
}

/**
 * Клас UserCalculationDataTest забезпечує тестування серіалізації, десеріалізації та обчислень.
 */
public class main3 {
    private static final String FILE_NAME = "userdata_calculation_data.ser";

    /**
     * Основний метод для тестування обчислень і серіалізації.
     *
     * @param args аргументи командного рядка (не використовуються)
     */
    public static void main(String[] args) {
        // Створюємо об'єкт із початковими значеннями
        UserCalculationData data = new UserCalculationData(15, 25);
        data.compute(); // Виконуємо обчислення
        System.out.println("До серіалізації: " + data);

        // Серіалізація
        serializeData(data);

        // Десеріалізація
        UserCalculationData loadedData = deserializeData();
        if (loadedData != null) {
            System.out.println("Після десеріалізації: " + loadedData);
            System.out.println("Тест успішний: " + (loadedData.getResult() == data.getResult()));
        }
    }

    /**
     * Серіалізує об'єкт UserCalculationData у файл.
     *
     * @param data об'єкт для збереження
     */
    private static void serializeData(UserCalculationData data) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(data);
            System.out.println("Об'єкт успішно серіалізовано.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Десеріалізує об'єкт UserCalculationData з файлу.
     *
     * @return десеріалізований об'єкт або null у разі помилки
     */
    private static UserCalculationData deserializeData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (UserCalculationData) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

package task2;

import java.io.*;

/**
 * Клас для зберігання параметрів і результатів обчислень.
 */
class CalculationData implements Serializable {
    private static final long serialVersionUID = 1L;

    private double input1;
    private double input2;
    private double result;

    /**
     * Конструктор для ініціалізації вхідних даних.
     *
     * @param input1 Перше число
     * @param input2 Друге число
     */
    public CalculationData(double input1, double input2) {
        this.input1 = input1;
        this.input2 = input2;
    }

    /**
     * Встановлює результат обчислень.
     *
     * @param result Значення результату
     */
    public void setResult(double result) {
        this.result = result;
    }

    /**
     * Повертає результат обчислення.
     *
     * @return значення результату
     */
    public double getResult() {
        return result;
    }

    /**
     * Геттер для input1.
     *
     * @return значення input1
     */
    public double getInput1() {
        return input1;
    }

    /**
     * Геттер для input2.
     *
     * @return значення input2
     */
    public double getInput2() {
        return input2;
    }

    @Override
    public String toString() {
        return "CalculationData{input1=" + input1 + ", input2=" + input2 + ", result=" + result + "}";
    }
}

/**
 * Клас Solver для виконання обчислень.
 */
class Solver {
    private CalculationData data;

    public Solver(CalculationData data) {
        this.data = data;
    }

    /**
     * Виконує обчислення (наприклад, додає input1 + input2).
     */
    public void compute() {
        double result = data.getInput1() + data.getInput2(); // Виправлено формулу
        data.setResult(result);
    }

    public CalculationData getData() {
        return data;
    }
}

/**
 * Основний клас Main.
 */
public class main {
    public static void main(String[] args) {
        CalculationData data = new CalculationData(5, 10);
        Solver solver = new Solver(data);
        solver.compute();

        System.out.println("Результат: " + solver.getData());

        // Серіалізація
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data.ser"))) {
            out.writeObject(data);
            System.out.println("Об'єкт серіалізовано.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Десеріалізація
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.ser"))) {
            CalculationData loadedData = (CalculationData) in.readObject();
            System.out.println("Завантажено: " + loadedData);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

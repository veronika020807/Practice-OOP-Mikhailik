package task7;

import javax.swing.*;
import java.awt.*;
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

public class main2_3 {

    private static final String FILE_NAME = "userdata_calculation_data.ser";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(main2_3::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Серіалізація та обчислення");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 2, 5, 5));

        // Створення компонентів
        JLabel label1 = new JLabel("Число 1:");
        JTextField input1Field = new JTextField();
        JLabel label2 = new JLabel("Число 2:");
        JTextField input2Field = new JTextField();
        JButton computeButton = new JButton("Обчислити");
        JButton saveButton = new JButton("Зберегти");
        JButton loadButton = new JButton("Завантажити");
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);

        computeButton.addActionListener(e -> {
            try {
                double input1 = Double.parseDouble(input1Field.getText());
                double input2 = Double.parseDouble(input2Field.getText());

                UserCalculationData data = new UserCalculationData(input1, input2);
                data.compute(); // Виконуємо обчислення
                resultArea.setText("Результат обчислення: " + data.getResult());
            } catch (NumberFormatException ex) {
                resultArea.setText("Будь ласка, введіть коректні числа.");
            }
        });

        saveButton.addActionListener(e -> {
            try {
                double input1 = Double.parseDouble(input1Field.getText());
                double input2 = Double.parseDouble(input2Field.getText());

                UserCalculationData data = new UserCalculationData(input1, input2);
                data.compute();
                serializeData(data);

                resultArea.setText("Об'єкт успішно збережено у файл.");
            } catch (NumberFormatException ex) {
                resultArea.setText("Будь ласка, введіть коректні числа.");
            }
        });

        loadButton.addActionListener(e -> {
            UserCalculationData loadedData = deserializeData();
            if (loadedData != null) {
                resultArea.setText("Після десеріалізації:\n" + loadedData);
            } else {
                resultArea.setText("Не вдалося завантажити дані.");
            }
        });

        // Додавання компонентів на панель
        frame.add(label1);
        frame.add(input1Field);
        frame.add(label2);
        frame.add(input2Field);
        frame.add(computeButton);
        frame.add(saveButton);
        frame.add(loadButton);
        frame.add(new JScrollPane(resultArea));

        frame.setVisible(true);
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

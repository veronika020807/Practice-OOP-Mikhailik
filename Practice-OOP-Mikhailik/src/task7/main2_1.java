package task7;

import javax.swing.*;
import java.awt.*;
import java.io.*;

// Клас для зберігання параметрів і результатів обчислень
class CalculationData implements Serializable {
    private static final long serialVersionUID = 1L;
    private double input1;
    private double input2;
    private double result;

    // Конструктор для ініціалізації вхідних даних
    public CalculationData(double input1, double input2) {
        this.input1 = input1;
        this.input2 = input2;
    }

    // Встановлює результат обчислень
    public void setResult(double result) {
        this.result = result;
    }

    // Повертає результат обчислення
    public double getResult() {
        return result;
    }

    // Геттер для input1
    public double getInput1() {
        return input1;
    }

    // Геттер для input2
    public double getInput2() {
        return input2;
    }
}

// Клас Solver для виконання обчислень
class Solver {
    private CalculationData data;

    public Solver(CalculationData data) {
        this.data = data;
    }

    // Виконує обчислення (наприклад, додає input1 + input2)
    public void compute() {
        double result = data.getInput1() + data.getInput2();
        data.setResult(result);
    }

    public CalculationData getData() {
        return data;
    }
}

public class main2_1 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(main2_1::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Обчислення та серіалізація");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 2, 5, 5));

        JLabel label1 = new JLabel("Число 1:");
        JTextField field1 = new JTextField();
        JLabel label2 = new JLabel("Число 2:");
        JTextField field2 = new JTextField();
        JButton computeButton = new JButton("Обчислити");
        JButton loadButton = new JButton("Завантажити");
        JLabel resultLabel = new JLabel("Результат:");
        JTextField resultField = new JTextField();
        resultField.setEditable(false);

        // Кнопка обчислення
        computeButton.addActionListener(e -> {
            try {
                double input1 = Double.parseDouble(field1.getText());
                double input2 = Double.parseDouble(field2.getText());

                CalculationData data = new CalculationData(input1, input2);
                Solver solver = new Solver(data);
                solver.compute();

                resultField.setText(String.valueOf(solver.getData().getResult()));

                // Серіалізація
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("data.ser"))) {
                    out.writeObject(data);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Будь ласка, введіть коректні числа!", "Помилка", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Кнопка для завантаження
        loadButton.addActionListener(e -> {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.ser"))) {
                CalculationData loadedData = (CalculationData) in.readObject();
                field1.setText(String.valueOf(loadedData.getInput1()));
                field2.setText(String.valueOf(loadedData.getInput2()));
                resultField.setText(String.valueOf(loadedData.getResult()));
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(frame, "Не вдалося завантажити дані!", "Помилка", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Додавання елементів на панель
        frame.add(label1);
        frame.add(field1);
        frame.add(label2);
        frame.add(field2);
        frame.add(computeButton);
        frame.add(loadButton);
        frame.add(resultLabel);
        frame.add(resultField);

        frame.setVisible(true);
    }
}

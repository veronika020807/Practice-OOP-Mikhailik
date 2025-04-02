package task7;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;

/**
 * Клас UserData, що серіалізується.
 * Поле password є transient і не зберігається при серіалізації.
 */
class UserData implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String username;
    private final int age;
    private transient String password; // transient - не серіалізується

    public UserData(String username, int age, String password) {
        this.username = username;
        this.age = age;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "username='" + username + '\'' +
                ", age=" + age +
                ", password='" + (password != null ? password : "N/A (transient)") + '\'' +
                '}';
    }
}

public class main2_2 {

    private static final String FILE_NAME = "userdata.ser";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(main2_2::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Серіалізація користувача");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 2, 5, 5));

        // Створення компонентів
        JLabel label1 = new JLabel("Ім'я користувача:");
        JTextField usernameField = new JTextField();
        JLabel label2 = new JLabel("Вік:");
        JTextField ageField = new JTextField();
        JLabel label3 = new JLabel("Пароль:");
        JPasswordField passwordField = new JPasswordField();
        JButton saveButton = new JButton("Зберегти");
        JButton loadButton = new JButton("Завантажити");
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);

        saveButton.addActionListener(e -> {
            try {
                String username = usernameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String password = new String(passwordField.getPassword());

                UserData user = new UserData(username, age, password);
                serializeObject(user);

                resultArea.setText("Об'єкт успішно збережено у файл.");
            } catch (NumberFormatException ex) {
                resultArea.setText("Введіть коректний вік.");
            }
        });

        loadButton.addActionListener(e -> {
            UserData loadedUser = deserializeObject();
            if (loadedUser != null) {
                resultArea.setText("Об'єкт після десеріалізації:\n" + loadedUser);
            } else {
                resultArea.setText("Не вдалося завантажити дані.");
            }
        });

        // Додавання компонентів на панель
        frame.add(label1);
        frame.add(usernameField);
        frame.add(label2);
        frame.add(ageField);
        frame.add(label3);
        frame.add(passwordField);
        frame.add(saveButton);
        frame.add(loadButton);
        frame.add(new JScrollPane(resultArea));

        frame.setVisible(true);
    }

    /**
     * Метод серіалізує об'єкт UserData у файл.
     */
    private static void serializeObject(UserData user) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(user);
            System.out.println("Об'єкт успішно збережено у файл.");
        } catch (IOException e) {
            System.err.println("Помилка при серіалізації: " + e.getMessage());
        }
    }

    /**
     * Метод десеріалізує об'єкт UserData з файлу.
     */
    private static UserData deserializeObject() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (UserData) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Помилка при десеріалізації: " + e.getMessage());
            return null;
        }
    }
}

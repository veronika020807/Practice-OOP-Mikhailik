package task2;

import java.io.*;
import java.util.Scanner;

/**
 * Клас UserData, що серіалізується.
 * Поле password є transient і не зберігається при серіалізації.
 */
class UserData implements Serializable {
    @Serial
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

/**
 * Основний клас для демонстрації серіалізації та десеріалізації.
 */
public class main2 {
    private static final String FILE_NAME = "userdata.ser";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Отримуємо дані від користувача
        System.out.print("Введіть ім'я користувача: ");
        String username = scanner.nextLine();

        System.out.print("Введіть вік: ");
        int age = scanner.nextInt();
        scanner.nextLine(); // Очищуємо буфер після nextInt()

        System.out.print("Введіть пароль: ");
        String password = scanner.nextLine();

        // Створюємо об'єкт UserData
        UserData user = new UserData(username, age, password);
        System.out.println("Об'єкт перед серіалізацією: " + user);

        // Виконуємо серіалізацію
        serializeObject(user);

        // Виконуємо десеріалізацію
        UserData loadedUser = deserializeObject();
        if (loadedUser != null) {
            System.out.println("Об'єкт після десеріалізації: " + loadedUser);
        }

        scanner.close();
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

package task5;

import java.util.*;

// Шаблон Singleton для менеджера команд
class CommandManager {
    private static CommandManager instance;
    private Stack<Command> commandStack = new Stack<>();

    private CommandManager() {}

    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    public void executeCommand(Command command) {
        command.execute();
        commandStack.push(command);
    }

    public void undo() {
        if (!commandStack.isEmpty()) {
            Command command = commandStack.pop();
            command.undo();
        } else {
            System.out.println("Немає команд для скасування.");
        }
    }
}

// Інтерфейс команди
interface Command {
    void execute();
    void undo();
}

// Конкретна команда
class PrintCommand implements Command {
    private String message;

    public PrintCommand(String message) {
        this.message = message;
    }

    @Override
    public void execute() {
        System.out.println("Виведено: " + message);
    }

    @Override
    public void undo() {
        System.out.println("Скасовано: " + message);
    }
}

// Макрокоманда (група команд)
class MacroCommand implements Command {
    private List<Command> commands = new ArrayList<>();

    public void addCommand(Command command) {
        commands.add(command);
    }

    @Override
    public void execute() {
        for (Command command : commands) {
            command.execute();
        }
    }

    @Override
    public void undo() {
        Collections.reverse(commands);
        for (Command command : commands) {
            command.undo();
        }
    }
}

// Основний клас
public class main5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CommandManager manager = CommandManager.getInstance();

        while (true) {
            System.out.println("Оберіть команду: 1 - Додати повідомлення, 2 - Скасувати, 3 - Виконати макрокоманду, 4 - Вийти");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            switch (choice) {
                case 1:
                    System.out.print("Введіть повідомлення: ");
                    String message = scanner.nextLine();
                    manager.executeCommand(new PrintCommand(message));
                    break;
                case 2:
                    manager.undo();
                    break;
                case 3:
                    MacroCommand macro = new MacroCommand();
                    macro.addCommand(new PrintCommand("Перше"));
                    macro.addCommand(new PrintCommand("Друге"));
                    macro.addCommand(new PrintCommand("Третє"));
                    manager.executeCommand(macro);
                    break;
                case 4:
                    System.out.println("Вихід...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Невірний вибір.");
            }
        }
    }
}


package task7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

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
        List<Command> reversedCommands = new ArrayList<>(commands);
        Collections.reverse(reversedCommands);
        for (Command command : reversedCommands) {
            command.undo();
        }
    }
}

// Основний клас для GUI
public class main5 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Command Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());

        // Text area for displaying output
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        // CommandManager instance
        CommandManager manager = CommandManager.getInstance();

        // Panel with buttons
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        // Add Message Button
        JButton addMessageButton = new JButton("Додати повідомлення");
        panel.add(addMessageButton);

        // Undo Button
        JButton undoButton = new JButton("Скасувати");
        panel.add(undoButton);

        // Execute Macro Button
        JButton macroButton = new JButton("Виконати макрокоманду");
        panel.add(macroButton);

        // Exit Button
        JButton exitButton = new JButton("Вийти");
        panel.add(exitButton);

        frame.add(panel, BorderLayout.SOUTH);

        // Add action listeners to buttons
        addMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = JOptionPane.showInputDialog(frame, "Введіть повідомлення:");
                if (message != null && !message.trim().isEmpty()) {
                    PrintCommand printCommand = new PrintCommand(message);
                    manager.executeCommand(printCommand);
                    textArea.append("Виведено: " + message + "\n");
                }
            }
        });

        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.undo();
                textArea.append("Скасовано останнє повідомлення\n");
            }
        });

        macroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MacroCommand macro = new MacroCommand();
                macro.addCommand(new PrintCommand("Перше"));
                macro.addCommand(new PrintCommand("Друге"));
                macro.addCommand(new PrintCommand("Третє"));
                manager.executeCommand(macro);
                textArea.append("Виконано макрокоманду: Перше, Друге, Третє\n");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }
}
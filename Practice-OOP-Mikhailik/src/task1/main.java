package task1;

public class main {
    public static void main (String[] args) {
        if (args.length == 0) {
            System.out.println("Аргументи командного рядка не передані.");
        } else {
            System.out.println("Передані аргументи:");
            for (int i = 0; i < args.length; i++) {
                System.out.println((i + 1) + ": " + args[i]);
            }
        }
    }
}

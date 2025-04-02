package task7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class WorkerThread {
    private final Queue<Runnable> taskQueue;
    private final ExecutorService executor;
    private final JTextArea outputArea;

    public WorkerThread(int numThreads, JTextArea outputArea) {
        this.taskQueue = new LinkedList<>();
        this.executor = Executors.newFixedThreadPool(numThreads);
        this.outputArea = outputArea;
    }

    public synchronized void addTask(Runnable task) {
        taskQueue.add(task);
        executor.submit(taskQueue.poll());
    }

    public void shutdown() {
        executor.shutdown();
    }
}

public class main6 {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Multithreading GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        frame.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JButton computeButton = new JButton("Compute Stats");
        JButton executeTasksButton = new JButton("Execute Tasks");
        JButton exitButton = new JButton("Exit");

        panel.add(computeButton);
        panel.add(executeTasksButton);
        panel.add(exitButton);
        frame.add(panel, BorderLayout.SOUTH);

        ExecutorService executor = Executors.newFixedThreadPool(4);

        computeButton.addActionListener((ActionEvent e) -> {
            List<Integer> numbers = IntStream.rangeClosed(1, 1000).boxed().collect(Collectors.toList());

            Future<Integer> minFuture = executor.submit(() -> numbers.parallelStream().min(Integer::compareTo).orElseThrow());
            Future<Integer> maxFuture = executor.submit(() -> numbers.parallelStream().max(Integer::compareTo).orElseThrow());
            Future<Double> avgFuture = executor.submit(() -> numbers.parallelStream().mapToInt(Integer::intValue).average().orElseThrow());
            Future<List<Integer>> filteredFuture = executor.submit(() -> numbers.parallelStream().filter(n -> n % 2 == 0).collect(Collectors.toList()));

            SwingUtilities.invokeLater(() -> {
                try {
                    outputArea.append("Min: " + minFuture.get() + "\n");
                    outputArea.append("Max: " + maxFuture.get() + "\n");
                    outputArea.append("Average: " + avgFuture.get() + "\n");
                    outputArea.append("Filtered (even numbers): " + filteredFuture.get().subList(0, 10) + "...\n");
                } catch (Exception ex) {
                    outputArea.append("Error computing stats\n");
                }
            });
        });

        executeTasksButton.addActionListener((ActionEvent e) -> {
            WorkerThread workerThread = new WorkerThread(3, outputArea);
            for (int i = 0; i < 7; i++) {
                final int taskId = i;
                workerThread.addTask(() -> SwingUtilities.invokeLater(() -> outputArea.append("Executing task " + taskId + "\n")));
            }
            workerThread.shutdown();
        });

        exitButton.addActionListener(e -> {
            executor.shutdown();
            System.exit(0);
        });

        frame.setVisible(true);
    }
}

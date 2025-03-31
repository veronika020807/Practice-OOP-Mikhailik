package task6;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

class WorkerThread implements Runnable {
    private final Queue<Runnable> taskQueue;
    private final ExecutorService executor;

    public WorkerThread(int numThreads) {
        this.taskQueue = new LinkedList<>();
        this.executor = Executors.newFixedThreadPool(numThreads);
    }

    public synchronized void addTask(Runnable task) {
        taskQueue.add(task);
        executor.submit(taskQueue.poll());
    }

    public void shutdown() {
        executor.shutdown();
    }

    @Override
    public void run() {
        while (!taskQueue.isEmpty()) {
            Runnable task;
            synchronized (this) {
                task = taskQueue.poll();
            }
            if (task != null) {
                executor.submit(task);
            }
        }
    }
}

public class main6 {
    public static void main(String[] args) {
        List<Integer> numbers = IntStream.rangeClosed(1, 1000).boxed().collect(Collectors.toList());
        ExecutorService executor = Executors.newFixedThreadPool(4);

        Future<Integer> minFuture = executor.submit(() -> numbers.parallelStream().min(Integer::compareTo).orElseThrow());
        Future<Integer> maxFuture = executor.submit(() -> numbers.parallelStream().max(Integer::compareTo).orElseThrow());
        Future<Double> avgFuture = executor.submit(() -> numbers.parallelStream().mapToInt(Integer::intValue).average().orElseThrow());
        Future<List<Integer>> filteredFuture = executor.submit(() -> numbers.parallelStream().filter(n -> n % 2 == 0).collect(Collectors.toList()));

        try {
            System.out.println("Min: " + minFuture.get());
            System.out.println("Max: " + maxFuture.get());
            System.out.println("Average: " + avgFuture.get());
            System.out.println("Filtered (even numbers): " + filteredFuture.get().subList(0, 10) + "...");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executor.shutdown();

        // Управління чергою завдань
        WorkerThread workerThread = new WorkerThread(3);
        for (int i = 0; i < 7; i++) {
            final int taskId = i;
            workerThread.addTask(() -> System.out.println("Executing task " + taskId));
        }
        workerThread.shutdown();
    }
}


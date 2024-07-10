package executor;

import task.ComplexTask;

import java.util.concurrent.*;

public class ComplexTaskExecutor {
    private final int numberOfTasks;
    private final CyclicBarrier cyclicBarrier;

    public ComplexTaskExecutor(int numberOfTasks) {
        this.numberOfTasks = numberOfTasks;
        this.cyclicBarrier = new CyclicBarrier(numberOfTasks, this::combineResult);
    }

    public void executeTasks(int numberOfTasks) {
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfTasks);
        for (int i = 0; i < numberOfTasks; i++) {
            int taskId = i + 1;
            executorService.submit(() -> {
                try {
                    new ComplexTask(taskId).execute();
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void combineResult() {
        System.out.println("Все закончили выполнение своей задачи");
    }

}

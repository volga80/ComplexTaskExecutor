package task;

import java.util.concurrent.TimeUnit;

public class ComplexTask {
    private final int taskId;

    public ComplexTask(int taskId) {
        this.taskId = taskId;
    }

    public void execute() {
        System.out.println("Поток: " + Thread.currentThread().getName() + " начал выполнение задачи");
        try {
            TimeUnit.SECONDS.sleep((long) (10 * Math.random()));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("задача выполнена потоком: " + Thread.currentThread().getName());
    }
}

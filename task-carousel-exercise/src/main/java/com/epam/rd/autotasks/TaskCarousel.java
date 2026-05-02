package com.epam.rd.autotasks;

public class TaskCarousel {
    private Task[] tasks;
    private int size = 0;
    private int currentIndex = 0;

    public TaskCarousel(int capacity) {
        tasks = new Task[capacity];
    }

    public boolean addTask(Task task) {
        if(task == null || task.isFinished() || isFull()){
            return false;
        }
        tasks[size++] = task;
        return true;
    }

    public boolean execute() {
        if(isEmpty()) return false;

        int cheked = 0;

        while (cheked < size){
            if(currentIndex >= size){
                currentIndex = 0;
            }
            Task task = tasks[currentIndex];
            task.execute();
            if(task.isFinished()){
                for (int i = currentIndex; i < size - 1; i++) {
                    tasks[i] = tasks[i + 1];
                }
                tasks[size - 1] = null;
                size--;
                if(isEmpty()){
                    return true;
                }
            } else currentIndex++;
            return true;
        }
        return false;
    }

    public boolean isFull() {
        return size == tasks.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}

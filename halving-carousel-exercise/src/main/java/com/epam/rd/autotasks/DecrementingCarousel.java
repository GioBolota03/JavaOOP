package com.epam.rd.autotasks;

public class DecrementingCarousel {
    private int[] elements;
    private int size = 0;
    private boolean isRunning = false;

    public DecrementingCarousel(int capacity) {
        elements = new int[capacity];
    }

    public boolean addElement(int element){
        if(isRunning) return false;
        if(element <= 0) return false;
        if(size >= elements.length) return false;

        elements[size++] = element;
        return true;
    }

    public CarouselRun run(){
        if (isRunning) return null;

        isRunning = true;

        int[] runElements = new int[size];
        System.arraycopy(elements, 0, runElements, 0, size);

        return new CarouselRun(runElements);
    }
}

package com.epam.rd.autotasks;

public class CarouselRun {

    protected int[] runElements;
    protected int currentIndex = 0;

    public CarouselRun(int[] runElements) {
        this.runElements = runElements;
    }

    protected void decreaseElement(int index) {
        runElements[index]--;
    }

    public int next() {
        if (isFinished()) return -1;

        int checked = 0;

        while (checked < runElements.length) {

            if (runElements[currentIndex] > 0) {
                int value = runElements[currentIndex];
                decreaseElement(currentIndex);
                currentIndex = (currentIndex + 1) % runElements.length;
                return value;
            }

            currentIndex = (currentIndex + 1) % runElements.length;
            checked++;
        }

        return -1;
    }

    public boolean isFinished() {
        for (int element : runElements) {
            if (element > 0) return false;
        }
        return true;
    }
}
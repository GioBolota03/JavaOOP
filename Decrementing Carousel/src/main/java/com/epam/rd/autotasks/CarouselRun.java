package com.epam.rd.autotasks;

public class CarouselRun {
    private int[] runElements;
    private int currentIndex=0;

    public CarouselRun(int[] runElements) {
        this.runElements = runElements;
    }

    public int next() {
       if(isFinished()) return -1;

       int checkedElements = 0;

       while (checkedElements < runElements.length){
           if(runElements[currentIndex] > 0){
               int value = runElements[currentIndex];
               runElements[currentIndex]--;
               currentIndex = (currentIndex + 1) % runElements.length;
               return value;
           }
           currentIndex = (currentIndex + 1) % runElements.length;
           checkedElements++;
       }
        return -1;
    }

    public boolean isFinished() {
        for(int element : runElements){
            if(element > 0) return false;
        }
        return true;
    }
}

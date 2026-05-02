package com.epam.rd.autotasks;

public class HalvingCarousel extends DecrementingCarousel {

    public HalvingCarousel(int capacity) {
        super(capacity);
    }

    @Override
    public CarouselRun run() {
        CarouselRun baseRun = super.run();
        if (baseRun == null) return null;

        return new CarouselRun(baseRun.runElements) {
            @Override
            protected void decreaseElement(int index) {
                runElements[index] /= 2;
            }
        };
    }
}
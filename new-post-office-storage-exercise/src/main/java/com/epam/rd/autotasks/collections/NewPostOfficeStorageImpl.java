package com.epam.rd.autotasks.collections;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Predicate;

public class NewPostOfficeStorageImpl implements NewPostOfficeStorage {

    private final List<Box> parcels;

    public NewPostOfficeStorageImpl() {
        this.parcels = new LinkedList<>();
    }

    public NewPostOfficeStorageImpl(Collection<Box> boxes) {
        if (boxes == null) {
            throw new NullPointerException();
        }
        this.parcels = new LinkedList<>();
        for (Box box : boxes) {
            if (box == null) {
                throw new NullPointerException();
            }
            this.parcels.add(box);
        }
    }

    @Override
    public boolean acceptBox(Box box) {
        if (box == null) {
            throw new NullPointerException();
        }
        return parcels.add(box);
    }

    @Override
    public boolean acceptAllBoxes(Collection<Box> boxes) {
        if (boxes == null) {
            throw new NullPointerException();
        }
        for (Box b : boxes) {
            if (b == null) {
                throw new NullPointerException();
            }
        }

        boolean modified = false;
        for (Box b : boxes) {
            if (parcels.add(b)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean carryOutBoxes(Collection<Box> boxes) {
        if (boxes == null) {
            throw new NullPointerException();
        }
        for (Box b : boxes) {
            if (b == null) {
                throw new NullPointerException();
            }
        }
        boolean changed = false;
        for (Box boxToRemove : boxes) {
            Iterator<Box> iterator = parcels.iterator();
            while (iterator.hasNext()) {
                if (iterator.next().equals(boxToRemove)) {
                    iterator.remove();
                    changed = true;
                    break;
                }
            }
        }
        return changed;
    }

    @Override
    public List<Box> carryOutBoxes(Predicate<Box> predicate) {
        if (predicate == null) {
            throw new NullPointerException();
        }
        List<Box> removedList = new LinkedList<>();
        Iterator<Box> iterator = parcels.iterator();
        while (iterator.hasNext()) {
            Box box = iterator.next();
            if (predicate.test(box)) {
                removedList.add(box);
                iterator.remove();
            }
        }
        return removedList;
    }

    @Override
    public List<Box> getAllWeightLessThan(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException();
        }
        return searchBoxes(new Predicate<Box>() {
            @Override
            public boolean test(Box box) {
                return box.getWeight() < weight;
            }
        });
    }

    @Override
    public List<Box> getAllCostGreaterThan(BigDecimal cost) {
        if (cost == null) {
            throw new NullPointerException();
        }
        if (cost.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        return searchBoxes(new Predicate<Box>() {
            @Override
            public boolean test(Box box) {
                return box.getCost().compareTo(cost) > 0;
            }
        });
    }

    @Override
    public List<Box> getAllVolumeGreaterOrEqual(double volume) {
        if (volume < 0) {
            throw new IllegalArgumentException();
        }
        return searchBoxes(new Predicate<Box>() {
            @Override
            public boolean test(Box box) {
                return box.getVolume() >= volume;
            }
        });
    }

    @Override
    public List<Box> searchBoxes(Predicate<Box> predicate) {
        if (predicate == null) {
            throw new NullPointerException();
        }
        List<Box> result = new LinkedList<>();
        for (Box box : parcels) {
            if (predicate.test(box)) {
                result.add(box);
            }
        }
        return result;
    }

    @Override
    public void updateOfficeNumber(Predicate<Box> predicate, int newOfficeNumber) {
        if (predicate == null) {
            throw new NullPointerException();
        }
        if (newOfficeNumber <= 0) {
            throw new IllegalArgumentException();
        }
        for (Box box : parcels) {
            if (predicate.test(box)) {
                box.setOfficeNumber(newOfficeNumber);
            }
        }
    }
}
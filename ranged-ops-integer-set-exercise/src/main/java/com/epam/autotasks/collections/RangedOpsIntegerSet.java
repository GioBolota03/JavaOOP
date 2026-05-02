package com.epam.autotasks.collections;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.TreeSet;

class RangedOpsIntegerSet extends AbstractSet<Integer> {

    private final TreeSet<Integer> set = new TreeSet<>();

    public boolean add(int fromInclusive, int toExclusive) {
        boolean changed = false;

        if (fromInclusive >= toExclusive) {
            return false;
        }

        for (int i = fromInclusive; i < toExclusive; i++) {
            if (set.add(i)) {
                changed = true;
            }
        }

        return changed;
    }

    public boolean remove(int fromInclusive, int toExclusive) {
        boolean changed = false;

        if (fromInclusive >= toExclusive) {
            return false;
        }

        for (int i = fromInclusive; i < toExclusive; i++) {
            if (set.remove(i)) {
                changed = true;
            }
        }

        return changed;
    }

    @Override
    public boolean add(final Integer integer) {
        if (integer == null) {
            throw new NullPointerException();
        }
        return set.add(integer);
    }

    @Override
    public boolean remove(final Object o) {
        if (o == null) {
            return false;
        }
        return set.remove(o);
    }

    @Override
    public Iterator<Integer> iterator() {
        return set.iterator();
    }

    @Override
    public int size() {
        return set.size();
    }
}
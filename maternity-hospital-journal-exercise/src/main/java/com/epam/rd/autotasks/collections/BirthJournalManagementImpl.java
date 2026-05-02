package com.epam.rd.autotasks.collections;

import java.util.*;

public class BirthJournalManagementImpl implements BirthJournalManagement {

    private Map<WeekDay, List<Baby>> journal = new EnumMap<>(WeekDay.class);
    private boolean committed = false;

    @Override
    public boolean addEntryOfBaby(WeekDay day, Baby baby) {
        if (committed) {
            return false;
        }

        List<Baby> list = journal.get(day);
        if (list == null) {
            list = new ArrayList<>();
            journal.put(day, list);
        }

        list.add(baby);
        return true;
    }

    @Override
    public void commit() {
        if (committed) return;

        for (Map.Entry<WeekDay, List<Baby>> entry : journal.entrySet()) {
            entry.setValue(Collections.unmodifiableList(entry.getValue()));
        }

        journal = Collections.unmodifiableMap(journal);
        committed = true;
    }

    @Override
    public int amountBabies() {
        int count = 0;

        for (List<Baby> list : journal.values()) {
            count += list.size();
        }

        return count;
    }

    @Override
    public List<Baby> findBabyWithHighestWeight(String gender) {
        double maxWeight = -1;
        List<Baby> result = new ArrayList<>();

        for (List<Baby> list : journal.values()) {
            for (Baby b : list) {
                if (b.getGender().equals(gender)) {
                    if (b.getWeight() > maxWeight) {
                        maxWeight = b.getWeight();
                        result.clear();
                        result.add(b);
                    } else if (b.getWeight() == maxWeight) {
                        result.add(b);
                    }
                }
            }
        }

        Collections.sort(result, new Comparator<Baby>() {
            @Override
            public int compare(Baby b1, Baby b2) {
                return b1.getName().compareTo(b2.getName());
            }
        });

        return Collections.unmodifiableList(result);
    }

    @Override
    public List<Baby> findBabyWithSmallestHeight(String gender) {
        int minHeight = Integer.MAX_VALUE;
        List<Baby> result = new ArrayList<>();

        for (List<Baby> list : journal.values()) {
            for (Baby b : list) {
                if (b.getGender().equals(gender)) {
                    if (b.getHeight() < minHeight) {
                        minHeight = b.getHeight();
                        result.clear();
                        result.add(b);
                    } else if (b.getHeight() == minHeight) {
                        result.add(b);
                    }
                }
            }
        }

        Collections.sort(result, new Comparator<Baby>() {
            @Override
            public int compare(Baby b1, Baby b2) {
                return Double.compare(b1.getWeight(), b2.getWeight());
            }
        });

        return Collections.unmodifiableList(result);
    }

    @Override
    public Set<Baby> findBabiesByBirthTime(String from, String to) {
        int fromMin = toMinutes(from);
        int toMin = toMinutes(to);

        Set<Baby> result = new HashSet<>();

        for (List<Baby> list : journal.values()) {
            for (Baby b : list) {
                int time = toMinutes(b.getTime());

                if (fromMin <= toMin) {
                    if (time >= fromMin && time <= toMin) {
                        result.add(b);
                    }
                } else {
                    if (time >= fromMin || time <= toMin) {
                        result.add(b);
                    }
                }
            }
        }

        return result;
    }

    private int toMinutes(String time) {
        String[] parts = time.split(":");
        int h = Integer.parseInt(parts[0]);
        int m = Integer.parseInt(parts[1]);
        return h * 60 + m;
    }
}
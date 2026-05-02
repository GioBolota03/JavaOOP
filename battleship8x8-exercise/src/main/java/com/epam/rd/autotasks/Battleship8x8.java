package com.epam.rd.autotasks;

public class Battleship8x8 {
    private final long ships;
    private long shots = 0L;

    public Battleship8x8(final long ships) {
        this.ships = ships;
    }

    private int getBitIndex(String shot) {
        int col = shot.charAt(0) - 'A';
        int row = shot.charAt(1) - '1';

        return row * 8 + col;
    }

    public boolean shoot(String shot) {
        int index = getBitIndex(shot);

        long mask = 1L << (63 - index);

        shots |= mask;

        return (ships & mask) != 0;
    }

    public String state() {
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                int index = row * 8 + col;
                long mask = 1L << (63 - index);

                boolean hasShip = (ships & mask) != 0;
                boolean wasShot = (shots & mask) != 0;

                if (hasShip && wasShot) {
                    sb.append('☒');
                } else if (hasShip) {
                    sb.append('☐');
                } else if (wasShot) {
                    sb.append('×');
                } else {
                    sb.append('.');
                }
            }
            if (row < 7) {
                sb.append("\n");
            }
        }

        return sb.toString();
    }
}
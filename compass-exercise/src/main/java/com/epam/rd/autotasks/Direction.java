package com.epam.rd.autotasks;

import java.util.Optional;

public enum Direction {
    N(0), NE(45), E(90), SE(135), S(180), SW(225), W(270), NW(315);

    private int degrees;

    Direction(final int degrees) {
        this.degrees = degrees;
    }
//    private int degrees;

    private static int normalize(int degrees) {
        int result = degrees % 360;
        if (result < 0) {
            result += 360;
        }
        return result;
    }

    public static Direction ofDegrees(int degrees) {
        int normalized = normalize(degrees);

        for (Direction d : values()) {
            if (d.degrees == normalized) {
                return d;
            }
        }
        return null;
    }

    public static Direction closestToDegrees(int degrees) {
        int normalized = normalize(degrees);

        Direction closest = null;
        int mindifferance = Integer.MAX_VALUE;
        for (Direction d : values()) {
            int differance = Math.abs(d.degrees - normalized);
            differance = Math.min(differance, 360 - differance);

            if (differance < mindifferance) {
                mindifferance = differance;
                closest = d;
            }
        }
        return closest;
    }

    public Direction opposite() {
        int oppositeDegrees = (this.degrees + 180) % 360;
        return ofDegrees(oppositeDegrees);
    }

    public int differenceDegreesTo(Direction direction) {
        int diff = Math.abs(this.degrees - direction.degrees);
        return Math.min(diff, 360 - diff);
    }
}

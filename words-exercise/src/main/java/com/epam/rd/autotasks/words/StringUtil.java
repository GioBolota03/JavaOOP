package com.epam.rd.autotasks.words;

import java.util.Arrays;
import java.util.StringJoiner;

public class StringUtil {
    public static int countEqualIgnoreCaseAndSpaces(String[] words, String sample) {
        if (words == null || words.length == 0 || sample == null) {
            return 0;
        }

        int count = 0;
        String normalizedSample = sample.strip();

        for (String word : words) {
            if (word.strip().equalsIgnoreCase(normalizedSample)) {
                count++;
            }
        }
        return count;
    }

    public static String[] splitWords(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }

        String[] parts = text.split("[,.;: ?!]+");

        if (parts.length == 0) {
            return null;
        }

        parts = Arrays.stream(parts)
                .filter(s -> !s.isBlank())
                .toArray(String[]::new);

        return parts.length == 0 ? null : parts;
    }

    public static String convertPath(String path, boolean toWin) {
        if (path == null || path.isEmpty()) {
            return null;
        }

        if (path.contains("/") && path.contains("\\")) {
            return null;
        }

        if (path.indexOf("~") > 0) return null;
        if (path.indexOf("C:") > 0) return null;
        if (path.contains("~~")) return null;
        if (path.contains("~/~")) return null;
        if (path.contains("C:\\") && path.substring(3).contains("C:\\")) return null;
        if (path.startsWith("~") && path.contains("\\")) return null;
        if (path.startsWith("C:") && path.contains("/")) return null;
        if (path.contains("/~/")) return null;

        boolean isUnix = path.contains("/") || path.startsWith("~") || path.equals("~");
        boolean isWin = path.contains("\\") || path.startsWith("C:") || path.equals("C:");

        if (!isUnix && !isWin) {
            return path;
        }

        if (toWin && isWin) return path;
        if (!toWin && isUnix) return path;

        if (toWin) {
            if (path.equals("~")) return "C:\\User";
            if (path.equals("/")) return "C:\\";

            String result = path;

            if (result.startsWith("~/")) {
                result = result.replaceFirst("~", "C:\\\\User");
            } else if (result.startsWith("~")) {
                result = result.replaceFirst("~", "C:\\\\User");
            } else if (result.startsWith("/")) {
                result = result.replaceFirst("/", "C:\\\\");
            }

            return result.replace("/", "\\");
        } else {
            if (path.equals("C:\\User")) return "~";
            if (path.equals("C:\\")) return "/";

            String result = path;

            if (result.startsWith("C:\\User\\")) {
                result = result.replaceFirst("C:\\\\User", "~");
            } else if (result.startsWith("C:\\User")) {
                result = result.replaceFirst("C:\\\\User", "~");
            } else if (result.startsWith("C:\\")) {
                result = result.replaceFirst("C:\\\\", "/");
            }

            return result.replace("\\", "/");
        }
    }

    public static String joinWords(String[] words) {
        if (words == null || words.length == 0) {
            return null;
        }

        StringJoiner joiner = new StringJoiner(", ", "[", "]");

        for (String word : words) {
            if (!word.isEmpty()) {
                joiner.add(word);
            }
        }

        String result = joiner.toString();
        return result.equals("[]") ? null : result;
    }

    public static void main(String[] args) {
        System.out.println("Test 1: countEqualIgnoreCaseAndSpaces");
        String[] words = new String[]{" WordS    \t", "words", "w0rds", "WOR  DS", };
        String sample = "words   ";
        int countResult = countEqualIgnoreCaseAndSpaces(words, sample);
        System.out.println("Result: " + countResult);
        int expectedCount = 2;
        System.out.println("Must be: " + expectedCount);

        System.out.println("Test 2: splitWords");
        String text = "   ,, first, second!!!! third";
        String[] splitResult = splitWords(text);
        System.out.println("Result : " + Arrays.toString(splitResult));
        String[] expectedSplit = new String[]{"first", "second", "third"};
        System.out.println("Must be: " + Arrays.toString(expectedSplit));

        System.out.println("Test 3: convertPath");
        String unixPath = "/some/unix/path";
        String convertResult = convertPath(unixPath, true);
        System.out.println("Result: " + convertResult);
        String expectedWinPath = "C:\\some\\unix\\path";
        System.out.println("Must be: " + expectedWinPath);

        System.out.println("Test 4: joinWords");
        String[] toJoin = new String[]{"go", "with", "the", "", "FLOW"};
        String joinResult = joinWords(toJoin);
        System.out.println("Result: " + joinResult);
        String expectedJoin = "[go, with, the, FLOW]";
        System.out.println("Must be: " + expectedJoin);
    }
}
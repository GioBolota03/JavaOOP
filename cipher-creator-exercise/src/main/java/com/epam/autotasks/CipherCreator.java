package com.epam.autotasks;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CipherCreator {

    private CipherCreator() {
    }

    public static StringBuilder cipherText(File input) {
        if (input == null || !input.exists() || input.isDirectory() || !input.canRead()) {
            throw new IllegalArgumentException();
        }

        StringBuilder result = new StringBuilder();

        try (TransformerInputStream in = new TransformerInputStream(new FileInputStream(input))) {

            int ch;
            while ((ch = in.read()) != -1) {
                result.append((char) ch);
            }

        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

        return result;
    }
}
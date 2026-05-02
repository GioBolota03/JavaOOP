package com.epam.autotasks;

import java.io.IOException;
import java.io.InputStream;

public class TransformerInputStream extends InputStream {

    private final InputStream in;

    public TransformerInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read() throws IOException {
        int ch = in.read();

        if (ch == -1) return -1;

        if (ch >= 'A' && ch <= 'Z') {
            return 'A' + (ch - 'A' + 2) % 26;
        }

        if (ch >= 'a' && ch <= 'z') {
            return 'a' + (ch - 'a' + 2) % 26;
        }

        if (ch == '\n' || ch == '\r') {
            return ch;
        }

        return read();
    }

    @Override
    public void close() throws IOException {
        in.close();
        System.out.println("Stream closed");
    }
}
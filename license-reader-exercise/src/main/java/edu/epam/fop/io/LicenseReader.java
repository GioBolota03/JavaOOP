package edu.epam.fop.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LicenseReader {

  public void collectLicenses(File root, File outputFile) {
    if (root == null || outputFile == null) {
      throw new IllegalArgumentException();
    }

    if (!root.exists() || !root.canRead()) {
      throw new IllegalArgumentException();
    }

    if (root.isDirectory() && !root.canExecute()) {
      throw new IllegalArgumentException();
    }

    List<String> result = new ArrayList<>();
    process(root, result);

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, false))) {
      for (String line : result) {
        writer.write(line);
        writer.newLine();
      }
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }

  private void process(File file, List<String> result) {
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      if (files == null) return;

      for (File f : files) {
        process(f, result);
      }
    } else {
      processFile(file, result);
    }
  }

  private void processFile(File file, List<String> result) {
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

      String line = reader.readLine();
      if (line == null) return;

      if (!line.equals("---")) return;

      String license = null;
      String issuer = null;
      String issuedOn = null;
      String expiresOn = null;

      boolean closed = false;

      while ((line = reader.readLine()) != null) {
        if (line.equals("---")) {
          closed = true;
          break;
        }

        if (line.startsWith("License:")) {
          license = line.substring(8).trim();
        } else if (line.startsWith("Issued by:")) {
          issuer = line.substring(10).trim();
        } else if (line.startsWith("Issued on:")) {
          issuedOn = line.substring(10).trim();
        } else if (line.startsWith("Expires on:")) {
          expiresOn = line.substring(11).trim();
        }
      }

      if (!closed || license == null || issuer == null || issuedOn == null) {
        throw new IllegalArgumentException();
      }

      String exp = (expiresOn == null) ? "unlimited" : expiresOn;

      result.add("License for " + file.getName()
              + " is " + license
              + " issued by " + issuer
              + " [" + issuedOn + " - " + exp + "]");

    } catch (IOException e) {
      throw new IllegalArgumentException();
    }
  }
}
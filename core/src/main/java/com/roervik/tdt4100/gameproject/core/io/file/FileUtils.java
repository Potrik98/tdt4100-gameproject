package com.roervik.tdt4100.gameproject.core.io.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static String readInputStream(final InputStream inputStream) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        final StringBuilder out = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            out.append(line);
            out.append("\n");
        }
        return out.toString();
    }

    public static List<String> readAllLines(final String resourcePath)  {
        return readAllLines(ClassLoader.getSystemClassLoader()
                .getResourceAsStream(resourcePath));
    }

    public static List<String> readAllLines(final InputStream inputStream) {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to read resource " + inputStream.toString());
        }
        return list;
    }
}

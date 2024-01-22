package dev.jotxee.file;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FileLoader {
    public static String load(String path) {
        return new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(FileLoader.class.getResourceAsStream(path)), StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }
    public static List<String> loadAsList(String path) {
        return new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(FileLoader.class.getResourceAsStream(path)), StandardCharsets.UTF_8))
                .lines()
                .toList();
    }
}

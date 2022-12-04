package io.github.frellibb;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class InputUtils {

    @SneakyThrows
    public static List<String> loadLines(final String filePath) {
        try (InputStream is = InputUtils.class.getClassLoader().getResourceAsStream(filePath)) {
            return new BufferedReader(new InputStreamReader(requireNonNull(is))).lines().toList();
        }
    }
}

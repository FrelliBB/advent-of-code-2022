package io.github.frellibb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

public class InputUtils {

    public static <T> T processFileAsLines(String filePath, Function<List<String>, T> function) throws IOException {
        try (InputStream is = InputUtils.class.getClassLoader().getResourceAsStream(filePath)) {
            final List<String> lines = new BufferedReader(new InputStreamReader(requireNonNull(is))).lines().toList();
            return function.apply(lines);
        }
    }

}

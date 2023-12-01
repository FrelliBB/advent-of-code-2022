package io.github.frellibb.adventofcode.y2023;

import io.github.frellibb.adventofcode.core.utils.InputUtils;
import io.github.frellibb.adventofcode.core.Day;
import org.apache.commons.lang3.StringUtils;

public class AdventOfCode2023 {

    public static void main(String[] args) throws Exception {

        for (int i = 1; i <= 25; i++) {
            final var dayString = StringUtils.leftPad(Integer.toString(i), 2, "0");
            final var className = AdventOfCode2023.class.getPackageName() + ".Day" + dayString;
            try {
                final Class<?> clazz = Class.forName(className);
                final Day dayInstance = (Day) clazz.getDeclaredConstructor().newInstance();

                final var lines = InputUtils.loadLines("2023/day%s.txt".formatted(dayString));

                System.out.println(clazz.getSimpleName());
                System.out.println(dayInstance.process(lines).print());
            } catch (ClassNotFoundException ex) {
                System.out.printf("Skipping %s due to ClassNotFoundException%n", className);
            }
        }
    }
}

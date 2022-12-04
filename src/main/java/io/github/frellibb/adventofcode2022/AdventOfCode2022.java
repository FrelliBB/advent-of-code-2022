package io.github.frellibb.adventofcode2022;

import io.github.frellibb.InputUtils;
import io.github.frellibb.core.Day;
import org.apache.commons.lang3.StringUtils;

public class AdventOfCode2022 {

    private static final int CURRENT_DAY = 4;

    public static void main(String[] args) throws Exception {

        for (int i = 1; i <= CURRENT_DAY; i++) {
            final var dayString = StringUtils.leftPad(Integer.toString(i), 2, "0");
            final Class<?> clazz = Class.forName(AdventOfCode2022.class.getPackageName() + ".Day" + dayString);
            final Day dayInstance = (Day) clazz.getDeclaredConstructor().newInstance();

            final var lines = InputUtils.loadLines("day%s.txt".formatted(dayString));

            System.out.println(clazz.getName());
            System.out.println(dayInstance.process(lines).print());
        }
    }
}

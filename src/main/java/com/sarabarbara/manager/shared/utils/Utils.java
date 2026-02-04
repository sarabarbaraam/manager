package com.sarabarbara.manager.shared.utils;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * Utils class.
 *
 * @author sarabarbaraam
 * @version 1.0
 * @since 03/02/2026
 */

@Component
public class Utils {

    @Contract(pure = true)
    public static @NotNull String toRoman(int number) {

        return switch (number) {
            case 1 -> "i";
            case 2 -> "ii";
            case 3 -> "iii";
            case 4 -> "iv";
            case 5 -> "v";
            case 6 -> "vi";
            case 7 -> "vii";
            case 8 -> "viii";
            case 9 -> "ix";
            case 10 -> "x";
            default -> String.valueOf(number);
        };
    }

    @Contract(pure = true)
    public static @NotNull String numberToWord(int number) {

        return switch (number) {
            case 1 -> "one";
            case 2 -> "two";
            case 3 -> "three";
            case 4 -> "four";
            case 5 -> "five";
            case 6 -> "six";
            case 7 -> "seven";
            case 8 -> "eight";
            case 9 -> "nine";
            case 10 -> "ten";
            default -> "";
        };
    }

}

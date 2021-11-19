package com.fpt.validation;

import java.util.function.Predicate;

/**
 * Validate the value of cookie.
 */
public class StringValidation {

    public static Predicate<String> predicate = s -> {
        return "id".equals(s);
    };

    public static Predicate<String> predicate2 = s -> {
        return "cart".equals(s);
    };

    public static boolean validationString(String input, Predicate<String> predicate) {
        return input != null && input.length() > 0 && predicate.test(input);
    }

    public static boolean validationString(String input) {
        return input != null && input.length() > 0;
    }

    public static boolean validationString(String input1, Predicate<String> predicate, String input2) {
        return validationString(input1, predicate) && validationString(input2);
    }

}

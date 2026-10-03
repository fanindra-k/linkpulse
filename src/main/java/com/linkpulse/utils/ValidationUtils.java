package com.linkpulse.utils;

public class ValidationUtils {

    public static String normalizeUrl(String value) {
        if (value == null) {
            return null;
        }

        return value
                .trim()
                .replaceAll("[\\p{Cntrl}&&[^\\r\\n\\t]]+", "")
                .replaceAll("\\s+", "");
    }

    public static String normalizeText(String value) {
        if (value == null) {
            return null;
        }

        return value
                .replaceAll("[\\p{Cntrl}&&[^\\r\\n\\t]]+", " ")
                .trim()
                .replaceAll("\\s+", " ");
    }

    public static String normalizeEmail(String value) {
        if (value == null) {
            return null;
        }

        return value
                .trim()
                .toLowerCase()
                .replaceAll("[\\p{Cntrl}&&[^\\r\\n\\t]]+", "")
                .replaceAll("\\s+", "");
    }
}

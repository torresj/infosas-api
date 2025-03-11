package com.torresj.infosas.utils;

public class StringUtils {
    public static String obfuscateDni(String dni) {
        // Check for null or empty dni
        if (dni == null || dni.isEmpty()) {
            return "";
        }

        int length = dni.length();

        // If string is 5 or fewer characters, can't apply the masking rule
        if (length <= 5) {
            return "";
        }

        // Create a StringBuilder for efficient string manipulation
        StringBuilder obfuscated = new StringBuilder();

        // Add three asterisks at the beginning
        obfuscated.append("***");

        // Add the middle part (unchanged)
        obfuscated.append(dni.substring(3, length - 2));

        // Add two asterisks at the end
        obfuscated.append("**");

        return obfuscated.toString();
    }
}

package org.gyt.web.api.utils;

import java.util.Random;

public class StringGeneratorUtils {

    private static final int MAX_LENGTH = 1024;

    private static final char[] LOWER_CASE_LETTER = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final char[] UPPER_CASE_LETTER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] NUMBERS = "0123456789".toCharArray();
    private static final char[] LETTER_NUMBER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private static final char[] ALL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*_-".toCharArray();

    private StringGeneratorUtils() {
    }

    public static int DEFAULT_LENGTH = 10;

    public static String nextLetter() {
        return nextString(DEFAULT_LENGTH, DEFAULT_LENGTH, LETTERS);
    }

    public static String nextLetter(int minLength, int maxLength) {
        return nextString(minLength, maxLength, LETTERS);
    }

    public static String nextLowerLetter() {
        return nextString(DEFAULT_LENGTH, DEFAULT_LENGTH, LOWER_CASE_LETTER);
    }

    public static String nextLowerLetter(int minLength, int maxLength) {
        return nextString(minLength, maxLength, LOWER_CASE_LETTER);
    }

    public static String nextUpperLetter() {
        return nextString(DEFAULT_LENGTH, DEFAULT_LENGTH, UPPER_CASE_LETTER);
    }

    public static String nextUpperLetter(int minLength, int maxLength) {
        return nextString(minLength, maxLength, UPPER_CASE_LETTER);
    }

    public static String nextNumberString() {
        return nextString(DEFAULT_LENGTH, DEFAULT_LENGTH, NUMBERS);
    }

    public static String nextNumberString(int minLength, int maxLength) {
        return nextString(minLength, maxLength, NUMBERS);
    }

    public static String nextNumberLetterString() {
        return nextString(DEFAULT_LENGTH, DEFAULT_LENGTH, LETTER_NUMBER);
    }

    public static String nextNumberLetterString(int minLength, int maxLength) {
        return nextString(minLength, maxLength, LETTER_NUMBER);
    }

    public static String nextString() {
        return nextString(DEFAULT_LENGTH, DEFAULT_LENGTH);
    }

    public static String nextString(int minLength, int maxLength) {
        return nextString(minLength, maxLength, ALL);
    }

    private static String nextString(int minLength, int maxLength, char[] chars) {
        int finalLength = getFinalLength(minLength, maxLength);

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < finalLength; i++) {
            stringBuilder.append(nextChar(chars));
        }

        return stringBuilder.toString();
    }

    private static int getFinalLength(int minLength, int maxLength) {
        int finalLength;

        if (minLength <= 0 || maxLength > MAX_LENGTH) {
            return 0;
        }

        if (minLength < maxLength) {
            finalLength = new Random().nextInt(maxLength - minLength + 1) + minLength;
        } else if (minLength == maxLength) {
            finalLength = minLength;
        } else {
            finalLength = 0;
        }
        return finalLength;
    }

    private static char nextChar(char[] chars) {
        return chars[new Random().nextInt(chars.length)];
    }
}

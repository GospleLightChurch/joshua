package org.gyt.web.api.utils;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by y27chen on 2016/8/23.
 */
public class LoginUtils {
    private LoginUtils() {
    }

    private static final char[] ACCEPT_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_".toCharArray();

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 20;

    public static boolean isValidUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            return false;
        }

        if (username.length() < MIN_LENGTH || username.length() > MAX_LENGTH) {
            return false;
        }

        if (!CharUtils.isAsciiAlpha(username.charAt(0))) {
            return false;
        }

        for (int i = 1; i < username.length(); i++) {
            char c = username.charAt(i);
            if (charNotExist(c)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isValidNickname(String nickname) {
        return !StringUtils.isEmpty(nickname) && nickname.length() <= MAX_LENGTH;
    }

    public static boolean isValidPassword(String password) {
        if (StringUtils.isEmpty(password)) {
            return false;
        }

        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            return false;
        }

        for (int i = 1; i < password.length(); i++) {
            char c = password.charAt(i);
            if (charNotExist(c)) {
                return false;
            }
        }

        return true;
    }

    private static boolean charNotExist(char c) {
        for (char ACCEPT_CHAR : ACCEPT_CHARS) {
            if (c == ACCEPT_CHAR) {
                return false;
            }
        }
        return true;
    }
}

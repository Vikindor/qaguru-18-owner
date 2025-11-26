package io.github.vikindor.extensions;

public class AuthContext {

    private static final ThreadLocal<String> TOKEN = new ThreadLocal<>();
    private static final ThreadLocal<String> USER_ID = new ThreadLocal<>();
    private static final ThreadLocal<String> EXPIRES = new ThreadLocal<>();

    public static void set(String token, String userId, String expires) {
        TOKEN.set(token);
        USER_ID.set(userId);
        EXPIRES.set(expires);
    }

    public static String getToken() {
        return TOKEN.get();
    }

    public static String getUserId() {
        return USER_ID.get();
    }

    public static String getExpires() {
        return EXPIRES.get();
    }

    public static void clear() {
        TOKEN.remove();
        USER_ID.remove();
        EXPIRES.remove();
    }
}

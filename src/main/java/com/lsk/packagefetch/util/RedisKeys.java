package com.lsk.packagefetch.util;

public enum RedisKeys {
    USERINFO("%d-USERINFO"), UID("%s-UID"), CODE_CONTENT("%s-CODE-CONTENT");

    private final String keyBody;
    RedisKeys(String keyBody) {
        this.keyBody = keyBody;
    }
    public String of(int i) {
        return String.format(keyBody, i);
    }
    public String of(String s) { return String.format(keyBody, s); }
}

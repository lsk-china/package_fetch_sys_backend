package com.lsk.packagefetch.util;

public class StatusCode extends RuntimeException{
    private int code;

    public int getCode() {
        return code;
    }

    public StatusCode(int code, String message) {
        super(message);
        this.code = code;
    }

    public StatusCode(int code, String message, Throwable t) {
        super(message, t);
        this.code = code;
    }

    public StatusCode(int code, Throwable t) {
        super(t);
        this.code = code;
    }
}

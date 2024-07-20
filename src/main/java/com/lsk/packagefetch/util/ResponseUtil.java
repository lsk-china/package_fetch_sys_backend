package com.lsk.packagefetch.util;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ResponseUtil {
    private static final Gson gson = new Gson();
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Response {
        private Integer code;
        private String message;
        private Object data;
        public String build() {
            return gson.toJson(this);
        }
    }

    public static Response ok(Object data) {
        return new Response(200, "Success", data);
    }

    public static Response ok() {
        return ok(new Object()); // data is always there
    }

    public static Response error(int code, String message) {
        return new Response(code, message, new Object());
    }

    public static Response error(Throwable t) {
        if (t.getCause() instanceof StatusCode) {
            StatusCode statusCode = (StatusCode) t.getCause();
            return new Response(
                    statusCode.getCode(),
                    statusCode.getMessage(),
                    statusCode.getCause() == null ? new Object() : statusCode.getCause()
            );
        } else {
            log.error("Caught unknown error", t);
            return new Response(500, "Unknown server error", t);
        }
    }
}

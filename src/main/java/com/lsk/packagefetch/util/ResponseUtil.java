package com.lsk.packagefetch.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public final class ResponseUtil {
    private static final Gson gson = GsonUtil.getGson();
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Response {
        private Integer code;
        private String message;
        private Object data;
        public String build() {
            if (data != null && data instanceof Page) {
                Map<String, Object> map = new HashMap<>();
                map.put("total", ((Page) data).getPages());
                map.put("current", ((Page) data).getCurrent());
                map.put("paged", ((Page) data).getRecords());
                this.data = map;
            }
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

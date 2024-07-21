package com.lsk.packagefetch.util;

import com.google.gson.*;
import com.google.gson.internal.bind.DateTypeAdapter;

import java.lang.reflect.Type;
import java.util.Date;

public final class GsonUtil {
    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
                    @Override
                    public JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
                        return date == null ? null : new JsonPrimitive(DateUtil.dateToString(date));
                    }
                })
                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    @Override
                    public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                        return jsonElement == null ? null : DateUtil.stringToDate(jsonElement.getAsString());
                    }
                })
                .create();
    }
}

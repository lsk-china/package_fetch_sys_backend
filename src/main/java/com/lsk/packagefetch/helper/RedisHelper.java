package com.lsk.packagefetch.helper;

import com.google.gson.Gson;
import com.lsk.packagefetch.model.User;
import com.lsk.packagefetch.util.GsonUtil;
import com.lsk.packagefetch.util.RedisKeys;
import com.lsk.packagefetch.util.StatusCode;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;

@Component
public class RedisHelper {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private static final Gson gson = GsonUtil.getGson();

    private void set(String key, Object val) {
        redisTemplate.opsForValue().set(key, gson.toJson(val));
    }

    private <T> T get(String key, Class<T> typeOfT) {
        String json = redisTemplate.opsForValue().get(key);
        return gson.fromJson(json, typeOfT);
    }

    public void delete(String key) {
        redisTemplate.expire(key, Duration.ZERO); // expire now == delete
    }

    public boolean has(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void cacheUser(User user) {
        set(RedisKeys.USERINFO.of(user.getId()), user);
    }

    public User queryCachedUser(Integer uid) {
        if (! has(RedisKeys.USERINFO.of(uid))) {
            return null;
        }
        return get(RedisKeys.USERINFO.of(uid), User.class);
    }

    public void bindUsernameAndUid(String username, Integer uid) {
        set(RedisKeys.UID.of(username), uid.toString());
    }

    public Integer getUidByUsername(String username) {
        if (! has(RedisKeys.UID.of(username))) {
            throw new StatusCode(404, "Username is not bind with uid");
        }
        return Integer.parseInt(get(RedisKeys.UID.of(username), String.class));
    }

    public void newCaptcha(String codeId, String codeContent) {
        set(RedisKeys.CODE_CONTENT.of(codeId), codeContent);
    }

    public String getCaptchaContent(String codeId) {
        if (! has(RedisKeys.CODE_CONTENT.of(codeId))) {
            throw new StatusCode(404, "CAPTCHA doesn't exists");
        }
        return get(RedisKeys.CODE_CONTENT.of(codeId), String.class);
    }
}

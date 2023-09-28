package com.connexion.cps.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class StringRedisService {

    private RedisTemplate<Object, Object> logicRedisTemplate;

    @Autowired
    @Qualifier("redisTemplate")
    public void setLogicRedisTemplate(RedisTemplate<Object, Object> template) {
        logicRedisTemplate = template;
    }

    public void set(String key, String val, int expiredInSecs) {

        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(val)) {
            return;
        }
        logicRedisTemplate.execute((RedisCallback<Void>) connection -> {
                    connection.set(key.getBytes(), val.getBytes(), Expiration.from(expiredInSecs, TimeUnit.SECONDS), RedisStringCommands.SetOption.UPSERT);
                    return null;
                }
        );
    }

    public String get(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        return logicRedisTemplate.execute((RedisCallback<String>) connection -> {
                    byte[] value = connection.get(key.getBytes());
                    if (value == null || value.length <= 0) {
                        return null;
                    }
                    String str = new String(value);
                    if (str == null || str.length() <= 0) {
                        return null;
                    }
                    return str;
                }
        );
    }

    public void delete(String key) {
        if (StringUtils.isEmpty(key)) {
            return;
        }

        logicRedisTemplate.delete(key);
    }
}

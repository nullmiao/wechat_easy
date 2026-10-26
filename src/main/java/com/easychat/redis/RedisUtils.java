package com.easychat.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis 通用工具
 * 注意：value 泛型必须是 Object —— 这个项目要往 Redis 存对象（TokenUserInfoDto、SysSettingDto）、
 * Long（心跳时间戳）、List<String>（联系人缓存），用 String 泛型存不了。
 */
@Slf4j
@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 删除缓存（支持批量）
     */
    public void delete(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }

    public <V> V get(String key) {
        return key == null ? null : (V) redisTemplate.opsForValue().get(key);
    }

    public <V> boolean set(String key, V value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error("设置redisKey:{},value:{}失败", key, value, e);
            return false;
        }
    }

    public <V> boolean setex(String key, V value, long seconds) {
        try {
            if (seconds > 0) {
                redisTemplate.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error("设置redisKey:{},value:{}失败", key, value, e);
            return false;
        }
    }

    public boolean expire(String key, long seconds) {
        try {
            if (seconds > 0) {
                redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error("设置redisKey:{}过期时间失败", key, e);
            return false;
        }
    }

    public <V> List<V> getQueueList(String key) {
        return (List<V>) redisTemplate.opsForList().range(key, 0, -1);
    }

    public <V> boolean lpush(String key, V value, long seconds) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            if (seconds > 0) {
                expire(key, seconds);
            }
            return true;
        } catch (Exception e) {
            log.error("设置redisKey:{},value:{}失败", key, value, e);
            return false;
        }
    }

    public <V> boolean lpushAll(String key, List<V> values, long seconds) {
        try {
            redisTemplate.opsForList().leftPushAll(key, values);
            if (seconds > 0) {
                expire(key, seconds);
            }
            return true;
        } catch (Exception e) {
            log.error("设置redisKey:{},values:{}失败", key, values, e);
            return false;
        }
    }

    public long remove(String key, Object value) {
        try {
            Long count = redisTemplate.opsForList().remove(key, 0, value);
            return count == null ? 0 : count;
        } catch (Exception e) {
            log.error("删除redisKey:{},value:{}失败", key, value, e);
            return 0;
        }
    }
}
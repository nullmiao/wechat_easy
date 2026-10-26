package com.easychat.redis;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Redisson 工具类
 *
 * 说明：本项目里 Redisson 主要用于 P6 的 RTopic 广播（在 MessageHandler 里直接用 RedissonClient），
 * 这个类目前只有集合操作，属于占位的薄封装。
 */
@Slf4j
@Component("redissonUtils")
public class RedissonUtils {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 向指定集合添加元素，并设置整个集合的过期时间
     * 注意：Redisson 默认 FstCodec（二进制），写入的数据不能用 RedisTemplate/redis-cli 正常读取
     *
     * @param key     集合 key
     * @param value   元素
     * @param seconds 过期秒数（<=0 则不设置过期）
     */
    public <T> void addSet(String key, T value, Long seconds) {
        try {
            RSet<T> rSet = redissonClient.getSet(key);
            boolean added = rSet.add(value);
            if (seconds != null && seconds > 0) {
                rSet.expire(seconds, TimeUnit.SECONDS);
            }
            log.debug("addSet key={}, value={}, added={}", key, value, added);
        } catch (Exception e) {
            log.error("addSet异常, key={}, value={}", key, value, e);
        }
    }
}
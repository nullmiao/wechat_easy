package com.easychat.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    /**
     * Redis 地址（来自 application.yml 的 spring.redis.host）
     */
    @Value("${spring.redis.host:127.0.0.1}")
    private String redisHost;

    /**
     * Redis 端口（来自 spring.redis.port）
     */
    @Value("${spring.redis.port:6379}")
    private Integer redisPort;

    /**
     * Redis 密码（来自 spring.redis.password）
     * 注意：Redisson 是独立客户端，不会读 spring.redis.* 自动配置，必须手动设置
     */
    @Value("${spring.redis.password:}")
    private String redisPassword;


    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // 1. key 序列化：String
        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        // 2. value 序列化：Jackson JSON
        Jackson2JsonRedisSerializer<Object> jacksonSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 开启类型记录，存对象的时候带上类名，方便反序列化
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL,
                JsonTypeInfo.As.PROPERTY);
        jacksonSerializer.setObjectMapper(om);

        // 设置序列化规则
        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);

        template.setValueSerializer(jacksonSerializer);
        template.setHashValueSerializer(jacksonSerializer);

        template.afterPropertiesSet();
        return template;
    }

    /**
     * Redisson 客户端（与上面的 RedisTemplate 是两套独立客户端）
     * 用途：
     *   - P3：RedissonUtils.addSet（在线用户集合等）
     *   - P6：MessageHandler 用 redissonClient.getTopic("message.topic") 做 WS 推送广播
     *
     * 注意：
     *   1. Redisson 不读 spring.redis.* 自动配置，host/port/password 要手动设置；
     *      本机 Redis 有 requirepass，必须 setPassword，否则启动报 NOAUTH；
     *   2. destroyMethod="shutdown"：容器关闭时释放 Redisson 的连接/线程池；
     *   3. 连接失败时这里让它直接抛异常（启动即失败），而不是像原项目那样 catch 后返回 null——
     *      返回 null 会让 Spring 认为 Bean 不存在，后面注入报错很难排查。
     */
    @Bean(name = "redissonClient", destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer()
                .setAddress("redis://" + redisHost + ":" + redisPort);
        if (redisPassword != null && !redisPassword.trim().isEmpty()) {
            singleServerConfig.setPassword(redisPassword);
        }
        return Redisson.create(config);
    }
}

package cn.echocow.xiaoming.config;

import cn.echocow.xiaoming.model.properties.ApplicationProperties;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.nio.charset.Charset;
import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * redis 配置
 *
 * @author Echo
 * @version 1.0
 * @date 2019-02-04 13:25
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    private final ApplicationProperties applicationProperties;
    private final RedisConnectionFactory connectionFactory;

    @Autowired
    public RedisConfig(RedisConnectionFactory connectionFactory, ApplicationProperties applicationProperties) {
        this.connectionFactory = connectionFactory;
        this.applicationProperties = applicationProperties;
    }

    @Bean
    @Override
    public CacheManager cacheManager() {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofHours(12))
                .prefixKeysWith(applicationProperties.getName())
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()));
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory))
                .cacheDefaults(redisCacheConfiguration)
                .transactionAware()
                .build();
    }

    @Bean
    public KeyGenerator cacheKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            Gson gson = new Gson();
            String paramsString = Arrays.stream(params).filter(Objects::nonNull)
                    .map(gson::toJson)
                    .collect(Collectors.joining(","));
            sb.append(paramsString);
            return sb.toString();
        };
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());
        return redisTemplate;
    }

    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    private RedisSerializer<Object> valueSerializer() {
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        Hibernate5Module hibernate5Module = new Hibernate5Module();
//        hibernate5Module.configure(Hibernate5Module.Feature.REQUIRE_EXPLICIT_LAZY_LOADING_MARKER, true);
//        om.registerModule(hibernate5Module);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        return jackson2JsonRedisSerializer;


        return new GenericJackson2JsonRedisSerializer();
//        return new Jackson2JsonRedisSerializer<>(Object.class);
//        return new OxmSerializer();
//        return new GsonRedisSerializer<>(Object.class);
//        return new FastJsonRedisSerializer<>(Object.class);
    }


    private class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
        private Class<T> clazz;
        FastJsonRedisSerializer(Class<T> clazz) {
            super();
            this.clazz = clazz;
        }

        @Override
        public byte[] serialize(T t) throws SerializationException {
            if (null == t) {
                return new byte[0];
            }
            return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(Charset.forName("UTF-8"));
        }

        @Override
        public T deserialize(byte[] bytes) throws SerializationException {
            if (null == bytes || bytes.length <= 0) {
                return null;
            }
            String str = new String(bytes, Charset.forName("UTF-8"));
            return JSON.parseObject(str, clazz);
        }
    }

    private class GsonRedisSerializer<T> implements RedisSerializer<T> {
        private Class<T> clazz;

        GsonRedisSerializer(Class<T> clazz) {
            super();
            this.clazz = clazz;
        }

        @Override
        public byte[] serialize(T t) throws SerializationException {
            if (t == null) {
                return new byte[0];
            }
            return new Gson().toJson(t).getBytes(Charset.forName("UTF-8"));
        }

        @Override
        public T deserialize(byte[] bytes) throws SerializationException {
            if (null == bytes || bytes.length <= 0) {
                return null;
            }
            String str = new String(bytes, Charset.forName("UTF-8"));
            return new Gson().fromJson(str, clazz);
        }
    }
}

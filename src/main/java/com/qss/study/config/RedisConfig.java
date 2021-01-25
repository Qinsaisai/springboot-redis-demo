package com.qss.study.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 自定义RedisTemplate
 * RedisTemplate,默认是jdk序列化,改成json格式序列化便于在redis中查看.
 * 由RedisAutoConfiguration源码可知，自定义RedisTemplate模版后，自定义的redisTemplate这个Bean会替换默认的redisTemplate这个Bean
 * 所以程序中使用redisTemplate来操作数据时，使用的是我们自定义的redisTemplate
 */
@Configuration
public class RedisConfig {

    //告诉编译器忽略全部的警告，不用在编译完成后出现警告信息
    @SuppressWarnings("all")
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<String, Object>();
        //连接工厂
        redisTemplate.setConnectionFactory(connectionFactory);
        //Jackson的序列化配置
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer=new Jackson2JsonRedisSerializer(Object.class);
        //Jackson对象
        ObjectMapper om=new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        //String类型的序列化配置
        StringRedisSerializer stringRedisSerializer=new StringRedisSerializer();
        //Key采用String的序列化操作
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //Hash的key也采用String的序列化方式
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        //value采用Jackson的序列化方式
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //Hash的value采用Jackson的序列化方式
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        //配置完之后将所有的properties设置进去
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}

# redis数据结构

- 字符串 String
- 列表 List
- 集合 Set
- 散列 Hash
- 有序集合 Zset

# spring封装的RedisTemplate和StringRedisTemplate

1. RedisTemplate模版中的key的类型通常为String类型，如RedisTemplate<String,Object>

    注意：如果没有特殊情况，切勿定义成RedisTemplate<Object,Object>，否则根据里氏替换原则，使用的时候会造成类型错误。

2. RedisTemplate和StringRedisTemplate对5种数据结构的操作

- 操作String

    redisTemplate.opsForValue()
    
    StringRedisTemplate.opsForValue()
    
- 操作List

    redisTemplate.opsForList()
    
    StringRedisTemplate.opsForList()
    
- 操作Set

    redisTemplate.opsForSet()
    
    StringRedisTemplate.opsForSet()
    
- 操作Hash

    redisTemplate.opsForHash()
    
    StringRedisTemplate.opsForHash()
    
- 操作Zset

    redisTemplate.opsForZSet()
    
    StringRedisTemplate.opsForZSet()
    
3. RedisTemplate与StringRedisTemplate的区别

- 两者的关系是StringRedisTemplate继承RedisTemplate
- 两者的数据是不共通的，即StringRedisTemplate只能管理StringRedisTemplate中的数据，RedisTemplate只能管理RedisTemplate中的数据
- 两者的序列化策略不同

    StringRedisTemplate默认采用的是String的序列化策略StringRedisSerializer，保存的key和value都是采用此策略序列化保存的
    
    RedisTemplate默认采用的是JDK的序列化策略JdkSerializationRedisSerializer，保存的key和value都是采用此策略序列化保存的
    
- 当你的redis数据库里面本来存的是字符串数据或者你要存取的数据就是字符串类型数据的时候，那么你就使用StringRedisTemplate即可；但是如果你的数据是复杂的对象类型，而取出的时候又不想做任何的数据转换，直接从Redis里面取出一个对象，那么使用RedisTemplate是更好的选择 

## spring-data-redis序列化策略

- GenericToStringSerializer: 可以将任何对象泛化为字符串并序列化
- Jackson2JsonRedisSerializer: 跟JacksonJsonRedisSerializer实际上是一样的
- JacksonJsonRedisSerializer: 序列化object对象为json字符串
- JdkSerializationRedisSerializer: 序列化java对象
- StringRedisSerializer: 简单的字符串序列化

其中，JdkSerializationRedisSerializer序列化后长度最小，Jackson2JsonRedisSerializer效率最高，所以
1. 如果综合考虑效率和可读性，牺牲部分空间，推荐key使用StringRedisSerializer，保持的key简明易读；value可以使用Jackson2JsonRedisSerializer
2. 如果空间比较敏感，效率要求不高，推荐key使用StringRedisSerializer，保持的key简明易读；value可以使用JdkSerializationRedisSerializer

推荐使用自定义的redisTemplate和自定义的Redis工具类简化redis对象操作，如/config/RedisConfig和/utils/RedisUtil

# spring基于注解的缓存(springBoot+redis+springCache 数据缓存)

对于缓存声明，spring的缓存提供了一组java注解：

- @Cacheable：触发缓存写入，将结果存储在缓存中，以便于在后续调用的时候可以直接返回缓存中的值，而不必再执行实际的方法
- @CacheEvict：触发缓存清除
- @CachePut：更新缓存（不会影响到方法的运行）
- @Caching：重新组合要应用于方法的多个缓存操作
- @CacheConfig：设置类级别上共享的一些常见缓存设置

springboot可通过注解的形式缓存数据到redis中

## Redis缓存配置

通过自定义缓存管理器（CacheManager）来统一基于注解的缓存规则，对缓存管理器的一些设置（全局过期时间等）都会反映到缓存管理器管理的所有缓存上

自定义缓存配置，如/config/RedisCacheConfig，其中自定义key的生成策略可根据业务或者其他要求编写，RedisCacheConfig中仅是一个简单的例子

# 参考文档

[https://www.cnblogs.com/hongchengcheng/p/8288747.html]

[https://blog.csdn.net/yitian_z/article/details/104265069]

[https://blog.csdn.net/weixin_43246215/article/details/108476328]

[https://www.cnblogs.com/wenjunwei/p/10779450.html]

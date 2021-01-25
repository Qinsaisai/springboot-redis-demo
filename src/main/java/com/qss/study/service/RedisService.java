package com.qss.study.service;

import com.qss.study.utils.RedisUtil;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Spring已封装好的RedisTemplate和StringTemplate
 * 最后一个方法测试使用我们自定义的redisTemplate
 */
@Service
public class RedisService {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisUtil redisUtil;

    /**
     * Redis字符串String操作示例
     */
    public void redisStringOps() {
        redisTemplate.opsForValue().set("key1","字符串1");
        System.out.println("value1:"+redisTemplate.opsForValue().get("key1"));
        redisTemplate.opsForValue().set("key2",1);
        System.out.println("value2:"+redisTemplate.opsForValue().get("key2"));
        redisTemplate.opsForValue().set("key3","1");
        System.out.println("value3:"+redisTemplate.opsForValue().get("key3"));

        stringRedisTemplate.opsForValue().set("key4","字符串4");
        System.out.println("value4:"+stringRedisTemplate.opsForValue().get("key4"));
        stringRedisTemplate.opsForValue().set("key5","1");
        System.out.println("value5:"+stringRedisTemplate.opsForValue().get("key5"));
        stringRedisTemplate.opsForValue().increment("key5",3);
        System.out.println("value6:"+stringRedisTemplate.opsForValue().get("key5"));
    }

    /**
     * Redis散列表Hash操作示例
     */
    public void redisHashOps() {
        // 存入一个Hash类型数据，第一个为存入Hash数据结构的整体key，后面为对应存入的值，也就是如下结构：
        // Map<Key, Map<field, value>>
        Map<String,Object> hash=new HashMap<>();
        hash.put("field1","value1");
        hash.put("field2","value2");
        hash.put("field3","value3");
        redisTemplate.opsForHash().putAll("hash1",hash);
        System.out.println("结果1:"+redisTemplate.opsForHash().get("hash1","field1"));
        // 向key为hash1的散列表中存入单条数据
        redisTemplate.opsForHash().put("hash1","field4","value4");
        System.out.println("结果2:"+redisTemplate.opsForHash().get("hash1","field4"));

        stringRedisTemplate.opsForHash().putAll("hash2",hash);
        System.out.println("结果3:"+stringRedisTemplate.opsForHash().get("hash2","field1"));
        // 向key为hash2的散列表中存入单条数据
        stringRedisTemplate.opsForHash().put("hash2","field4","value4");
        System.out.println("结果4:"+stringRedisTemplate.opsForHash().get("hash2","field4"));
    }

    /**
     * Redis集合List操作示例
     */
    public void redisListOps() {
        redisTemplate.opsForList().leftPushAll("list1","v2","v4","v6","v8");
        System.out.println("结果1:"+redisTemplate.opsForList().range("list1",0,4));
        List<String> list2=new ArrayList<>();
        list2.add("v2");
        list2.add("v4");
        list2.add("v6");
        list2.add("v8");
        redisTemplate.opsForList().leftPushAll("list2",list2);
        System.out.println("结果2:"+redisTemplate.opsForList().range("list2",0,4));

        stringRedisTemplate.opsForList().leftPushAll("list3","v2","v4","v6","v8");
        System.out.println("结果3:"+stringRedisTemplate.opsForList().range("list3",0,4));
        stringRedisTemplate.opsForList().leftPushAll("list4",list2);
        System.out.println("结果4:"+stringRedisTemplate.opsForList().range("list4",0,4));
        BoundListOperations listOperations=stringRedisTemplate.boundListOps("list4");
        System.out.println("结果5:"+listOperations.size());
    }

    /**
     * Redis中无序集合操作Set，元素无序且不能重复
     */
    public void redisSetOps() {
        redisTemplate.opsForSet().add("set1", "v1", "v1", "v2", "v3", "v4", "v5");
        System.out.println("结果1:"+redisTemplate.opsForSet().members("set1"));
        BoundSetOperations setOperations1=redisTemplate.boundSetOps("set1");
        setOperations1.add("v6");
        System.out.println("结果2:"+setOperations1.members());

        stringRedisTemplate.opsForSet().add("set2", "v1", "v1", "v2", "v3", "v4", "v5");
        System.out.println("结果3:"+stringRedisTemplate.opsForSet().members("set2"));
        BoundSetOperations setOperations2=stringRedisTemplate.boundSetOps("set2");
        setOperations2.add("v6");
        System.out.println("结果4:"+setOperations2.members());
    }

    /**
     * Redis中ZSet集合示例操作
     * Zset集合根据score值进行排序，所以每个元素的结构为/<value, score>
     */
    public void redisZSetOps() {
        Set<ZSetOperations.TypedTuple<Object>> zSet1=new HashSet<>();
        for (int i = 1; i <= 9; i++) {
            double score = i * 0.1;
            ZSetOperations.TypedTuple<Object> typedTuple = new DefaultTypedTuple<>("value" + i, score);
            zSet1.add(typedTuple);
        }
        redisTemplate.opsForZSet().add("zSet1", zSet1);
        System.out.println("结果1:"+redisTemplate.opsForZSet().range("zSet1",0,12));

        Set<ZSetOperations.TypedTuple<String>> zSet2=new HashSet<>();
        for (int i = 1; i <= 9; i++) {
            double score = i * 0.1;
            ZSetOperations.TypedTuple<String> typedTuple = new DefaultTypedTuple<>("value" + i, score);
            zSet2.add(typedTuple);
        }
        stringRedisTemplate.opsForZSet().add("zSet2", zSet2);
        System.out.println("结果2:"+stringRedisTemplate.opsForZSet().range("zSet2",0,12));
    }

    /**
     * 测试我们自定义的redisTemplate和redis工具类RedisUtil
     */
    public void ceshi() {
        redisUtil.set("key111","测试");
        System.out.println("结果:"+redisUtil.get("key111"));
    }
}

package com.qss.study.controller;

import com.qss.study.service.RedisService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 使用Spring已封装好的RedisTemplate和StringTemplate
 * 最后一个方法测试使用我们自定义的redisTemplate
 */
@RestController
public class RedisController {
    @Resource
    private RedisService redisService;

    /**
     * Redis字符串String操作示例
     */
    @GetMapping("/redis/string")
    public void redisStringOps(){
        redisService.redisStringOps();
    }

    /**
     * Redis散列表Hash操作示例
     */
    @GetMapping("/redis/hash")
    public void redisHashOps(){
        redisService.redisHashOps();
    }

    /**
     * Redis集合List操作示例
     */
    @GetMapping("/redis/list")
    public void redisListOps(){
        redisService.redisListOps();
    }

    /**
     * Redis中无序集合操作Set，元素无序且不能重复
     */
    @GetMapping("/redis/set")
    public void redisSetOps(){
        redisService.redisSetOps();
    }

    /**
     * Redis中ZSet集合示例操作
     * Zset集合根据score值进行排序，所以每个元素的结构为/<value, score>
     */
    @GetMapping("/redis/zSet")
    public void redisZSetOps(){
        redisService.redisZSetOps();
    }

    /**
     * 测试我们自定义的redisTemplate和redis工具类RedisUtil
     */
    @GetMapping("/redis/ceshi")
    public void ceshi(){
        redisService.ceshi();
    }
}

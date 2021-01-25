package com.qss.study.controller;

import com.qss.study.dto.UserDTO;
import com.qss.study.service.CacheService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * springBoot+redis+springcache 数据缓存
 */
@RestController
public class CacheController {
    @Resource
    private CacheService cacheService;

    /**
     * 获取用户
     * 测试@Cacheable
     * @return
     */
    @GetMapping("/cache/get")
    public void getCache(){
        for (int i = 0; i < 3; i++) {
            System.out.println("第" + i + "次");
            UserDTO user = cacheService.findUser();
            System.out.println(user);
        }
    }

    /**
     * 更新缓存中的用户
     * 测试@CachePut
     */
    @GetMapping("/cache/put")
    public void putCache(){
        cacheService.updateUser();
        UserDTO user = cacheService.findUser();
        System.out.println(user);
    }

    /**
     * 清除缓存中的用户
     * 测试@CacheEvict
     */
    @GetMapping("/cache/evict")
    public void evictCache(){
        cacheService.clearUser();
        UserDTO user = cacheService.findUser();
        System.out.println(user);
    }

    @GetMapping("/cache/ceshi/key")
    public void ceshiKey(){
        UserDTO user = cacheService.ceshiKey();
        System.out.println(user);
    }
}

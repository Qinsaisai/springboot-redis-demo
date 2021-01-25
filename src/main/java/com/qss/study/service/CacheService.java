package com.qss.study.service;

import com.qss.study.dto.UserDTO;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.text.DefaultEditorKit;

@Service
public class CacheService {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 查询用户
     * @return
     */
    @Cacheable(cacheNames = "user",key = "'key1'")
    public UserDTO findUser() {
        System.out.println("没有走缓存，执行了方法");
        UserDTO userDTO=new UserDTO();
        userDTO.setId(1);
        userDTO.setUserName("小红");
        userDTO.setAge(20);
        return userDTO;
    }

    /**
     * 更新缓存中的用户
     */
    @CachePut(cacheNames = "value1",key = "'key1'")
    public UserDTO updateUser() {
        System.out.println("更新缓存中的用户");
        UserDTO userDTO=new UserDTO();
        userDTO.setId(1);
        userDTO.setUserName("小绿");
        userDTO.setAge(18);
        return userDTO;
    }

    /**
     * 清除缓存中的用户
     * 注解参数里面加上beforeInvocation为true，意思是说当执行这个方法之前执行清除缓存的操作，这样不管这个方法执行成功与否，该缓存都将不存在
     * 注解参数加上allEntries为true时，意思是说这个清除缓存是清除当前value值空间下的所有缓存数据
     */
    @CacheEvict(cacheNames = "value1",allEntries = true)
    public void clearUser() {
        System.out.println("清除缓存中的用户");
    }

    @Cacheable(cacheNames = "user",keyGenerator = "myKeyGenerator")
    public UserDTO ceshiKey() {
        UserDTO userDTO=new UserDTO();
        userDTO.setId(1);
        userDTO.setUserName("小红");
        userDTO.setAge(20);
        return userDTO;
    }
}

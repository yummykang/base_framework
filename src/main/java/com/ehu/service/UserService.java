package com.ehu.service;

import com.ehu.entity.primary.MerchantUser;
import com.ehu.entity.another.DemonUser;
import com.ehu.repository.primary.MerchantUserDao;
import com.ehu.repository.another.DemonUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * write something to describe this file.
 *
 * @author demon
 * @since 2017-03-02 15:58.
 */
@Service
public class UserService {
    @Autowired
    private MerchantUserDao userDao;

    @Autowired
    private DemonUserDao demonUserDao;

    /**
     * 获取所有用户列表
     * Cacheable表示使用缓存，由于此方法是幂等性，所以方法被调用的结果将是一致的
     *
     * @return
     */
    @Cacheable(value = "userList", cacheManager = "redisCache", keyGenerator = "redisKeyGen")
    public Object getUserList() {

        System.out.println("无缓存的时候调用这里");
        return userDao.findAll();
    }

    /**
     * 添加用户
     * CachePut演示缓存的清除策略
     *
     * @return
     */
    @CachePut(value = "userList", cacheManager = "redisCache", keyGenerator = "redisKeyGen")
    public Object addUser() {
        MerchantUser merchantUser = new MerchantUser();
        return userDao.save(merchantUser);
    }

    /**
     * 添加用户，使用另一个数据源
     *
     * @return
     */
    public Object addDemonUser() {
        DemonUser demonUser = new DemonUser();
        demonUser.setName("demon");
        return demonUserDao.save(demonUser);
    }
}

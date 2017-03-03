package com.ehu.repository.primary;

import com.ehu.entity.primary.MerchantUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * write something to describe this file.
 *
 * @author demon
 * @since 2017-03-02 15:02.
 */
public interface MerchantUserDao extends JpaRepository<MerchantUser, Integer>, JpaSpecificationExecutor<MerchantUser> {
    /**
     * 根据手机号及登录密码查询用户信息
     *
     * @param phone    手机号
     * @param password 登录密码
     * @return
     */
    MerchantUser findByPhoneAndPassword(String phone, String password);
}

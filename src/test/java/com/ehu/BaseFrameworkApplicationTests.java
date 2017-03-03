package com.ehu;

import com.ehu.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseFrameworkApplicationTests {

	@Autowired
	private UserService userService;

	@Resource(name ="redisTemplate")
	ValueOperations valueOperations;

	@Test
	public void contextLoads() {
//		assert userDao.findAll() != null;
//		assert userDao.findAll() != null;
//		assert userDao.findAll() != null;
//		assert userService.getUserList() != null;
//		assert userService.getUserList() != null;
		Assert.notNull(userService.addUser());
		Assert.notNull(userService.addDemonUser());
//		assert userService.getUserList() != null;
		valueOperations.set("demon", "demon,biu,biu...");
		assert "demon,biu,biu...".equals(valueOperations.get("demon"));
	}

	@Test
	@Transactional("mysql2TransactionManager")
	public void anotherTest() {
		Assert.notNull(userService.addDemonUser());
	}

}

package junit.test.example.dao.impl;

import javax.annotation.Resource;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.hiveadmin.example.beans.UserExample;
import com.hiveadmin.example.dao.impl.ExampleUserDao;
import static org.junit.Assert.*;

import org.junit.Test;

@Component
public class ExampleUserDaoImplTest {
	@Resource
	ExampleUserDao exampleUserDao;
	@Test private void testAddUser() {
		exampleUserDao.addUser(new UserExample(1,"wangjie"));
	}
}

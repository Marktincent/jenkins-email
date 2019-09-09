package com.uaes.esw;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JenkinsEmailApplicationTests {

	@Test
	public void contextLoads() {
		String key = "\"aaaa\"\"";
		System.out.println(key);
		key = key.replace("\"", "");
		System.out.println(key);
	}

}

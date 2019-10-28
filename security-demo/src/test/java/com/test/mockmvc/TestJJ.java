package com.test.mockmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.my.security.SecurityDemoApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SecurityDemoApplication.class)
public class TestJJ {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void userTest() throws Exception {
		String result = mockMvc
				.perform(get("/user").param("userName", "ggg").contentType(MediaType.APPLICATION_JSON_UTF8))
				// 需要状态成功
				.andExpect(status().isOk())
				// 集合的 长度预期值是3
				.andExpect(jsonPath("$.length()").value(3)).andReturn().getResponse().getContentAsString();
		System.out.println(result);
	}

	@Test
	public void userTest1() throws Exception {
		String result = mockMvc.perform(get("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk()).andExpect(jsonPath("$.userName").value("tom")).andReturn().getResponse()
				.getContentAsString();
		System.out.println(result);
	}

	@Test
	public void userTest2() throws Exception {
		mockMvc.perform(get("/user/a").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().is4xxClientError());
	}

	@Test
	public void createSucceTest() throws Exception {
		String contenxt = "{\"userName\":\"tomc\",\"passWord\":\"null\"}";
		mockMvc.perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(contenxt))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").value("1"));

	}

}

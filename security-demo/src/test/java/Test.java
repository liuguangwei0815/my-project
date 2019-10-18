import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@org.junit.Test
	public void userTest() throws Exception {
		mockMvc.perform(get("/user").contentType(MediaType.APPLICATION_JSON_UTF8))
		//需要状态成功
		.andExpect(status().isOk())
		//集合的
		.andExpect(jsonPath("$.length()").value(3));
	}
	
	
	
	

}

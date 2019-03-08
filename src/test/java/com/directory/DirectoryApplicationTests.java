package com.directory;


import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

import com.directory.controller.UserController;
import com.directory.model.User;
import com.directory.repository.UserRepository;
import com.directory.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class DirectoryApplicationTests {


	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	private final ObjectMapper mapper = new ObjectMapper();


	@Test
	public void createUser() throws  Exception{
		User user = new User("Maria", "Stella", "marilosi@gmail.com");

		given(userService.create(any(User.class))).willReturn(user);

		String res = mockMvc.perform(post("/user")
				.content(mapper.writeValueAsString(user))
				.contentType(APPLICATION_JSON)
		)
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		User responseUser = mapper.readValue(res, User.class);
		assertEquals(user.getId(), responseUser.getId());

	}

}

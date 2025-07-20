package com.example.demo.errors;

import com.example.demo.user.UserController;
import com.example.demo.user.UserDTO;
import com.example.demo.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(ApiExceptionHandler.class)
class ApiExceptionHandlerTest {

  @Autowired MockMvc mvc;
  @Autowired ObjectMapper json;
  @MockBean UserService service;

  @Test void validationErrorReturnsJson422() throws Exception {
    when(service.create(any())).thenReturn(
        UserDTO.builder().id(1L).firstname("John").lastname("Doe").email("john@doe.com").build());

    UserDTO invalid = UserDTO.builder().firstname("").lastname("x").email("bad").build();

    mvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.writeValueAsString(invalid)))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.firstname").exists());
  }
}

package com.example.demo.user;

import com.example.demo.errors.ApiExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(ApiExceptionHandler.class)
class UserControllerTest {

  @Autowired MockMvc mvc;
  @Autowired ObjectMapper json;
  @MockBean UserService service;

  @Test void postUser422() throws Exception {
    UserDTO invalid = UserDTO.builder().firstname("").lastname("").email("bad").build();

    mvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.writeValueAsString(invalid)))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("$.firstname").exists());
  }

  @Test void getUser404() throws Exception {
    when(service.findById(99L)).thenThrow(new EntityNotFoundException());
    mvc.perform(get("/users/99")).andExpect(status().isNotFound());
  }
}

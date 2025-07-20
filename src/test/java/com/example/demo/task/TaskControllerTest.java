package com.example.demo.task;

import com.example.demo.board.Board;
import com.example.demo.board.BoardRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

  @Autowired MockMvc mvc;
  @Autowired ObjectMapper json;
  @Autowired UserRepository userRepo;
  @Autowired BoardRepository boardRepo;
  @Autowired TaskRepository taskRepo;
  @SpyBean TaskCompletedListener listener;

  User owner;
  Board board;

  @BeforeEach
  void setup() {
    taskRepo.deleteAll();
    boardRepo.deleteAll();
    userRepo.deleteAll();
    owner = userRepo.save(User.builder().firstname("John").lastname("Doe").email("john@doe.com").build());
    board = boardRepo.save(Board.builder().name("Kanban").build());
  }

  @Test void postTask201() throws Exception {
    TaskDTO dto = TaskDTO.builder().title("Task1").description("d".repeat(30)).status(Status.TODO).build();

    mvc.perform(post("/users/{uid}/tasks", owner.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.writeValueAsString(dto)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").exists());
  }

  @Test void putTaskDonePublishesEvent() throws Exception {
    Task t = taskRepo.save(Task.builder().title("TaskZ").description("d".repeat(30)).status(Status.TODO).owner(owner).board(board).build());

    TaskDTO update = TaskDTO.builder().title("TaskZ").description("d".repeat(30)).status(Status.DONE).build();

    mvc.perform(put("/users/{uid}/tasks/{tid}", owner.getId(), t.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.writeValueAsString(update)))
        .andExpect(status().isOk());

    Mockito.verify(listener).on(any(TaskCompletedEvent.class));
  }
}

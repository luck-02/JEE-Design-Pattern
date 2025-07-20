package com.example.demo;

import com.example.demo.board.Board;
import com.example.demo.board.BoardDTO;
import com.example.demo.task.Status;
import com.example.demo.task.Task;
import com.example.demo.task.TaskDTO;
import com.example.demo.user.User;
import com.example.demo.user.UserDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DumbCoverageTest {

  @Test void dtoMappersInvoke() {
    User u = User.builder().id(1L).firstname("A").lastname("B").email("c@d.e").build();
    Task t = Task.builder().id(1L).title("T").description("desc desc").status(Status.TODO).build();
    Board b = Board.builder().id(1L).name("N").build();

    assertThat(UserDTO.from(u).id()).isEqualTo(1L);
    assertThat(TaskDTO.from(t).status()).isEqualTo(Status.TODO);
    assertThat(BoardDTO.from(b).name()).isEqualTo("N");
  }
}

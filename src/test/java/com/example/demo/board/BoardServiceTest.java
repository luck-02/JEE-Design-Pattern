package com.example.demo.board;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

  @Mock BoardRepository repo;
  @InjectMocks BoardService service;

  @Test void createBoard() {
    Board saved = Board.builder().id(1L).name("Project").description("Desc").build();
    when(repo.save(any(Board.class))).thenReturn(saved);

    BoardDTO dto = BoardDTO.builder().name("Project").description("Desc").build();
    BoardDTO out = service.create(dto);

    assertThat(out.id()).isEqualTo(1L);
  }

  @Test void findAllBoards() {
    when(repo.findAll()).thenReturn(List.of(Board.builder().id(1L).name("P").build()));
    assertThat(service.findAll()).hasSize(1);
  }
}

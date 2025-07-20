package com.example.demo.task;
import com.example.demo.user.*;
import com.example.demo.board.BoardRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.extension.ExtendWith;
@ExtendWith(MockitoExtension.class)
class TaskServiceTest{
  @Mock TaskRepository taskRepo;
  @Mock UserRepository userRepo;
  @Mock BoardRepository boardRepo;
  @Mock org.springframework.context.ApplicationEventPublisher events;
  @InjectMocks TaskService service;
  @Test void create_links_owner(){
    User owner=User.builder().id(1L).build();
    when(userRepo.findById(1L)).thenReturn(Optional.of(owner));
    Task saved=Task.builder().id(2L).owner(owner).title("T").description("desc desc desc").status(Status.TODO).build();
    when(taskRepo.save(any(Task.class))).thenReturn(saved);
    TaskDTO in=TaskDTO.builder().title("T").description("desc desc desc").status(Status.TODO).build();
    TaskDTO out=service.create(1L,in);
    assertThat(out.id()).isEqualTo(2L);
  }
}

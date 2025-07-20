package com.example.demo.user;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @Mock UserRepository repo;
  @InjectMocks UserService service;
  @Test void create_returns_dto(){
    User saved=User.builder().id(1L).firstname("Ada").lastname("Lovelace").email("ada@io").build();
    when(repo.save(any(User.class))).thenReturn(saved);
    UserDTO dto=UserDTO.builder().firstname("Ada").lastname("Lovelace").email("ada@io").build();
    UserDTO out=service.create(dto);
    assertThat(out.id()).isEqualTo(1L);
  }
}

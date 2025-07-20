package com.example.demo.user;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service @RequiredArgsConstructor
public class UserService {
  private final UserRepository repo;

  public List<UserDTO> findAll(){
    return repo.findAll().stream().map(UserDTO::from).toList();
  }

  public UserDTO findById(Long id){
  return repo.findById(id)
    .map(UserDTO::from)
    .orElseThrow(() -> new EntityNotFoundException("User %d not found".formatted(id)));
}


  public UserDTO create(UserDTO dto){
    User u=User.builder().firstname(dto.firstname()).lastname(dto.lastname()).email(dto.email()).build();
    return UserDTO.from(repo.save(u));
  }

  public UserDTO update(Long id,UserDTO dto){
    User u=repo.findById(id).orElseThrow();
    u.setFirstname(dto.firstname());u.setLastname(dto.lastname());u.setEmail(dto.email());
    return UserDTO.from(repo.save(u));
  }
  
  public void delete(Long id){
    repo.deleteById(id);
  }
}

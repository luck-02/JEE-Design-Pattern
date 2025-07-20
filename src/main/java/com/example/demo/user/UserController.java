package com.example.demo.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService service;

  @GetMapping
  public List<UserDTO> all() {
    return service.findAll();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserDTO create(@Valid @RequestBody UserDTO dto) {
    return service.create(dto);
  }

  @GetMapping("/{id}")
  public UserDTO one(@PathVariable("id") Long id) {
    return service.findById(id);
  }

  @PutMapping("/{id}")
  public UserDTO update(@PathVariable("id") Long id,
                        @Valid @RequestBody UserDTO dto) {
    return service.update(id, dto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("id") Long id) {
    service.delete(id);
  }
}

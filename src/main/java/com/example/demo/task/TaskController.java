package com.example.demo.task;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/tasks")
public class TaskController {

  private final TaskService service;

  @GetMapping
  public List<TaskDTO> all(@PathVariable("userId") Long userId,
                           @RequestParam(required = false) String status,
                           @RequestParam(required = false) String search) {
    return service.findAll(userId, status, search);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TaskDTO create(@PathVariable("userId") Long userId,
                        @Valid @RequestBody TaskDTO dto) {
    return service.create(userId, dto);
  }

  @GetMapping("/{id}")
  public TaskDTO one(@PathVariable("userId") Long userId,
                     @PathVariable("id") Long id) {
    return service.findById(userId, id);
  }

  @PutMapping("/{id}")
  public TaskDTO update(@PathVariable("userId") Long userId,
                        @PathVariable("id") Long id,
                        @Valid @RequestBody TaskDTO dto) {
    return service.update(userId, id, dto);
  }

  @PutMapping("/{id}/owner/{ownerId}")
  public TaskDTO assignOwner(@PathVariable("userId") Long userId,
                            @PathVariable("id") Long id,
                            @PathVariable Long ownerId) {
    return service.assignOwner(id, ownerId);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("userId") Long userId,
                     @PathVariable("id") Long id) {
    service.delete(userId, id);
  }
}

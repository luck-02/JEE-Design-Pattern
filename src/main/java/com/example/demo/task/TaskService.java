package com.example.demo.task;
import com.example.demo.user.*;
import com.example.demo.board.*;
import com.example.demo.task.filter.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import java.util.*;
@Service @RequiredArgsConstructor
public class TaskService {
  private final TaskRepository repo;
  private final UserRepository userRepo;
  private final BoardRepository boardRepo;
  private final ApplicationEventPublisher events;

  public List<TaskDTO> findAll(Long userId,String status,String keyword){
    List<Task> tasks = repo.findByOwnerId(userId);
    List<TaskFilter> filters=new ArrayList<>();
    if(status!=null) filters.add(new StatusFilter(Status.valueOf(status)));
    if(keyword!=null&&!keyword.isBlank()) filters.add(new KeywordFilter(keyword));
    for(TaskFilter f:filters) tasks=f.apply(tasks);
    return tasks.stream().map(TaskDTO::from).toList();
  }

  public TaskDTO findById(Long userId,Long id){
    return repo.findById(id).filter(t->t.getOwner().getId().equals(userId)).map(TaskDTO::from).orElseThrow();
  }

  public TaskDTO create(Long creatorId, TaskDTO dto) {
    User creator = userRepo.findById(creatorId).orElseThrow();
    Board board  = boardRepo.findById(dto.boardId()).orElseThrow();
    User owner   = dto.ownerId() == null ? null :
                  userRepo.findById(dto.ownerId()).orElseThrow();
    Task t = Task.builder()
        .title(dto.title())
        .description(dto.description())
        .status(dto.status())
        .board(board)
        .owner(owner != null ? owner : creator) 
        .build();
    return TaskDTO.from(repo.save(t));
  }

  public TaskDTO assignOwner(Long taskId, Long ownerId) {
    Task t = repo.findById(taskId).orElseThrow();
    t.setOwner(userRepo.findById(ownerId).orElseThrow());
    return TaskDTO.from(repo.save(t));
  }

  public TaskDTO update(Long userId,Long id,TaskDTO dto){
    Task t=repo.findById(id).orElseThrow();
    if(!t.getOwner().getId().equals(userId)) throw new EntityNotFoundException();
    Status before=t.getStatus();
    t.setTitle(dto.title());t.setDescription(dto.description());t.setStatus(dto.status());
    Task saved=repo.save(t);
    if(before!=Status.DONE && saved.getStatus()==Status.DONE){
      events.publishEvent(new TaskCompletedEvent(saved.getId(),userId));
    }
    return TaskDTO.from(saved);
  }

  public void delete(Long userId,Long id){
    Task t=repo.findById(id).orElseThrow();
    if(!t.getOwner().getId().equals(userId)) throw new EntityNotFoundException();
    repo.delete(t);
  }
}

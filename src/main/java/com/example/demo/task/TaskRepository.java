package com.example.demo.task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface TaskRepository extends JpaRepository<Task,Long>{
  List<Task> findByOwnerId(Long ownerId);
  List<Task> findByBoardId(Long boardId);
}

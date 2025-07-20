package com.example.demo.task.filter;
import com.example.demo.task.Task;
import com.example.demo.task.Status;
import java.util.List;
public record StatusFilter(Status status) implements TaskFilter{
  public List<Task> apply(List<Task> source){
    return source.stream().filter(t->t.getStatus()==status).toList();
  }
}

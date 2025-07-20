package com.example.demo.task.filter;
import com.example.demo.task.Task;
import java.util.List;
public record KeywordFilter(String keyword) implements TaskFilter{
  public List<Task> apply(List<Task> source){
    return source.stream().filter(t->t.getTitle().contains(keyword)||t.getDescription().contains(keyword)).toList();
  }
}

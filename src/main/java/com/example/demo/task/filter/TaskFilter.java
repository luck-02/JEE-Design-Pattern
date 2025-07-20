package com.example.demo.task.filter;
import com.example.demo.task.Task;
import java.util.List;
public interface TaskFilter {
  List<Task> apply(List<Task> source);
}

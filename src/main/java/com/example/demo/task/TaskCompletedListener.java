package com.example.demo.task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
@Component @Slf4j
public class TaskCompletedListener {
  @EventListener
  public void on(TaskCompletedEvent e){ log.info("Task {} completed for user {}",e.taskId(),e.userId()); }
}

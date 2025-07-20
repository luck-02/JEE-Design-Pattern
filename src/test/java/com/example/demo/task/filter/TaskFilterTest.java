package com.example.demo.task.filter;
import com.example.demo.task.*;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;
import java.util.List;
class TaskFilterTest {
  @Test void status_filter(){
    Task t1=Task.builder().status(Status.TODO).build();
    Task t2=Task.builder().status(Status.DONE).build();
    List<Task> res=new StatusFilter(Status.DONE).apply(List.of(t1,t2));
    assertThat(res).containsExactly(t2);
  }
}

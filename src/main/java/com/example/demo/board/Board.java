package com.example.demo.board;

import com.example.demo.task.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.*;

@Entity @Table(name="boards")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Board {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @NotBlank @Size(min=3,max=50)
  private String name;

  @Size(max=255)
  private String description;

  @OneToMany(mappedBy="board", cascade=CascadeType.ALL, orphanRemoval=true)
  @Builder.Default
  private List<Task> tasks = new ArrayList<>();
}

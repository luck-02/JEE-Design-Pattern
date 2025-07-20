package com.example.demo.task;

import com.example.demo.user.User;
import com.example.demo.board.Board;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity @Table(name="tasks")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Task {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @NotBlank @Size(min=3,max=50)
  private String title;

  @Size(min=10,max=255)
  private String description;

  @Enumerated(EnumType.STRING)
  private Status status = Status.TODO;

  @ManyToOne(fetch=FetchType.LAZY)
  private User owner;

  @ManyToOne(fetch=FetchType.LAZY)
  private Board board;
}

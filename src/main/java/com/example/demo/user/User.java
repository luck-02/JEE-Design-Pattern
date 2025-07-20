package com.example.demo.user;

import com.example.demo.task.Task;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.*;

@Entity @Table(name="users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class User {

  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @NotBlank @Size(min=3,max=50)
  private String firstname;

  @NotBlank @Size(min=3,max=50)
  private String lastname;

  @NotBlank @Email @Column(unique=true)
  private String email;

  @OneToMany(mappedBy="owner", cascade=CascadeType.ALL, orphanRemoval=true)
  @Builder.Default
  private List<Task> tasks = new ArrayList<>();
}

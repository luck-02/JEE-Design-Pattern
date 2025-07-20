package com.example.demo.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record TaskDTO(
    Long id,
    @NotBlank @Size(max = 50) String title,
    @NotBlank @Size(max = 200) String description,
    Status status,
    Long boardId,
    Long ownerId
) {
  public static TaskDTO from(Task t) {
    return new TaskDTO(
        t.getId(),
        t.getTitle(),
        t.getDescription(),
        t.getStatus(),
        t.getBoard() == null ? null : t.getBoard().getId(),
        t.getOwner() == null ? null : t.getOwner().getId()
    );
  }
}

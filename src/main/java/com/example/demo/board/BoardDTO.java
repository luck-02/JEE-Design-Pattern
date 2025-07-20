package com.example.demo.board;
import jakarta.validation.constraints.*;
import lombok.Builder;
@Builder
public record BoardDTO(
  Long id,
  @NotBlank @Size(min=3,max=50) String name,
  @Size(max=255) String description) {
  public static BoardDTO from(Board b){return new BoardDTO(b.getId(),b.getName(),b.getDescription());}
}

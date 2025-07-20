package com.example.demo.user;
import jakarta.validation.constraints.*;
import lombok.Builder;
@Builder
public record UserDTO(
  Long id,
  @NotBlank @Size(min=3,max=50) String firstname,
  @NotBlank @Size(min=3,max=50) String lastname,
  @NotBlank @Email String email) {
    
  public static UserDTO from(User u){
    return new UserDTO(u.getId(),u.getFirstname(),u.getLastname(),u.getEmail());
  }
}

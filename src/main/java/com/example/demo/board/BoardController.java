package com.example.demo.board;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/boards") @RequiredArgsConstructor
public class BoardController {
  private final BoardService service;
  @GetMapping public List<BoardDTO> all(){return service.findAll();}
  @PostMapping @ResponseStatus(HttpStatus.CREATED) public BoardDTO create(@Valid @RequestBody BoardDTO d){return service.create(d);}
  @GetMapping("/{id}") public BoardDTO one(@PathVariable Long id){return service.findById(id);}
  @PutMapping("/{id}") public BoardDTO update(@PathVariable Long id,@Valid @RequestBody BoardDTO d){return service.update(id,d);}
  @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){service.delete(id);}
}

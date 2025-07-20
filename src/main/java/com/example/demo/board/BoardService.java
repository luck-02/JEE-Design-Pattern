package com.example.demo.board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service @RequiredArgsConstructor
public class BoardService {
  private final BoardRepository repo;
  public List<BoardDTO> findAll(){return repo.findAll().stream().map(BoardDTO::from).toList();}
  public BoardDTO findById(Long id){return repo.findById(id).map(BoardDTO::from).orElseThrow();}
  public BoardDTO create(BoardDTO dto){
    Board b=Board.builder().name(dto.name()).description(dto.description()).build();
    return BoardDTO.from(repo.save(b));
  }
  public BoardDTO update(Long id,BoardDTO dto){
    Board b=repo.findById(id).orElseThrow();
    b.setName(dto.name());b.setDescription(dto.description());
    return BoardDTO.from(repo.save(b));
  }
  public void delete(Long id){repo.deleteById(id);}
}

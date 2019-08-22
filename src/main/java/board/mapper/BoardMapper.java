package board.mapper;

import board.model.api.BoardApi;
import board.model.entity.BoardEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

// myBatis의 mapper.mapper interface임을 선언
@Repository
@Mapper
public interface BoardMapper {
    // interface이기 때문에 메서드의 이름과 반환 형식만 지정한다.
    // 여기서 지정한 메서드의 이름은 SQL의 이름과 동일해야 한다.
    List<BoardEntity> selectBoardList() throws Exception;
    void insertBoard(BoardEntity entity) throws Exception;
    BoardEntity selectBoardDetail(int boardIdx) throws Exception;
    void updateBoard(BoardEntity entity) throws Exception;
    void deleteBoard(int boardIdx) throws Exception;
}

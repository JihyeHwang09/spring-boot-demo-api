package board.service;

import board.model.api.BoardApi;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

// 서비스 영역
// 일반적으로 2개의 파일로 구성(Service 인터페이스와 ServiceImpl 클래스)
// * 인터페이스를 실제로 구현하는 클래스와 인터페이스로 분리할 경우 장점
// 1) 느슨한 결합을 유지하여 각 기능 간의 의존 관계를 최소화
// 2) 의존관계의 최소화로 인해 기능의 변화에도 최소한의 수정으로 개발할 수 있음(유연함)
// 3) 모듈화를 통해 어디서든 사용할 수 있도록 하여 재사용성을 높임
// 4) 스프링의 IOC/DI 기능을 이용한 Bean 관리 기능을 사용할 수 있음
@Service
public interface BoardService {
    // 게시물 목록
    List<BoardApi> selectBoardList() throws Exception;
    // 게시물 등록
    void insertBoard(BoardApi api) throws Exception;
    // 게시물 상세 페이지
    BoardApi selectBoardDetail(int boardIdx) throws Exception;
    // 게시물 수정
    void updateBoard(int BoardIdx, BoardApi api) throws Exception;
    // 게시물 삭제
    void deleteBoard(int boardIdx) throws Exception;
}
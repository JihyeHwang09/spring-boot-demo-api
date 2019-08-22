package board.controller;


import board.model.api.BoardApi;
import board.model.entity.BoardEntity;
import board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


//@Controller와 @ResponseBody 어노테이션을 합친 어노테이션
// @RestController 어노테이션을 사용하면 해당 API의 응답 결과를
//웹 응답 바디(Web response body)를 이용해서 보내준다.
// 일반적으로는 서버와 클라이언트의 통신에 JSON 형식을 사용한다.
//@RestController 어노테이션을 사용하면 결과값을 JSON 형식으로 만들어준다.
@RestController
public class RestBoardApiController {

    private BoardService boardService;

    @Autowired
    public void setBoardService(BoardService boardService) {
        this.boardService = boardService;
    }

    // 게시물 목록을 조회하고 그 결과를 반환한다.
    // 조회 결과를 바로 API의 응답 결과로 사용
    // 게시글 목록 조회는 List<BoardEntity>형식이고, 이를 바로 JSON 형태로 반환한다.
    @RequestMapping(value = "/api/board", method = RequestMethod.GET)
    public List<BoardApi> openBoardList() throws Exception {
        return boardService.selectBoardList();
    }

    @RequestMapping(value = "/api/board", method = RequestMethod.POST)
    public void insertBoard(@RequestBody BoardApi api) throws  Exception {
        // * 첨부파일은 구현하지 않을 예정이므로 boardService.insertBoard(board, null)을
        // 아래와 같이 수정했음
        boardService.insertBoard(api);
    }

//  @PathVariable: 경로의 특정부분의 값이 고정되어 있지 않고 달라질 때
//  사용할 수 있다.
//  mapping 경로에 {경로변수}와 같이 중괄호로 둘러싸인 부분을 '경로변수'라고 한다.
// -> {경로변수}에 해당하는 값은 같은 경로 변수 이름을
// 지정한 @PathVariable 파라미터로 전달된다.

// int boardIdx: boardIdx 파라미터의 타입은 String인데 int타입으로 변환해준다.
    @RequestMapping(value="/api/board/{boardIdx}", method = RequestMethod.GET)
    public BoardApi openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws  Exception {
        return boardService.selectBoardDetail(boardIdx);
    }


// * GET과 POST, PUT 요청의 차이점
//: GET은 요청 주소에 파라미터를 같이 보낸다.
//-> GET 요청은 @RequestParam이나 @PathVariable를 붙여준다.
// POST와 PUT은 GET과 달리 파라미터를 HTTP 패킷의 바디에 담아서 전송한다.
// @RequestBody는 메서드의 파라미터를 HTTP 바디에 담아서 보낸다.
// -> POST, PUT을 요청을 사용할 때는 @RequestBody를 붙여준다.

    @RequestMapping(value = "/api/board/{boardIdx}", method = RequestMethod.PUT)
    public void updateBoard(@PathVariable("boardIdx") int boardIdx, @RequestBody  BoardApi api) throws Exception {
        boardService.updateBoard(boardIdx, api);
    }

    // 임상시험 data는 절대 삭제해서는 안되므로
    // 실제로 delete를 수행하지는 않고, table에 삭제 여부만 바꿔준다. -> PUT 요청 사용
    @RequestMapping(value="/api/board/{boardIdx}", method = RequestMethod.DELETE)
    public void deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
        boardService.deleteBoard(boardIdx);
    }
}
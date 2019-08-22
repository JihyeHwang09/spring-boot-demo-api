package board.service;
// 1. 컨트롤러 클래스에 @Controller 어노테이션을 적용
// 2. @RequestMapping 어노테이션을 이용하여 요청에 대한 주소를 지정
// 3. 요청에 필요한 비즈니스 로직을 호출(비즈니스 로직이 필요한 경우에만)
// 4. 실행된 비즈니스 로직의 결과를 뷰로 리턴


import board.mapper.BoardMapper;
import board.model.api.BoardApi;
import board.model.entity.BoardEntity;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



// @Service
//: 비즈니스 로직을 처리하는 서비스 클래스를 나타내는 Annotation
// 서비스 클래스에서 @Service를 이용해서 해당 클래스가 스프링 MVC 서비스임을 나타낸다.
// @MapperScan(basePackages = "board.mapper.BoardMapper")
@Service
public class BoardServiceImpl implements  BoardService {

    // 데이터베이스에 접근하는 DAO 빈을 선언
    @Autowired
    private BoardMapper boardMapper;

    /*public void setBoardMapper(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }*/


    // 사용자 요청을 처리하기 위한 비즈니스 로직 구현
    // 데이터를 조회하도록 BoardMapper 클래스의 selectBoardList 메서드를 호출
    // 게시글 목록을 조회할 때는 별다른 비즈니스 로직이 없기 때문에 조회 메서드만 호출하였지만
    // 일반적으로는 결과 조회 및 데이터 가공을 위해 좀 더 복잡한 로직을 처리하게 된다.
    @Override
    public List<BoardApi> selectBoardList() throws Exception {
    // Lamda로 작성한 코드
        // Optional:존재할 수 있지만 안 할 수도 있는 객체
        // -> 즉, null이 될 수도 있는 객체를 감싸고 있는 일종의 래퍼 클래스
        // 직접 다루기에 위험하고 까다로운 null을 담을 수 있는 특수한 그릇으로 생각하면, 이해하기 쉽다.

        // * Optional의 효과
        // : NPE를 유발할 수 있는 null을 직접 다루지 않아도 된다.
        // 수고롭게 null 체크를 직접 하지 않아도 된다.
        //명시적으로 해당 변수가 null일 수도 있다는 가능성을 표현할 수 있다.
        // -> 따라서 불필요한 방어 로직을 줄일 수 있다.

        // * Optional.ofNullable(value)
        //: null인지 아닌지 확신할 수 없는 객체를 담고 있는 Optional 객체를 생성한다.
        // Optional.empty()와 Optional.of(value)를 합쳐놓은 메소드라고 생각하면 된다.
        // null이 넘어올 경우, NPE를 던지지  않고, Optional.empty()와 동일하게 비어있는 Optional 객체를 얻어온다.
        // 해당 객체가 null인지 아닌지 자신이 없는 상황에서 이 메소드를 사용
        return Optional.ofNullable(boardMapper.selectBoardList())
                        // 글목록인 entityList에서 Stream을 얻는다.
                .map(entityList ->
                                entityList.stream().map(entity -> {
                                // entity 객체 하나가 들어올 때마다 BoardApi 객체를 만든다.
                                BoardApi api = new BoardApi();
                                //  BeanUtils.copyProperties('원본 클래스', '대상 클래스');
                                // :class 간 property를 복사할 때 사용
                                // entity의 property를 api로 복사한다.
                                 BeanUtils.copyProperties(entity, api);
    //                            api객체를 리턴한다.
                                return api;
                                // .collect(Collectors.toList()): stream -> collection으로 변환
                                // Stream에서 collect() 메소드로 BoardApi를 수집해서 새로운 List를 얻는다.
                                }).collect(Collectors.toList())
                        // 값이 없다면 null을 리턴
                    ).orElse(null);

//        Lamda를 이용하지 않고 구현하는 방법
//        List<BoardApi> apiList = null;
//        if (!CollectionUtils.isEmpty(entityList)) {
//            apiList = new ArrayList<>();
//            for (BoardEntity entity : entityList) {
//                BoardApi api = new BoardApi();
//                BeanUtils.copyProperties(entity, api);
//                apiList.add(api);
//            }
//        }
//
//        return apiList;
    }
    @Override
    public void insertBoard(BoardApi api) throws Exception {
        BoardEntity entity = new BoardEntity();
        BeanUtils.copyProperties(api, entity);
        boardMapper.insertBoard(entity);
    }


    @Override
    public BoardApi selectBoardDetail(int boardIdx) throws Exception {
        // 선택된 게시글의 내용을 조회
        BoardEntity entity = boardMapper.selectBoardDetail(boardIdx);
        BoardApi api = new BoardApi();
        BeanUtils.copyProperties(entity, api);
        return api;
    }

//boardIdx, BoardApi api의 총 2개의 매개변수를 받는데,
//MyBatis에서는 parameter를 1개만 줄 수 있다
//해결 방법:
//parameter로 보내기 전에 boardIdx를  set해서 1개의 객체로 만들어준다.
//-> 1) api에 boardIdx를 set하고 copyProperties로 api => entity로 복사해준다.
//-> 2) copyProperties로 api => entity로 복사한 후, entity에 boardIdx를 set 해준다.
    @Override
    public void updateBoard(int boardIdx, BoardApi api) throws Exception {
        // 1) api에 boardIdx를 set하고 copyProperties로 api => entity로 복사해준다.
        api.setBoardIdx(boardIdx);
        BoardEntity entity = new BoardEntity();
        BeanUtils.copyProperties(api, entity);
        // 2) copyProperties로 api => entity로 복사한 후, entity에 boardIdx를 set 해준다.
//        entity.setBoardIdx(boardIdx);
        boardMapper.updateBoard(entity);

    }

    @Override
    public void deleteBoard(int boardIdx) throws Exception {
        boardMapper.deleteBoard(boardIdx);
    }

}

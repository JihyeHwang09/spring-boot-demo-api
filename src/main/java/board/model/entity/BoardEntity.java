package board.model.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;


// lombok의 annotation으로 모든 필드의 getter와 setter를 생성,
//  DB를 사용해 데이터를 조회하거나 조작하는 기능을 담당하는 것들을 DAO라고 부른다.
//  domain logic (비즈니스 로직이나 DB와 관련없는 코드들)을 persistence mechanism과 분리하기 위해 사용

//// toString, hashCode, equals 메서드도 생성
//// 단, setter의 경우, final이 선언되지 않은 필드에만 적용


@JsonInclude(JsonInclude.Include.NON_NULL)
@Repository
@Data
public class BoardEntity {
    // DB의 게시판 테이블 컬럼과 매칭됨
    // 표기법: 자바는 camel case, DB는 snake case를 사용
    // -> 표기법과 관련된 설정도 필요함
    // 이 설정을 위해 application.yml에 mybatis: map-underscore-to-camel-case: true를 추가
    // 글 번호
    private int boardIdx;
    // 제목
    private String title;
    // 내용
    private String contents;
    // 조회수
    private int hitCnt;
    // 작성자
    private String creatorId;
    // 작성시간
    private Date createdDatetime;
    // 수정자
    private String updaterId;
    // 수정시간
    private Date updatedDatetime;
}

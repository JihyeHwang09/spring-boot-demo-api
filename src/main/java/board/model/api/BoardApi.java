package board.model.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;


// 가져온 data들 중에서 null이 있으면 제외한다.
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class BoardApi {
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

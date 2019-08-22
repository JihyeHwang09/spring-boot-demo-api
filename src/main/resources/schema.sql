CREATE TABLE T_BOARD(
--  AUTO_INCREMENT 속성: INSERT문을 보낼 때마다
--  자동으로 NUM값이 증가되도록 저장
    BOARD_IDX INT(11) NOT NULL AUTO_INCREMENT COMMENT '글 번호',
    TITLE VARCHAR2(300) NOT NULL COMMENT '제목',
    CONTENTS TEXT NOT NULL COMMENT '내용',
    CREATOR_ID VARCHAR2(50) NOT NULL COMMENT '작성자',
    CREATED_DATETIME DATE DEFAULT SYSDATE COMMENT '작성시간',
    UPDATER_ID VARCHAR2(50) DEFAULT NULL COMMENT '수정자',
    UPDATED_DATETIME DATE DEFAULT NULL COMMENT '수정시간',
    DELETED INT(1) NOT NULL DEFAULT 0 COMMENT '삭제여부',
    PRIMARY KEY (BOARD_IDX)
);

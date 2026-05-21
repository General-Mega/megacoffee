package com.megacoffee.modules.boards;

import com.megacoffee.model.BaseVO;

public class BoardsFileVO extends BaseVO{
    private Long boardSeq;
    private int orderNum;
    private String fileId;

    /**
     * 게시글 관리번호
     * @return
     */
    public Long getBoardSeq() {
        return boardSeq;
    }
    public void setBoardSeq(Long boardSeq) {
        this.boardSeq = boardSeq;
    }
    /**
     * 게시글 내 파일 순서
     * @return
     */
    public int getOrderNum() {
        return orderNum;
    }
    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
    /**
     * 파일 관리ID
     * @return
     */
    public String getFileId() {
        return fileId;
    }
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
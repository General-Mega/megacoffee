package com.megacoffee.modules.boards;

import java.util.List;

import com.megacoffee.model.BaseVO;
import com.megacoffee.model.BoardTypeEnum;
import com.megacoffee.model.FileVO;

public class BoardsVO extends BaseVO {
    private Long seq;
    private String type;
    private String title;
    private String content;
    private boolean topFixed = false;

    private List<String> fileIds;
    private List<FileVO> files;

    public Long getSeq() {
        return seq;
    }
    public void setSeq(Long seq) {
        this.seq = seq;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public BoardTypeEnum getTypeEnum() {
        return BoardTypeEnum.fromCode(type);
    }
    public void setTypeEnum(BoardTypeEnum type) {
        this.type = type != null ? type.getCode() : null;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public boolean isTopFixed() {
        return topFixed;
    }
    public void setTopFixed(boolean topFixed) {
        this.topFixed = topFixed;
    }
    public List<String> getFileIds() {
        return fileIds;
    }
    public void setFileIds(List<String> fileIds) {
        this.fileIds = fileIds;
    }
    public List<FileVO> getFiles() {
        return files;
    }
    public void setFiles(List<FileVO> files) {
        this.files = files;
    }
}
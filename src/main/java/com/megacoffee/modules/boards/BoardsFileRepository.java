package com.megacoffee.modules.boards;

import java.util.List;

import com.megacoffee.model.FileVO;

public interface BoardsFileRepository {
    List<FileVO> findFilesByBoardSeq(Long boardSeq);
    int append(BoardsFileVO file);
    int deleteByBoardSeq(Long boardSeq, Long deleteIdx);
}

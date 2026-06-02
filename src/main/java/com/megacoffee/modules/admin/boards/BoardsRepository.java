package com.megacoffee.modules.admin.boards;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.megacoffee.model.PageVO;

public interface BoardsRepository {
    List<BoardsVO> list(BoardsSearchVO param);
    PageVO paging(BoardsSearchVO param);
    BoardsVO item(BoardsVO param);
    int append(BoardsVO board);
    int modify(BoardsVO board);
    int remove(BoardsVO board);
    int removes(@Param("seqs") List<Long> seqs, @Param("type") String type, @Param("createIdx") Long createIdx);
}

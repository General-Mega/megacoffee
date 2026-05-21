package com.megacoffee.modules.boards;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.megacoffee.infra.Security;
import com.megacoffee.model.FileVO;
import com.megacoffee.model.PageVO;

@Service
public class BoardsService {

    @Autowired
    private BoardsRepository repo;

    @Autowired
    private BoardsFileRepository fileRepo;

    public List<BoardsVO> list(BoardsSearchVO param) {
        return repo.list(param);
    }

    public PageVO paging(BoardsSearchVO param) {
        return repo.paging(param);
    }

    public BoardsVO item(BoardsVO board) {
        BoardsVO data = repo.item(board);
        if (data != null) {
            List<FileVO> files = fileRepo.findFilesByBoardSeq(data.getSeq());
            data.setFiles(files);
            if (files != null && !files.isEmpty()) {
                data.setFileIds(files.stream()
                        .map(FileVO::getFileId)
                        .collect(Collectors.toList()));
            }
        }
        return data;
    }

    @Transactional
    public int append(BoardsVO board) {
        board.setCreateIdx(Security.idx());
        int count = repo.append(board);
        if (count == 1 && board.getFileIds() != null) {
            saveBoardFiles(board.getSeq(), board.getFileIds());
        }
        return count;
    }

    @Transactional
    public int modify(BoardsVO board) {
        board.setCreateIdx(Security.idx());
        int count = repo.modify(board);
        if (count == 1 && board.getFileIds() != null) {
            fileRepo.deleteByBoardSeq(board.getSeq(), Security.idx());
            saveBoardFiles(board.getSeq(), board.getFileIds());
        }
        return count;
    }

    private void saveBoardFiles(Long boardSeq, List<String> fileIds) {
        if (boardSeq == null || fileIds == null || fileIds.isEmpty()) {
            return;
        }
        int order = 1;
        for (String fileId : fileIds) {
            if (fileId == null || fileId.trim().isEmpty()) {
                continue;
            }
            BoardsFileVO link = new BoardsFileVO();
            link.setBoardSeq(boardSeq);
            link.setFileId(fileId.trim());
            link.setOrderNum(order++);
            fileRepo.append(link);
        }
    }

    public int remove(BoardsVO board) {
        board.setCreateIdx(Security.idx());
        return repo.remove(board);
    }

    public int removes(List<Long> seqs, String type) {
        if (seqs == null || seqs.isEmpty()) {
            return 0;
        }
        return repo.removes(seqs, type, Security.idx());
    }
}

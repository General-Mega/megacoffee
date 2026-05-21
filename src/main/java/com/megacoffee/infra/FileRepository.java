package com.megacoffee.infra;

import com.megacoffee.model.FileVO;

public interface FileRepository {
    int append(FileVO file);
    FileVO findByFileId(String fileId);
}

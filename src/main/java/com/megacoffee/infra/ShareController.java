package com.megacoffee.infra;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.megacoffee.model.FileVO;
import com.megacoffee.model.ResultVO;

@RestController
public class ShareController {
    @Autowired
    private ShareService service;

    @Autowired
    private FileService fileService;

    @PostMapping("/file/upload")
    public ResultVO fileUpload(@RequestParam("file") MultipartFile file) {
        try {
            FileVO saved = fileService.saveFile(file);
            return new ResultVO(200, "Success", saved);
        } catch (IllegalArgumentException e) {
            return new ResultVO(400, e.getMessage());
        } catch (Exception e) {
            return new ResultVO(500, "파일 업로드 중 오류가 발생했습니다. " + e.getMessage());
        }
    }

    @GetMapping("/file/{fileId}/load")
    public ResultVO fileLoad(@PathVariable String fileId) {
        FileVO file = fileService.findByFileId(fileId);
        if (file == null) {
            return new ResultVO(404, "파일을 찾을 수 없습니다.");
        }
        return new ResultVO(200, "Success", file);
    }

    @GetMapping("/file/{fileId}/download")
    public ResponseEntity<Resource> fileDownload(@PathVariable String fileId) {
        try {
            FileVO file = fileService.findByFileId(fileId);
            if (file == null) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = fileService.loadFileAsResource(file);
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            String encodedFileName = URLEncoder.encode(file.getOriginalName(), StandardCharsets.UTF_8.toString())
                    .replace("+", "%20");
            String contentDisposition = "attachment; filename=\"" + file.getOriginalName() + "\"; filename*=UTF-8''" + encodedFileName;

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(file.getMimeType() != null ? file.getMimeType() : "application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

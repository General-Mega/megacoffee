package com.megacoffee.infra;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.megacoffee.model.FileVO;

@Service
public class FileService {

    private final Path rootPath;

    @Autowired
    private FileRepository fileRepository;

    public FileService(@Value("${file.upload-dir:uploads}") String uploadDir) throws IOException {
        this.rootPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(this.rootPath);
    }

    public FileVO saveFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new IllegalArgumentException("업로드할 파일을 선택해주세요.");
        }

        Long createIdx = Security.idx();

        String originalName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        if (originalName.contains("..")) {
            throw new IllegalArgumentException("잘못된 파일 이름입니다.");
        }

        String extension = extractExtension(originalName);
        String fileId = UUID.randomUUID().toString();
        String storedName = fileId + (extension.isEmpty() ? "" : "." + extension);

        Path targetLocation = rootPath.resolve(storedName);
        Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        FileVO file = new FileVO();
        file.setFileId(fileId);
        file.setOriginalName(originalName);
        file.setStoredName(storedName);
        file.setFilePath(targetLocation.toString());
        file.setFileExtension(extension);
        file.setFileSize(multipartFile.getSize());
        file.setMimeType(StringUtils.hasText(multipartFile.getContentType()) ? multipartFile.getContentType() : "application/octet-stream");
        file.setCreateIdx(createIdx);
        
        fileRepository.append(file);
        return file;
    }

    public FileVO findByFileId(String fileId) {
        if (!StringUtils.hasText(fileId)) {
            return null;
        }
        return fileRepository.findByFileId(fileId);
    }

    public Resource loadFileAsResource(FileVO file) throws MalformedURLException {
        Path filePath = Paths.get(file.getFilePath()).toAbsolutePath().normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists() && resource.isReadable()) {
            return resource;
        }
        throw new IllegalStateException("파일을 읽을 수 없습니다: " + filePath);
    }

    private String extractExtension(String originalName) {
        int index = originalName.lastIndexOf('.');
        if (index == -1 || index == originalName.length() - 1) {
            return "";
        }
        return originalName.substring(index + 1);
    }
}

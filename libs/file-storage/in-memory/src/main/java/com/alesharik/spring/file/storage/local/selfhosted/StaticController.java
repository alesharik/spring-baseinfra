package com.alesharik.spring.file.storage.local.selfhosted;

import com.alesharik.spring.file.storage.FileStorage;
import com.alesharik.spring.file.storage.exception.FileNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/staticv1")
public class StaticController {
    private final FileStorage fileStorage;

    @GetMapping("/{type}/{item}/{file}.{ext}")
    public ResponseEntity<FileSystemResource> getFile(
            @PathVariable("type") String type,
            @PathVariable("item") String item,
            @PathVariable("file") String file,
            @PathVariable("ext") String ext
    ) {
        try {
            var f = fileStorage.getFile(type, item, file + "." + ext);
            var contentType = Files.probeContentType(f.getFile().toPath());
            LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            if (contentType != null)
                headers.set("Content-Type", contentType);
            return new ResponseEntity<>(f, headers, HttpStatus.OK);
        } catch (FileNotFoundException | IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

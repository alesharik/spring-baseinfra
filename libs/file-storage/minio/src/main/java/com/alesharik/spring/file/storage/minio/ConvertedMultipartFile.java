package com.alesharik.spring.file.storage.minio;

import com.alesharik.spring.file.storage.FileConverter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
class ConvertedMultipartFile implements MultipartFile {
    private final MultipartFile source;
    private final FileConverter converter;

    @NotNull
    @Override
    public String getName() {
        return source.getName();
    }

    @Override
    public String getOriginalFilename() {
        return source.getOriginalFilename();
    }

    @Override
    public String getContentType() {
        return source.getContentType();
    }

    @Override
    public boolean isEmpty() {
        return source.isEmpty();
    }

    @Override
    public long getSize() {
        return source.getSize();
    }

    @NotNull
    @Override
    public byte[] getBytes() throws IOException {
        return getInputStream().readAllBytes();
    }

    @NotNull
    @Override
    public InputStream getInputStream() throws IOException {
        return converter.convert(source.getInputStream());
    }

    @Override
    public void transferTo(@NotNull File dest) throws IOException, IllegalStateException {
        try (var out = new FileOutputStream(dest)) {
            getInputStream().transferTo(out);
        }
    }
}

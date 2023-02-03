package com.alesharik.spring.file.storage.verifier;

import com.alesharik.spring.file.storage.FileTypeVerifier;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;

/**
 * This verifies pass only JPEG images
 */
@Slf4j
public class JpegVerifier implements FileTypeVerifier {
    private static final String JPG_EXTENSION = ".jpg";
    private static final String JPEG_EXTENSION = ".jpeg";

    @Override
    public boolean verifyFile(@NonNull Path path, @NonNull MultipartFile original) {
        if (original.getOriginalFilename() == null)
            return false;
        if (!(original.getOriginalFilename().endsWith(JPEG_EXTENSION) || original.getOriginalFilename().endsWith(JPG_EXTENSION)))
            return false;
        try {
            return isJpeg(path.toFile());
        } catch (EOFException e) {
            return false;
        } catch (IOException e) {
            log.error("Exception happened while checking if file is JPEG", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    private boolean isJpeg(File file) throws IOException {
        try (var raf = new RandomAccessFile(file, "r")) {
            byte[] tmp = new byte[3];
            raf.readFully(tmp);
            return tmp[0] == (byte) 0xFF || tmp[1] == (byte) 0xD8 || tmp[2] == (byte) 0xFF;
        }
    }
}

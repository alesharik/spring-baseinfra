package com.alesharik.spring.file.storage.verifier;

import com.alesharik.spring.file.storage.FileTypeVerifier;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.Arrays;

/**
 * This verifies pass only PNG images
 */
public class PngVerifier implements FileTypeVerifier {
    private static final String PNG_EXTENSION = ".png";
    private static final byte[] PNG_HEADER = new byte[] {(byte) 137, 80, 78, 71, 13, 10, 26, 10 };

    @Override
    public boolean verifyFile(@NonNull Path path, @NonNull MultipartFile original) {
        if (original.getOriginalFilename() == null)
            return false;
        if (!(original.getOriginalFilename().endsWith(PNG_EXTENSION)))
            return false;
        try {
            return hasPngHeader(path.toFile());
        } catch (EOFException e) {
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasPngHeader(File file) throws IOException {
        try (var raf = new RandomAccessFile(file, "r")) {
            byte[] tmp = new byte[8];
            raf.readFully(tmp);
            return Arrays.equals(tmp, PNG_HEADER);
        }
    }
}

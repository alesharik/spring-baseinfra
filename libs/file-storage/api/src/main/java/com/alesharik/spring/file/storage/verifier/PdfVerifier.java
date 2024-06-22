package com.alesharik.spring.file.storage.verifier;

import com.alesharik.spring.file.storage.FileTypeVerifier;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.util.Arrays;

public class PdfVerifier implements FileTypeVerifier {
    private static final String PDF_EXTENSION = ".pdf";
    private static final byte[] V12 = new byte[]{0x31, 0x2E, 0x32};
    private static final byte[] V13 = new byte[]{0x31, 0x2E, 0x33};
    private static final byte[] V14 = new byte[]{0x31, 0x2E, 0x34};
    private static final byte[] V14_FOOTER = new byte[]{0x25 ,0x25 ,0x45, 0x4F, 0x46, 0x0A}; // % % E O F EOL
    private static final byte[] V13_FOOTER = new byte[]{0x25 ,0x25 ,0x45 ,0x4F ,0x46 ,0x20 ,0x0A}; // % % E O F SPACE EOL
    private static final byte[] HEADER = new byte[]{0x25 ,0x50 ,0x44 ,0x46 ,0x2D}; // % P D F -

    @Override
    public boolean verifyFile(@NonNull Path path, @NonNull MultipartFile original) {
        if (original.getOriginalFilename() == null)
            return false;
        if (!(original.getOriginalFilename().endsWith(PDF_EXTENSION)))
            return false;
        try {
            return isPdf(path.toFile());
        } catch (EOFException e) {
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isPdf(File file) throws IOException {
        try (var raf = new RandomAccessFile(file, "r")) {
            byte[] tmp = new byte[5];
            raf.readFully(tmp);
            if (!Arrays.equals(tmp, HEADER)) {
                return false;
            }
            byte[] ver = new byte[3];
            raf.readFully(ver);
            if (Arrays.equals(ver, V13)) {
                raf.seek(raf.length() - V13_FOOTER.length);
                byte[] dat = new byte[V13_FOOTER.length];
                raf.readFully(dat);
                return Arrays.equals(dat, V13_FOOTER);
            } else if (Arrays.equals(ver, V14) || Arrays.equals(ver, V12)) {
                raf.seek(raf.length() - V14_FOOTER.length);
                byte[] dat = new byte[V14_FOOTER.length];
                raf.readFully(dat);
                return Arrays.equals(dat, V14_FOOTER);
            } else {
                return false;
            }
        }
    }
}

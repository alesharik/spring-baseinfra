package com.alesharik.spring.file.storage.verifier;

import com.alesharik.spring.file.storage.FileTypeVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

@RequiredArgsConstructor
public class AnyOfVerifier implements FileTypeVerifier {
    private final List<FileTypeVerifier> verifiers;

    @Override
    public boolean verifyFile(@NonNull Path path, @NonNull MultipartFile original) {
        for (FileTypeVerifier verifier : verifiers) {
            if (verifier.verifyFile(path, original)) {
                return true;
            }
        }
        return false;
    }
}

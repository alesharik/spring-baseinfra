package com.alesharik.spring.file.storage.verifier;

import com.alesharik.spring.file.storage.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JpegVerifierTest {
    private final JpegVerifier verifier = new JpegVerifier();

    @Test
    void shouldVerifyPng() throws IOException {
        var path = TestUtils.storeResource("image.jpg");
        var file = new MockMultipartFile("test", "test.jpg", "image/jpeg", new byte[]{});
        assertTrue(verifier.verifyFile(path, file));
        var file1 = new MockMultipartFile("test", "test.jpeg", "image/jpeg", new byte[]{});
        assertTrue(verifier.verifyFile(path, file1));
    }

    @Test
    void shouldNotVerifyFileWithWrongExt() throws IOException {
        var path = TestUtils.storeResource("image.jpg");
        var file = new MockMultipartFile("test", "test.png", "image/jpeg", new byte[]{});
        assertFalse(verifier.verifyFile(path, file));
    }

    @Test
    void shouldNotVerifyWrongFile() throws IOException {
        var path = TestUtils.storeResource("image.png");
        var file = new MockMultipartFile("test", "test.jpg", "image/jpeg", new byte[]{});
        assertFalse(verifier.verifyFile(path, file));
        var file1 = new MockMultipartFile("test", "test.jpeg", "image/jpeg", new byte[]{});
        assertFalse(verifier.verifyFile(path, file1));
    }
}

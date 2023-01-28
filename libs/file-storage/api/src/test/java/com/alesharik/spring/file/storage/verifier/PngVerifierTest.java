package com.alesharik.spring.file.storage.verifier;

import com.alesharik.spring.file.storage.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PngVerifierTest {
    private final PngVerifier verifier = new PngVerifier();

    @Test
    void shouldVerifyPng() throws IOException {
        var path = TestUtils.storeResource("image.png");
        var file = new MockMultipartFile("test", "test.png", "image/png", new byte[]{});
        assertTrue(verifier.verifyFile(path, file));
    }

    @Test
    void shouldNotVerifyFileWithWrongExt() throws IOException {
        var path = TestUtils.storeResource("image.png");
        var file = new MockMultipartFile("test", "test.jpg", "image/png", new byte[]{});
        assertFalse(verifier.verifyFile(path, file));
    }

    @Test
    void shouldNotVerifyWrongFile() throws IOException {
        var path = TestUtils.storeResource("image.jpg");
        var file = new MockMultipartFile("test", "test.png", "image/png", new byte[]{});
        assertFalse(verifier.verifyFile(path, file));
    }
}

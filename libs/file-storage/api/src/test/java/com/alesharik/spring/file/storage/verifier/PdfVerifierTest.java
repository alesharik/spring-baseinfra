package com.alesharik.spring.file.storage.verifier;

import com.alesharik.spring.file.storage.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PdfVerifierTest {
    private final PdfVerifier verifier = new PdfVerifier();

    @Test
    void shouldVerifyPdf() throws IOException {
        var path = TestUtils.storeResource("sample.pdf");
        var file = new MockMultipartFile("test", "test.pdf", "application/pdf", new byte[]{});
        assertTrue(verifier.verifyFile(path, file));
    }

    @Test
    void shouldNotVerifyFileWithWrongExt() throws IOException {
        var path = TestUtils.storeResource("sample.pdf");
        var file = new MockMultipartFile("test", "test.png", "application/pdf", new byte[]{});
        assertFalse(verifier.verifyFile(path, file));
    }

    @Test
    void shouldNotVerifyWrongFile() throws IOException {
        var path = TestUtils.storeResource("image.png");
        var file = new MockMultipartFile("test", "test.pdf", "application/pdf", new byte[]{});
        assertFalse(verifier.verifyFile(path, file));
    }
}
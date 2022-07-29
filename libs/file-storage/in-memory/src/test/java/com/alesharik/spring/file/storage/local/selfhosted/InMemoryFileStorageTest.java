package com.alesharik.spring.file.storage.local.selfhosted;

import com.alesharik.spring.file.storage.FileTypeVerifier;
import com.alesharik.spring.file.storage.SaveFileRequest;
import com.alesharik.spring.file.storage.exception.FileAlreadyExistsException;
import com.alesharik.spring.file.storage.exception.FileNotFoundException;
import com.alesharik.spring.file.storage.exception.FileVerificationFailedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = InMemoryFileStorageConfiguration.class)
class InMemoryFileStorageTest {
    @Autowired
    private InMemoryFileStorage storage;

    @Test
    void shouldUseOverriddenName() {
        var itemId = UUID.randomUUID().toString();
        var filename = storage.saveFile(
                SaveFileRequest.builder()
                        .type("test")
                        .itemId(itemId)
                        .file(new MockMultipartFile("test", "asd.txt", "text/plain", "Test 123".getBytes(StandardCharsets.UTF_8)))
                        .filenameOverride("test.txt")
                        .build()
        );
        assertThat(filename)
                .isEqualTo("test.txt");
    }

    @Test
    void shouldGenerateRandomNameIfNoneIsProvided() {
        var itemId = UUID.randomUUID().toString();
        var filename = storage.saveFile(
                SaveFileRequest.builder()
                        .type("test")
                        .itemId(itemId)
                        .file(new MockMultipartFile("test", null, "text/plain", "Test 123".getBytes(StandardCharsets.UTF_8)))
                        .build()
        );
        assertThat(filename)
                .isNotBlank()
                .endsWith(".dat");
    }

    @Test
    void shouldThrowExceptionIfFileExists() {
        var itemId = UUID.randomUUID().toString();
        var filename = storage.saveFile(
                SaveFileRequest.builder()
                        .type("test")
                        .itemId(itemId)
                        .file(new MockMultipartFile("test", "test", "text/plain", "Test 123".getBytes(StandardCharsets.UTF_8)))
                        .build()
        );
        assertThat(filename).isNotBlank();
        assertThatThrownBy(() -> storage.saveFile(
                SaveFileRequest.builder()
                        .type("test")
                        .itemId(itemId)
                        .file(new MockMultipartFile("test", "test", "text/plain", "Test 123".getBytes(StandardCharsets.UTF_8)))
                        .build()
        )).isInstanceOf(FileAlreadyExistsException.class);
    }

    @Test
    void shouldNotThrowExceptionIfFileOverrideIsEnabled() {
        var itemId = UUID.randomUUID().toString();
        var filename = storage.saveFile(
                SaveFileRequest.builder()
                        .type("test")
                        .itemId(itemId)
                        .file(new MockMultipartFile("test", null, "text/plain", "Test 123".getBytes(StandardCharsets.UTF_8)))
                        .build()
        );
        assertThat(filename).isNotBlank();
        storage.saveFile(
                SaveFileRequest.builder()
                        .type("test")
                        .itemId(itemId)
                        .ignoreExisting(true)
                        .file(new MockMultipartFile("test", null, "text/plain", "Test 123".getBytes(StandardCharsets.UTF_8)))
                        .build()
        );
    }

    @Test
    void shouldUseVerifier() {
        var itemId = UUID.randomUUID().toString();
        var goodVerifier = mock(FileTypeVerifier.class);
        when(goodVerifier.verifyFile(any(), any())).thenReturn(true);
        storage.saveFile(
                SaveFileRequest.builder()
                        .type("test")
                        .itemId(itemId)
                        .file(new MockMultipartFile("test", null, "text/plain", "Test 123".getBytes(StandardCharsets.UTF_8)))
                        .verifier(goodVerifier)
                        .build()
        );

        var badVerifier = mock(FileTypeVerifier.class);
        when(badVerifier.verifyFile(any(), any())).thenReturn(false);
        assertThatThrownBy(() -> storage.saveFile(
                SaveFileRequest.builder()
                        .type("test")
                        .itemId(itemId)
                        .file(new MockMultipartFile("test", null, "text/plain", "Test 123".getBytes(StandardCharsets.UTF_8)))
                        .verifier(badVerifier)
                        .build()
        ))
                .describedAs("Should throw exception when not passing verification")
                .isInstanceOf(FileVerificationFailedException.class);
    }

    @Test
    void shouldThrowExceptionIfNotFound() {
        assertThatThrownBy(() -> storage.getFile("test", "test", "test"))
                .isInstanceOf(FileNotFoundException.class);
    }

    @Test
    void shouldDeleteFile() {
        var itemId = UUID.randomUUID().toString();
        var filename = storage.saveFile(
                SaveFileRequest.builder()
                        .type("test")
                        .itemId(itemId)
                        .file(new MockMultipartFile("test", "test.txt", "text/plain", "Test 123".getBytes(StandardCharsets.UTF_8)))
                        .build()
        );
        storage.getFile("test", itemId, filename);
        storage.deleteFile("test", itemId, filename);
        assertThatThrownBy(() -> storage.getFile("test", itemId, filename))
                .isInstanceOf(FileNotFoundException.class);
    }

    @Test
    void shouldCreateLink() {
        var link = storage.getLink("test", "123", "asd.txt");
        assertThat(link).isEqualTo("http://localhost:3000/api/staticv1/test/123/asd.txt");
    }

    @Test
    void shouldThrowExceptionIfTryingToUseSeparatorsInGetFile() {
        assertThatThrownBy(() -> storage.getFile("test", "123", "/etc/passwd"))
                .isInstanceOf(FileNotFoundException.class);
    }

    @Test
    void shouldRemoveSeparatorsWhenSaving() {
        var itemId = UUID.randomUUID().toString();
        var filename = storage.saveFile(
                SaveFileRequest.builder()
                        .type("test")
                        .itemId(itemId)
                        .file(new MockMultipartFile("test", "../Downloads/test.txt", "text/plain", "Test 123".getBytes(StandardCharsets.UTF_8)))
                        .build()
        );
        assertThat(filename).isEqualTo("test.txt");
    }
}

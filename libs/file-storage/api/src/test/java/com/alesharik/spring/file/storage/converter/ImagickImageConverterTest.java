package com.alesharik.spring.file.storage.converter;

import com.alesharik.spring.file.storage.exception.FileConversionFailedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ImagickImageConverterTest {
    @Test
    @Timeout(1)
    void shouldConvertPngToJpg() {
        var classLoader = ImagickImageConverter.class.getClassLoader();
        var converter = new ImagickImageConverter("jpg");
        var converted = converter.convert(Objects.requireNonNull(classLoader.getResourceAsStream("image.png")));
        assertThat(converted).hasSameContentAs(classLoader.getResourceAsStream("image.jpg"));
    }

    @Test
    @Timeout(1)
    void shouldConvertAndRescalePngToJpg() {
        var classLoader = ImagickImageConverter.class.getClassLoader();
        var converter = new ImagickImageConverter("jpg");
        converter.setResizeWidth(50);
        var converted = converter.convert(Objects.requireNonNull(classLoader.getResourceAsStream("image.png")));
        assertThat(converted).hasSameContentAs(classLoader.getResourceAsStream("image_50.jpg"));
    }

    @Test
    @Timeout(1)
    void shouldFailIfInputIsNotAnImage() {
        assertThatThrownBy(() -> {
            var classLoader = ImagickImageConverter.class.getClassLoader();
            var converter = new ImagickImageConverter("jpg");
            converter.convert(Objects.requireNonNull(classLoader.getResourceAsStream("test.txt")));
        }).isInstanceOf(FileConversionFailedException.class);
    }
}

package com.alesharik.spring.file.storage.converter;

import com.alesharik.spring.file.storage.FileConverter;
import com.alesharik.spring.file.storage.exception.FileConversionFailedException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class ImagickImageConverter implements FileConverter {
    private final String targetFormat;
    @Setter
    @Getter
    private int resizeWidth = 0;
    @Setter
    @Getter
    private Duration timeout = Duration.ofSeconds(5);

    @NonNull
    @Override
    public InputStream convert(@NonNull InputStream file) {
        try {
            var cmd = buildCommand();
            var command = new ProcessBuilder().command(cmd).start();
            writeFileToCommand(file, command);
            checkSuccessful(command);
            return command.getInputStream();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeFileToCommand(InputStream file, Process command) throws IOException {
        file.transferTo(command.getOutputStream());
        command.getOutputStream().close();
    }

    private void checkSuccessful(Process command) throws InterruptedException {
        if (!command.waitFor(timeout.toMillis(), TimeUnit.MILLISECONDS)) {
            throw new FileConversionFailedException();
        }
        if (command.exitValue() != 0) {
            throw new FileConversionFailedException();
        }
    }

    @NotNull
    private String[] buildCommand() {
        var cmd = "convert ";
        if (resizeWidth > 0) {
            cmd += "-thumbnail " + resizeWidth + " ";
        }
        cmd += "- " + targetFormat + ":-";
        return cmd.split(" ");
    }
}

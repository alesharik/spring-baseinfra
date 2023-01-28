package com.alesharik.spring.file.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class TestUtils {
    public static Path storeResource(String resource) throws IOException {
        var classLoader = TestUtils.class.getClassLoader();
        try (var res = classLoader.getResourceAsStream(resource)) {
            assert res != null;

            var resourceParts = resource.split("/");
            var temp = Files.createTempFile("alesharikspring", resourceParts[resourceParts.length - 1]);
            try (var writer = Files.newOutputStream(temp, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
                res.transferTo(writer);
            }

            return temp;
        }
    }
}

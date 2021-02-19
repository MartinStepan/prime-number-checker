package sdc.primenumberchecker.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sdc.primenumberchecker.exceptions.ExtensionException;
import sdc.primenumberchecker.utils.FileUtils;

@SpringBootTest
public class FileUtilsTest {

    @Autowired
    FileUtils fileUtils;

    @Test
    public void checkFileWithNoExtension1() {
        Assertions.assertThrows(ExtensionException.class, () -> fileUtils.checkFileExtension("file"));
    }

    @Test
    public void checkFileWithNoExtension2() {
        Assertions.assertThrows(ExtensionException.class, () -> fileUtils.checkFileExtension("file."));
    }

    @Test
    public void checkFileWithUnsupportedExtension() {
        Assertions.assertThrows(ExtensionException.class, () -> fileUtils.checkFileExtension("file.x"));
    }

    @Test
    public void checkFileWithSupportedExtension() {
        Assertions.assertDoesNotThrow(() -> fileUtils.checkFileExtension("file.xlsx"));
    }
}

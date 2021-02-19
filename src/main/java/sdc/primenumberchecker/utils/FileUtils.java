package sdc.primenumberchecker.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sdc.primenumberchecker.exceptions.ExtensionException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class FileUtils {

    @Value("#{'${file.extension.support}'.split(',')}")
    List<String> fileExtension;

    /** Check if is entered file with correct extension
     *
     * @param filename
     * @throws ExtensionException if no extension defined or not supported extension
     */
    public void checkFileExtension(String filename) throws ExtensionException {
        if(!filename.contains(".")) {
            throw new ExtensionException("You must specify file with extension! Entered filename: " + filename);
        }

        String extension = StringUtils.substring(filename, filename.lastIndexOf(".")+1);
        if(extension == null || extension.isEmpty()) {
            throw new ExtensionException("No file extension specified! Entered filename: " + filename);
        }

        if(!fileExtension.contains(extension)) {
            throw new ExtensionException("File extension "+extension+" is not supported");
        }
    }

    public boolean fileExist(String filename) {
        Path path = Paths.get(filename);
        return Files.exists(path) && !Files.isDirectory(path);
    }
}

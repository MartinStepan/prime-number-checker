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
        String fName;

        //handle relative path
        fName = handleRelativePath(filename, "/", "\\");
        if(fName.equals(filename)) {
            fName = handleRelativePath(filename, "\\", "/");
        }

        if(!fName.contains(".")) {
            throw new ExtensionException("You must specify file with extension! Entered filename: " + filename);
        }

        String extension = StringUtils.substring(fName, fName.lastIndexOf(".")+1);
        if(extension == null || extension.isEmpty()) {
            throw new ExtensionException("No file extension specified! Entered filename: " + filename);
        }

        if(!fileExtension.contains(extension)) {
            throw new ExtensionException("File extension "+extension+" is not supported");
        }
    }

    private String handleRelativePath(String filename, String firstCheck, String secondCheck) {
        String fNameFirstCheck;
        String fNameSecondCheck;
        if(StringUtils.contains(filename, firstCheck)) {
            fNameFirstCheck = StringUtils.substring(filename, filename.lastIndexOf(firstCheck) + 1, filename.length());
            if (fNameFirstCheck != null && !fNameFirstCheck.isEmpty()) {
                if (StringUtils.contains(fNameFirstCheck, secondCheck)) {
                    fNameSecondCheck = StringUtils.substring(fNameFirstCheck, fNameFirstCheck.lastIndexOf(secondCheck) + 1, fNameFirstCheck.length());
                    return fNameSecondCheck;
                } else {
                    return fNameFirstCheck;
                }
            } else {
                return fNameFirstCheck;
            }
        } else {
            return filename;
        }
    }

    public boolean fileExist(String filename) {
        Path path = Paths.get(filename);
        return Files.exists(path) && !Files.isDirectory(path);
    }
}

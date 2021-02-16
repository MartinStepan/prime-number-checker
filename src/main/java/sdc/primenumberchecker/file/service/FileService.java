package sdc.primenumberchecker.file.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sdc.primenumberchecker.file.parser.FileParser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class FileService {

    static final Logger log = LoggerFactory.getLogger(FileService.class);

    @Autowired
    @Qualifier("excelParser")
    FileParser parser;

    @Value("#{'${file.extension.support}'.split(',')}")
    List<String> fileExtension;

    public void processFile(String filename) {
        if(!checkFileExtension(filename)) {
            return;
        }

        if(!fileExist(filename)) {
            log.error("File {} not found", filename);
            return;
        }

        parser.readFile(filename);
    }

    private boolean checkFileExtension(String filename) {
        if(!filename.contains(".")) {
            log.error("You must specify file with extension! Entered filename: {}", filename);
            return false;
        }

        String extension = StringUtils.substring(filename, filename.lastIndexOf(".")+1);
        if(extension == null || extension.isEmpty()) {
            log.error("No file extension specified! Entered filename: {}", filename);
            return false;
        }

        boolean supportedExtension = fileExtension.contains(extension) ? true:false;

        if(!supportedExtension) {
            log.error("File extension {} is not supported", extension);
        }

        return supportedExtension;
    }

    private boolean fileExist(String filename) {
        Path path = Paths.get(filename);
        return Files.exists(path) && !Files.isDirectory(path);
    }
}

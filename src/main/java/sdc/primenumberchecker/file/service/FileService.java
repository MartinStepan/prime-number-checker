package sdc.primenumberchecker.file.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sdc.primenumberchecker.exceptions.ExtensionException;
import sdc.primenumberchecker.file.parser.FileParser;
import sdc.primenumberchecker.utils.FileUtils;

@Component
public class FileService {

    static final Logger log = LoggerFactory.getLogger(FileService.class);

    @Autowired
    @Qualifier("excelParser")
    FileParser parser;

    @Autowired
    FileUtils fileUtils;

    public void processFile(String filename) {
        try {
            fileUtils.checkFileExtension(filename);
        } catch (ExtensionException e) {
            log.error(e.toString());
            return;
        }

        if(!fileUtils.fileExist(filename)) {
            log.error("File {} not found", filename);
            return;
        }

        parser.processFile(filename);
    }
}

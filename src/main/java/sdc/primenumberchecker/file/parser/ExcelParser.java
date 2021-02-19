package sdc.primenumberchecker.file.parser;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sdc.primenumberchecker.algorithm.MillerRabinPrimeTest;

import java.io.IOException;

@Component
public class ExcelParser implements FileParser {

    static final Logger log = LoggerFactory.getLogger(ExcelParser.class);

    @Value("${prime.test.cycle.count}")
    private int cycleCount;

    @Autowired
    private MillerRabinPrimeTest primeTest;

    @Override
    public void processFile(String filename) {
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(filename);
        } catch (IOException e) {
            log.error("Error while reading file {}: {}", filename, e.toString());
        }

        if(workbook.getNumberOfSheets() < 1) {
            log.error("No sheets found in file {}", filename);
            return;
        }

        Sheet sheet = workbook.getSheetAt(0);

        log.info("Read numbers from file: {}",filename);
        log.info("--------------------------");

        for(Row row : sheet) {
            if(row != null) {
                for(Cell cell : row) {
                    if(cell != null) {
                        if(StringUtils.isNumeric(cell.getStringCellValue())) {
                            long value = Long.valueOf(cell.getStringCellValue());
                            if(value < 0) {
                                log.debug("Value {} is not valid - ignoring", value);
                            }
                            else {
                                boolean result = primeTest.isNumberPrime(value, cycleCount);
                                log.info("Number {} is {}", value, result ? "probably prime " + "(probability " + (100 - (1 / (Math.pow(4, cycleCount)) * 100)) + "%)" : "not prime");
                            }
                        }
                    }
                }
            }
        }
    }

}

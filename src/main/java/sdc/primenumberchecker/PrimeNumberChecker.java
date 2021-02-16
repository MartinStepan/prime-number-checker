package sdc.primenumberchecker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sdc.primenumberchecker.file.service.FileService;

@SpringBootApplication
public class PrimeNumberChecker implements CommandLineRunner {

	static final Logger log = LoggerFactory.getLogger(PrimeNumberChecker.class);

	@Autowired
	FileService fileService;

	public static void main(String[] args) {
		SpringApplication.run(PrimeNumberChecker.class, args);
	}

	@Override
	public void run(String... args) {
		if(args.length > 0) {
			fileService.processFile(args[0]);
		} else {
			log.error("No file name specified");
		}
	}
}

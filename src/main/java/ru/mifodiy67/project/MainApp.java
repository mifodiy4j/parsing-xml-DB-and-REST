package ru.mifodiy67.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.SAXException;
import ru.mifodiy67.project.service.ParsingService;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@SpringBootApplication
public class MainApp {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException,
            ClassNotFoundException {

        SpringApplication.run(MainApp.class, args);

        String fileName = "";
        if (args.length > 0) {
            fileName = args[0];
        }
        ParsingService service = ParsingService.getInstance();
        service.parsingAndSaveFile(fileName);
    }
}

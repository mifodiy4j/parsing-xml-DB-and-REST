package ru.mifodiy67.project.service;

import org.xml.sax.SAXException;
import ru.mifodiy67.project.dao.ItemDao;
import ru.mifodiy67.project.model.Box;
import ru.mifodiy67.project.model.Item;
import ru.mifodiy67.project.util.ParsingFile;
import ru.mifodiy67.project.dao.BoxDao;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Set;

public class ParsingService {

    private static final ParsingService instance = new ParsingService();

    private ParsingService() {}

    public void parsingAndSaveFile(String fileName) throws IOException, SAXException, ParserConfigurationException, ClassNotFoundException {
        ParsingFile parsingFile = new ParsingFile(fileName);
        Set<Box> boxes = parsingFile.getBoxes();
        Set<Item> items = parsingFile.getItems();
        BoxDao boxDao = new BoxDao();
        ItemDao itemDao = new ItemDao();
        boxDao.saveAll(boxes);
        itemDao.saveAll(items);
    }

    public static ParsingService getInstance() {
        return instance;
    }
}

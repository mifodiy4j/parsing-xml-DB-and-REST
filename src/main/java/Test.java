import model.Box;
import model.Item;
import org.xml.sax.SAXException;
import util.ParsingFile;
import util.SaveToDb;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Set;

public class Test {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException,
            ClassNotFoundException {
        String fileName = "";
        if (args.length > 0) {
            fileName = args[0];
        }
        ParsingFile parsingFile = new ParsingFile(fileName);
        Set<Box> boxes = parsingFile.getBoxes();
        Set<Item> items = parsingFile.getItems();
        SaveToDb saveToDb = new SaveToDb();
        saveToDb.boxSave(boxes);
        saveToDb.itemSave(items);
    }
}

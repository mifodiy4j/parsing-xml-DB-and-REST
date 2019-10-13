package util;

import model.Box;
import model.Item;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

public class ParsingFile {

    private Document doc;

    public ParsingFile(String fileName) throws ParserConfigurationException, IOException, SAXException {
        File fXmlFile = new File(fileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();
    }

    public Set<Box> getBoxes() {
        NodeList nList = doc.getElementsByTagName("Box");
        Set<Box> boxes = new TreeSet<>();
        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;

                String id = eElement.getAttribute("id");

                Node parentNode = eElement.getParentNode();

                String parentId = null;
                if (parentNode.getNodeName().equals("Box")) {
                    Element parentElement = (Element) parentNode;
                    parentId = parentElement.getAttribute("id");
                }

                Integer containedIn = parentId == null ? null : Integer.parseInt(parentId);
                Box box = new Box(Integer.parseInt(id), containedIn);

                boxes.add(box);
            }
        }
        return boxes;
    }

    public Set<Item> getItems() {
        NodeList nList = doc.getElementsByTagName("Item");
        Set<Item> items = new TreeSet<>();
        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;

                String id = eElement.getAttribute("id");

                String colorAttribute = eElement.getAttribute("color");
                String color = colorAttribute.isEmpty() ? null : colorAttribute;

                Node parentNode = eElement.getParentNode();

                String parentId = null;
                if (parentNode.getNodeName().equals("Box")) {
                    Element parentElement = (Element) parentNode;
                    parentId = parentElement.getAttribute("id");
                }

                Integer containedIn = parentId == null ? null : Integer.parseInt(parentId);
                Item item = new Item(Integer.parseInt(id), containedIn);
                item.setCololr(color);

                items.add(item);
            }
        }
        return items;
    }
}

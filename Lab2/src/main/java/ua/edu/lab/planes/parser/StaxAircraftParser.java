package ua.edu.lab.planes.parser;

import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ua.edu.lab.planes.model.Aircraft;

public final class StaxAircraftParser extends XmlAircraftParser {
    private static final Logger LOGGER = Logger.getLogger(StaxAircraftParser.class.getName());

    public List<Aircraft> parse(Path xml) throws Exception {
        XMLInputFactory factory = XMLInputFactory.newFactory();
        factory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
        factory.setProperty("javax.xml.stream.isSupportingExternalEntities", false);
        XMLStreamReader reader = factory.createXMLStreamReader(Files.newInputStream(xml));
        List<Aircraft> result = new ArrayList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        Document doc = dbf.newDocumentBuilder().newDocument();
        Element current = null;
        java.util.Deque<Element> stack = new java.util.ArrayDeque<>();
        while (reader.hasNext()) {
            int event = reader.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                String name = reader.getLocalName();
                if (name.equals("Aircraft")) {
                    current = doc.createElement("Aircraft");
                    current.setAttribute("id", reader.getAttributeValue(null, "id"));
                    stack.clear();
                } else if (current != null) {
                    Element e = doc.createElement(name);
                    (stack.isEmpty() ? current : stack.peek()).appendChild(e);
                    stack.push(e);
                }
            } else if (event == XMLStreamConstants.CHARACTERS && current != null && !stack.isEmpty()) {
                stack.peek().appendChild(doc.createTextNode(reader.getText()));
            } else if (event == XMLStreamConstants.END_ELEMENT) {
                String name = reader.getLocalName();
                if (current != null && !stack.isEmpty()) stack.pop();
                if (name.equals("Aircraft") && current != null) {
                    result.add(parseAircraft(current));
                    current = null;
                    stack.clear();
                }
            }
        }
        reader.close();
        LOGGER.info(() -> "StAX: прочитано літаків: " + result.size());
        return result;
    }
}

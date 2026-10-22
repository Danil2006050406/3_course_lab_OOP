package ua.edu.lab.planes.parser;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import java.io.IOException;
import ua.edu.lab.planes.model.Aircraft;

public final class SaxAircraftParser extends XmlAircraftParser {
    private static final Logger LOGGER = Logger.getLogger(SaxAircraftParser.class.getName());

    public List<Aircraft> parse(Path xml) throws ParserConfigurationException, SAXException, IOException {
        var factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(false);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        var builder = factory.newSAXParser();
        List<Aircraft> result = new ArrayList<>();
        builder.parse(new InputSource(xml.toUri().toString()), new DefaultHandler() {
            private org.w3c.dom.Document currentDoc;
            private org.w3c.dom.Element currentAircraft;
            private final StringBuilder buffer = new StringBuilder();
            private final javax.xml.parsers.DocumentBuilder documentBuilder = createBuilder();

            private javax.xml.parsers.DocumentBuilder createBuilder() {
                try { return javax.xml.parsers.DocumentBuilderFactory.newInstance().newDocumentBuilder(); }
                catch (Exception e) { throw new IllegalStateException(e); }
            }

            @Override public void startElement(String uri, String localName, String qName, Attributes attributes) {
                buffer.setLength(0);
                if (qName.equals("Aircraft")) {
                    currentDoc = documentBuilder.newDocument();
                    currentAircraft = currentDoc.createElement("Aircraft");
                    currentAircraft.setAttribute("id", attributes.getValue("id"));
                    currentDoc.appendChild(currentAircraft);
                } else if (currentAircraft != null) {
                    org.w3c.dom.Element e = currentDoc.createElement(qName);
                    if (qName.equals("Char") || qName.equals("Chars") || qName.equals("Parameters") || qName.equals("Armament")) {
                        org.w3c.dom.Element parent = currentAircraft;
                        // Place element under its current XML parent using a small explicit stack.
                        if (!stack.isEmpty()) parent = stack.peek();
                        parent.appendChild(e);
                    } else {
                        org.w3c.dom.Element parent = stack.isEmpty() ? currentAircraft : stack.peek();
                        parent.appendChild(e);
                    }
                    stack.push(e);
                }
            }

            private final java.util.Deque<org.w3c.dom.Element> stack = new java.util.ArrayDeque<>();

            @Override public void characters(char[] ch, int start, int length) {
                buffer.append(ch, start, length);
            }

            @Override public void endElement(String uri, String localName, String qName) {
                if (currentAircraft == null) return;
                if (!buffer.toString().trim().isEmpty() && !stack.isEmpty()) {
                    stack.peek().appendChild(currentDoc.createTextNode(buffer.toString()));
                }
                if (!stack.isEmpty()) stack.pop();
                if (qName.equals("Aircraft")) {
                    result.add(parseAircraft(currentAircraft));
                    currentAircraft = null;
                    currentDoc = null;
                    stack.clear();
                }
                buffer.setLength(0);
            }
        });
        LOGGER.info(() -> "SAX: прочитано літаків: " + result.size());
        return result;
    }
}

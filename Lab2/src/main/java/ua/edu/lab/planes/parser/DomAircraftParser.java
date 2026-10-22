package ua.edu.lab.planes.parser;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import ua.edu.lab.planes.model.Aircraft;
import org.w3c.dom.Element;

public final class DomAircraftParser extends XmlAircraftParser {
    private static final Logger LOGGER = Logger.getLogger(DomAircraftParser.class.getName());

    public List<Aircraft> parse(Path xml) throws Exception {
        var factory = DocumentBuilderFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        var document = factory.newDocumentBuilder().parse(xml.toFile());
        List<Aircraft> result = new ArrayList<>();
        for (Element element : children(document.getDocumentElement(), "Aircraft")) {
            result.add(parseAircraft(element));
        }
        LOGGER.info(() -> "DOM: прочитано літаків: " + result.size());
        return result;
    }
}

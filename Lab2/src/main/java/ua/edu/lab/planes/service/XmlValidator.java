package ua.edu.lab.planes.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

public final class XmlValidator {
    private static final Logger LOGGER = Logger.getLogger(XmlValidator.class.getName());

    private XmlValidator() {}

    public static void validate(Path xml, Path xsd) throws SAXException, IOException {
        var factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        var schema = factory.newSchema(xsd.toFile());
        var validator = schema.newValidator();
        validator.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        validator.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        validator.validate(new StreamSource(xml.toFile()));
        LOGGER.info(() -> "XML успішно пройшов валідацію: " + xml);
    }
}

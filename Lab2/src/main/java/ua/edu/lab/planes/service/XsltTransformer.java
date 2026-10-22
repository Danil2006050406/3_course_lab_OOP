package ua.edu.lab.planes.service;

import java.nio.file.Path;
import java.util.logging.Logger;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public final class XsltTransformer {
    private static final Logger LOGGER = Logger.getLogger(XsltTransformer.class.getName());
    private XsltTransformer() {}

    public static void transform(Path xml, Path xsl, Path output) throws Exception {
        var factory = TransformerFactory.newInstance();
        factory.setFeature(javax.xml.XMLConstants.FEATURE_SECURE_PROCESSING, true);
        var transformer = factory.newTransformer(new StreamSource(xsl.toFile()));
        transformer.transform(new StreamSource(xml.toFile()), new StreamResult(output.toFile()));
        LOGGER.info(() -> "XSLT-результат збережено: " + output);
    }
}

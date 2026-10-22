package ua.edu.lab.planes;

import ua.edu.lab.planes.model.Aircraft;
import ua.edu.lab.planes.parser.DomAircraftParser;
import ua.edu.lab.planes.parser.SaxAircraftParser;
import ua.edu.lab.planes.parser.StaxAircraftParser;
import ua.edu.lab.planes.service.AircraftSorters;
import ua.edu.lab.planes.service.XmlValidator;
import ua.edu.lab.planes.service.XsltTransformer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public final class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    private Main() {}

    public static void main(String[] args) {
        configureLogging();
        Path resources = Path.of("src/main/resources");
        Path xml = resources.resolve("planes.xml");
        Path xsd = resources.resolve("planes.xsd");
        Path xsl = resources.resolve("planes.xsl");
        try {
            XmlValidator.validate(xml, xsd);
            List<Aircraft> aircraft = new DomAircraftParser().parse(xml);
            LOGGER.info("Приклад SAX: " + new SaxAircraftParser().parse(xml).size() + " літаків");
            LOGGER.info("Приклад StAX: " + new StaxAircraftParser().parse(xml).size() + " літаків");

            List<Aircraft> sorted = new ArrayList<>(aircraft);
            sorted.sort(AircraftSorters.BY_PRICE);
            System.out.println("\nВійськові літаки (сортування за ціною):");
            for (Aircraft plane : sorted) {
                System.out.printf("%s | %s | %s | %s талерів%n",
                        plane.id(), plane.model(), plane.origin(), plane.price().toPlainString());
            }

            Path output = Path.of("target", "planes-by-origin.xml");
            java.nio.file.Files.createDirectories(output.getParent());
            XsltTransformer.transform(xml, xsl, output);
            System.out.println("\nРезультат XSLT: " + output.toAbsolutePath());
        } catch (Exception exception) {
            LOGGER.log(Level.SEVERE, "Помилка під час обробки XML", exception);
            System.exit(1);
        }
    }

    private static void configureLogging() {
        Logger root = Logger.getLogger("");
        root.setLevel(Level.INFO);
        for (Handler handler : root.getHandlers()) handler.setLevel(Level.INFO);
    }
}

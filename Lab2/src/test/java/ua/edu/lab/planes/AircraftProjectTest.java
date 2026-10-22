package ua.edu.lab.planes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ua.edu.lab.planes.model.*;
import ua.edu.lab.planes.parser.DomAircraftParser;
import ua.edu.lab.planes.parser.SaxAircraftParser;
import ua.edu.lab.planes.parser.StaxAircraftParser;
import ua.edu.lab.planes.service.AircraftSorters;
import ua.edu.lab.planes.service.XmlValidator;
import ua.edu.lab.planes.service.XsltTransformer;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class AircraftProjectTest {
    private static final Path RESOURCES = Path.of("src/main/resources");

    @Test
    void xsdValidationAcceptsSampleXml() throws Exception {
        assertDoesNotThrow(() -> XmlValidator.validate(
                RESOURCES.resolve("planes.xml"), RESOURCES.resolve("planes.xsd")));
    }

    @Test
    void allParsersReadSameAircraftCount() throws Exception {
        int dom = new DomAircraftParser().parse(RESOURCES.resolve("planes.xml")).size();
        int sax = new SaxAircraftParser().parse(RESOURCES.resolve("planes.xml")).size();
        int stax = new StaxAircraftParser().parse(RESOURCES.resolve("planes.xml")).size();
        assertEquals(3, dom);
        assertEquals(dom, sax);
        assertEquals(dom, stax);
    }

    @Test
    void priceComparatorSortsAscending() throws Exception {
        var aircraft = new DomAircraftParser().parse(RESOURCES.resolve("planes.xml"));
        aircraft.sort(AircraftSorters.BY_PRICE);
        assertEquals("U-2 Dragon Lady", aircraft.get(0).model());
        assertEquals("F-16 Fighting Falcon", aircraft.get(aircraft.size() - 1).model());
    }

    @Test
    void xsltGroupsPlanesByOrigin(@TempDir Path tempDir) throws Exception {
        Path output = tempDir.resolve("grouped.xml");
        XsltTransformer.transform(RESOURCES.resolve("planes.xml"), RESOURCES.resolve("planes.xsl"), output);
        String result = java.nio.file.Files.readString(output);
        assertTrue(result.contains("PlanesByOrigin"));
        assertTrue(result.contains("Country"));
        assertTrue(result.contains("USA"));
        assertTrue(result.contains("Ukraine"));
    }

    @Test
    void invalidSeatCountIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new PlaneCharacteristic(PlaneType.FIGHTER, 3,
                        Optional.of(new Armament(2, true))));
    }

    @Test
    void reconnaissancePlaneCannotHaveArmament() {
        assertThrows(IllegalArgumentException.class,
                () -> new PlaneCharacteristic(PlaneType.RECONNAISSANCE, 1,
                        Optional.of(new Armament(0, false))));
    }

    @Test
    void missileCountMustBeInRange() {
        assertThrows(IllegalArgumentException.class, () -> new Armament(11, true));
    }

    @Test
    void dimensionsMustBePositive() {
        assertThrows(IllegalArgumentException.class, () -> new Dimensions(0, 10, 5));
    }

    @Test
    void negativePriceIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Aircraft(
                "P999", "Test", "Testland",
                java.util.List.of(
                        new PlaneCharacteristic(PlaneType.RECONNAISSANCE, 1, Optional.empty()),
                        new PlaneCharacteristic(PlaneType.RECONNAISSANCE, 2, Optional.empty())),
                new Dimensions(10, 10, 5), new BigDecimal("-1")));
    }
}

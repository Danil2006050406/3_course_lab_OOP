package ua.edu.lab.planes.parser;

import ua.edu.lab.planes.model.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public abstract class XmlAircraftParser {
    protected Aircraft parseAircraft(Element element) {
        String id = element.getAttribute("id");
        String model = text(element, "Model");
        String origin = text(element, "Origin");
        Element charsElement = child(element, "Chars");
        List<PlaneCharacteristic> chars = new ArrayList<>();
        for (Element charElement : children(charsElement, "Char")) {
            PlaneType type = PlaneType.fromXml(text(charElement, "Type"));
            int seats = Integer.parseInt(text(charElement, "Seats"));
            Element armamentElement = optionalChild(charElement, "Armament");
            Optional<Armament> armament = Optional.empty();
            if (armamentElement != null) {
                armament = Optional.of(new Armament(
                        Integer.parseInt(text(armamentElement, "Missiles")),
                        Boolean.parseBoolean(text(armamentElement, "Radar"))));
            }
            chars.add(new PlaneCharacteristic(type, seats, armament));
        }
        Element parameters = child(element, "Parameters");
        Dimensions dimensions = new Dimensions(
                Double.parseDouble(text(parameters, "Length")),
                Double.parseDouble(text(parameters, "Width")),
                Double.parseDouble(text(parameters, "Height")));
        return new Aircraft(id, model, origin, chars, dimensions, new BigDecimal(text(element, "Price")));
    }

    protected static String text(Element parent, String tag) {
        return child(parent, tag).getTextContent().trim();
    }

    protected static Element child(Element parent, String tag) {
        Element result = optionalChild(parent, tag);
        if (result == null) throw new IllegalArgumentException("Відсутній обов'язковий елемент: " + tag);
        return result;
    }

    protected static Element optionalChild(Element parent, String tag) {
        for (Node node = parent.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node instanceof Element e && e.getTagName().equals(tag)) return e;
        }
        return null;
    }

    protected static List<Element> children(Element parent, String tag) {
        List<Element> result = new ArrayList<>();
        for (Node node = parent.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node instanceof Element e && e.getTagName().equals(tag)) result.add(e);
        }
        return result;
    }
}

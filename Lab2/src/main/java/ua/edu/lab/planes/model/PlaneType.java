package ua.edu.lab.planes.model;

public enum PlaneType {
    SUPPORT("Support"),
    ESCORT("Escort"),
    FIGHTER("Fighter"),
    INTERCEPTOR("Interceptor"),
    RECONNAISSANCE("Reconnaissance");

    private final String xmlValue;

    PlaneType(String xmlValue) {
        this.xmlValue = xmlValue;
    }

    public String xmlValue() {
        return xmlValue;
    }

    public static PlaneType fromXml(String value) {
        for (PlaneType type : values()) {
            if (type.xmlValue.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Невідомий тип літака: " + value);
    }
}

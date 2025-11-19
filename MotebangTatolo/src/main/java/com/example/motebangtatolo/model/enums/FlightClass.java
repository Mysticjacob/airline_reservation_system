// File: src/main/java/com/example/motebangtatolo/model/enums/FlightClass.java

package com.example.motebangtatolo.model.enums;

/**
 * Enum representing different flight classes.
 */
public enum FlightClass {
    ECONOMIC("Economic"),
    EXECUTIVE("Executive");

    private final String displayName;

    FlightClass(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    // Optional: Method to get enum from string (useful for parsing from UI/database)
    public static FlightClass fromString(String value) {
        for (FlightClass flightClass : FlightClass.values()) {
            if (flightClass.name().equalsIgnoreCase(value) || flightClass.displayName.equalsIgnoreCase(value)) {
                return flightClass;
            }
        }
        throw new IllegalArgumentException("No FlightClass enum constant with value: " + value);
    }
}
// File: src/main/java/com/example/motebangtatolo/model/enums/ConcessionType.java

package com.example.motebangtatolo.model.enums;

/**
 * Enum representing different customer concession types and their associated discount rates.
 * Used for calculating final fare based on customer category.
 */
public enum ConcessionType {
    NONE("None", 0.0),
    STUDENT("Student", 0.10), // 10% discount
    SENIOR_CITIZEN("Senior Citizen", 0.15), // 15% discount
    CANCER_PATIENT("Cancer Patient", 0.25); // 25% discount

    private final String displayName;
    private final double discountRate;

    ConcessionType(String displayName, double discountRate) {
        this.displayName = displayName;
        this.discountRate = discountRate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getDiscountRate() {
        return discountRate;
    }

    // Optional: Method to get enum from string (useful for parsing from UI/database)
    public static ConcessionType fromString(String value) {
        for (ConcessionType type : ConcessionType.values()) {
            if (type.name().equalsIgnoreCase(value) || type.displayName.equalsIgnoreCase(value)) {
                return type;
            }
        }
        // Default to NONE if no match is found (robustness)
        return NONE;
    }
}
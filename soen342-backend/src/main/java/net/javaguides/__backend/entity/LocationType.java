package net.javaguides.__backend.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum LocationType {
    PARK,
    GYM,
    BEACH,
    SOCCER_FOOTBALL_FIELD,
    POOL;

    @JsonCreator
    public static LocationType fromString(String value) {
        return LocationType.valueOf(value.toUpperCase());  // Makes it case-insensitive
    }
}

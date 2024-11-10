package net.javaguides.__backend.Mapper;

import net.javaguides.__backend.dto.OfferingDto;
import net.javaguides.__backend.entity.Offering;

public class OfferingMapper {

    public static OfferingDto mapToOfferingDto(Offering offering) {
        return new OfferingDto(
                offering.getId(),
                offering.getLesson().getId(),    // Assuming lesson is a referenced entity
                offering.getInstructor().getId(), // Assuming instructor is a referenced entity
                offering.getTimeSlot().getId(),   // Assuming timeSlot is a referenced entity
                offering.isAvailable()
        );
    }

    public static Offering mapToOffering(OfferingDto offeringDto) {
        return new Offering(
                offeringDto.getId(),
                null, // Set lesson from DB or other way
                null, // Set instructor from DB or other way
                null, // Set timeSlot from DB or other way
                offeringDto.isAvailable()
        );
    }
}

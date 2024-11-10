package net.javaguides.__backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

    private Long id;            // The ID of the booking
    private Long clientId;      // The ID of the associated Client
    private Long offeringId;    // The ID of the associated Offering
    private boolean isActive;   // Status flag indicating if the booking is active
}

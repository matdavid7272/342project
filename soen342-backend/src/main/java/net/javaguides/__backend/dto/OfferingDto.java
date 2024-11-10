package net.javaguides.__backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OfferingDto {

    private Long id;
    private Long lessonId;       // The ID of the associated Lesson
    private Long instructorId;   // The ID of the associated Instructor
    private Long timeSlotId;     // The ID of the associated TimeSlot
    private boolean isAvailable;
}

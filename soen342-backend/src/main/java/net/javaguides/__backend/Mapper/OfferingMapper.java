package net.javaguides.__backend.Mapper;

import net.javaguides.__backend.dto.OfferingDto;
import net.javaguides.__backend.entity.Offering;
import net.javaguides.__backend.entity.Lesson;
import net.javaguides.__backend.entity.Instructor;
import net.javaguides.__backend.entity.TimeSlot;
import net.javaguides.__backend.entity.Location;
import net.javaguides.__backend.exception.ResourceNotFoundException;
import net.javaguides.__backend.repository.LessonRepository;
import net.javaguides.__backend.repository.InstructorRepository;
import net.javaguides.__backend.repository.TimeSlotRepository;
import net.javaguides.__backend.repository.LocationRepository;
import org.springframework.stereotype.Component;

@Component
public class OfferingMapper {

    private final LessonRepository lessonRepository;
    private final InstructorRepository instructorRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final LocationRepository locationRepository;

    public OfferingMapper(LessonRepository lessonRepository,
                          InstructorRepository instructorRepository,
                          TimeSlotRepository timeSlotRepository,
                          LocationRepository locationRepository) {
        this.lessonRepository = lessonRepository;
        this.instructorRepository = instructorRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.locationRepository = locationRepository;
    }

    public OfferingDto mapToOfferingDto(Offering offering) {
        return new OfferingDto(
                offering.getId(),
                offering.getLesson() != null ? offering.getLesson().getId() : null,
                offering.getInstructor() != null ? offering.getInstructor().getId() : null,
                offering.getTimeSlot() != null ? offering.getTimeSlot().getId() : null,
                offering.getLocation() != null ? offering.getLocation().getId() : null,
                offering.isva
        );
    }

    public Offering mapToOffering(OfferingDto offeringDto) {
        // Fetch Lesson entity from the database using the provided lesson ID
        Lesson lesson = lessonRepository.findById(offeringDto.getLessonId()).orElseThrow(
                () -> new ResourceNotFoundException("Lesson with ID " + offeringDto.getLessonId() + " not found"));

        // Fetch Instructor entity from the database using the provided instructor ID
        Instructor instructor = instructorRepository.findById(offeringDto.getInstructorId()).orElseThrow(
                () -> new ResourceNotFoundException("Instructor with ID " + offeringDto.getInstructorId() + " not found"));

        // Fetch TimeSlot entity from the database using the provided time slot ID
        TimeSlot timeSlot = timeSlotRepository.findById(offeringDto.getTimeSlotId()).orElseThrow(
                () -> new ResourceNotFoundException("TimeSlot with ID " + offeringDto.getTimeSlotId() + " not found"));

        // Fetch Location entity from the database using the provided location ID
        Location location = locationRepository.findById(offeringDto.getLocationId()).orElseThrow(
                () -> new ResourceNotFoundException("Location with ID " + offeringDto.getLocationId() + " not found"));

        // Return new Offering with loaded Lesson, Instructor, TimeSlot, and Location
        return new Offering(
                offeringDto.getId(),
                lesson,
                instructor,
                timeSlot,
                location,
                offeringDto.isAvailable()
        );
    }
}

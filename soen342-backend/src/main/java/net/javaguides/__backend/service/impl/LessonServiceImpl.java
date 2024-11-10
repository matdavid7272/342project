package net.javaguides.__backend.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.LessonDto;
import net.javaguides.__backend.entity.Lesson;
import net.javaguides.__backend.Mapper.LessonMapper;
import net.javaguides.__backend.exception.ResourceNotFoundException;
import net.javaguides.__backend.repository.LessonRepository;
import net.javaguides.__backend.service.LessonService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    @Override
    public LessonDto createLesson(LessonDto lessonDto) {
        // Map the incoming LessonDto to Lesson entity
        Lesson lesson = LessonMapper.mapToLesson(lessonDto);

        // Save the Lesson entity
        Lesson savedLesson = lessonRepository.save(lesson);

        // Return the saved Lesson as a LessonDto
        return LessonMapper.mapToLessonDto(savedLesson);
    }

    @Override
    public LessonDto getLessonById(Long lessonId) {
        // Find the Lesson by ID
        Lesson lesson = lessonRepository.findById(lessonId);
        if (lesson == null) {
            throw new ResourceNotFoundException("Lesson with id " + lessonId + " does not exist");
        }
        // Map and return the Lesson as a LessonDto
        return LessonMapper.mapToLessonDto(lesson);
    }

    @Override
    public void deleteLesson(Long lessonId) {
        // Find the Lesson by ID
        Lesson lesson = lessonRepository.findById(lessonId);
        if (lesson == null) {
            throw new ResourceNotFoundException("Lesson with id " + lessonId + " does not exist");
        }

        // Try deleting the lesson
        boolean deleted = lessonRepository.deleteLesson(lessonId); // Assuming deleteLesson returns true if deleted
        if (!deleted) {
            throw new ResourceNotFoundException("Lesson with id " + lessonId + " could not be deleted");
        }
    }

    @Override
    public LessonDto updateLesson(Long lessonId, LessonDto updatedLessonDto) {
        // Find the existing Lesson by ID
        Lesson existingLesson = lessonRepository.findById(lessonId);
        if (existingLesson == null) {
            throw new ResourceNotFoundException("Lesson with id " + lessonId + " does not exist");
        }

        // Map the updated LessonDto to a Lesson entity
        Lesson updatedLesson = LessonMapper.mapToLesson(updatedLessonDto);

        // Save the updated Lesson
        Lesson savedLesson = lessonRepository.editLesson(lessonId, updatedLesson);  // Assuming editLesson updates and returns the updated lesson

        // Return the updated Lesson as a LessonDto
        return LessonMapper.mapToLessonDto(savedLesson);
    }

    @Override
    public List<LessonDto> getAllLessons() {
        // Fetch all Lessons from the repository
        List<Lesson> lessons = lessonRepository.findAll();

        // Convert each Lesson entity to LessonDto and return as a list
        return lessons.stream()
                .map(LessonMapper::mapToLessonDto)  // Convert each Lesson to LessonDto
                .collect(Collectors.toList());
    }
}

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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;  // Inject LessonMapper

    @Override
    public LessonDto createLesson(LessonDto lessonDto) {
        // Convert LessonDto to Lesson entity using injected LessonMapper
        Lesson lesson = lessonMapper.mapToLesson(lessonDto);
        // Save the lesson entity
        Lesson savedLesson = lessonRepository.save(lesson);
        // Convert saved lesson back to DTO
        return lessonMapper.mapToLessonDto(savedLesson);
    }

    @Override
    public LessonDto getLessonById(Long lessonId) {
        // Try to find the lesson by ID
        Optional<Lesson> lessonOptional = lessonRepository.findById(lessonId);
        if (!lessonOptional.isPresent()) {
            // Handle case where lesson is not found
            throw new ResourceNotFoundException("Lesson with id " + lessonId + " does not exist");
        }
        Lesson lesson = lessonOptional.get();
        return lessonMapper.mapToLessonDto(lesson);
    }

    @Override
    public void deleteLesson(Long id) {
        // Try to find the lesson by ID
        Optional<Lesson> lessonOptional = lessonRepository.findById(id);
        if (!lessonOptional.isPresent()) {
            // Handle case where lesson is not found
            throw new ResourceNotFoundException("Lesson with id " + id + " does not exist");
        }
        // Delete the found lesson
        lessonRepository.delete(lessonOptional.get());
    }

    @Override
    public LessonDto updateLesson(Long id, LessonDto updatedLessonDto) {
        // Try to find the lesson by ID
        Optional<Lesson> existingLessonOptional = lessonRepository.findById(id);
        if (!existingLessonOptional.isPresent()) {
            // Handle case where lesson is not found
            throw new ResourceNotFoundException("Lesson with id " + id + " does not exist");
        }

        // Convert updated DTO to entity using injected LessonMapper
        Lesson updatedLesson = lessonMapper.mapToLesson(updatedLessonDto);
        updatedLesson.setId(id);  // Ensure the updated lesson has the correct ID

        // Save the updated lesson
        Lesson savedLesson = lessonRepository.save(updatedLesson);
        return lessonMapper.mapToLessonDto(savedLesson);
    }

    @Override
    public List<LessonDto> getAllLessons() {
        // Get all lessons from the repository
        List<Lesson> lessons = lessonRepository.findAll();
        // Convert lessons to DTOs and return as a list
        return lessons.stream()
                .map(lessonMapper::mapToLessonDto)
                .collect(Collectors.toList());
    }
}

package net.javaguides.__backend.service;

import net.javaguides.__backend.dto.LessonDto;
import java.util.List;

public interface LessonService {

    // Create a new Lesson
    LessonDto createLesson(LessonDto lessonDto);

    // Get a Lesson by its ID
    LessonDto getLessonById(Long lessonId);

    // Delete a Lesson by its ID
    void deleteLesson(Long lessonId);

    // Update a Lesson with new data
    LessonDto updateLesson(Long lessonId, LessonDto lessonDto);

    // Get all Lessons
    List<LessonDto> getAllLessons();
}

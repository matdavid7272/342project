package net.javaguides.__backend.controller;

import lombok.AllArgsConstructor;
import net.javaguides.__backend.dto.LessonDto;
import net.javaguides.__backend.service.LessonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private LessonService lessonService;

    // Build add Lesson REST API
    @PostMapping
    public ResponseEntity<LessonDto> createLesson(@RequestBody LessonDto lessonDto) {
        LessonDto savedLesson = lessonService.createLesson(lessonDto);
        return new ResponseEntity<>(savedLesson, HttpStatus.CREATED);
    }

    // Build get Lesson by ID REST API
    @GetMapping("{id}")
    public ResponseEntity<LessonDto> getLessonById(@PathVariable("id") Long lessonId) {
        LessonDto lessonDto = lessonService.getLessonById(lessonId);
        return ResponseEntity.ok(lessonDto);
    }

    // Get all Lessons REST API
    @GetMapping
    public ResponseEntity<List<LessonDto>> getAllLessons() {
        List<LessonDto> lessons = lessonService.getAllLessons();
        return ResponseEntity.ok(lessons);
    }

    // Delete Lesson by ID REST API
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable("id") Long lessonId) {
        lessonService.deleteLesson(lessonId);
        return ResponseEntity.noContent().build();
    }

    // Update Lesson by ID REST API
    @PutMapping("{id}")
    public ResponseEntity<LessonDto> updateLesson(@PathVariable("id") Long lessonId,
                                                  @RequestBody LessonDto updatedLesson) {
        LessonDto lessonDto = lessonService.updateLesson(lessonId, updatedLesson);
        return ResponseEntity.ok(lessonDto);
    }
}

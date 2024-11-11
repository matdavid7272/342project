package net.javaguides.__backend;

import net.javaguides.__backend.controller.LessonController;
import net.javaguides.__backend.dto.LessonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class SystemService {

    @Autowired
    private LessonController lessonController;

    public void displayAllLessons() {
        // Call the controller method directly (not recommended, though works)
        ResponseEntity<List<LessonDto>> response = lessonController.getAllLessons();
        List<LessonDto> lessons = response.getBody();
        if (lessons != null) {
            lessons.forEach(lesson -> {
                System.out.println("ID: " + lesson.getId() +
                        ", Name: " + lesson.getName() +
                        ", Duration: " + lesson.getDuration() +
                        ", Group: " + lesson.isGroup());
            });
        } else {
            System.out.println("No lessons found.");
        }
    }
}

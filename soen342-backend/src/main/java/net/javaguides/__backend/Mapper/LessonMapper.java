package net.javaguides.__backend.Mapper;

import net.javaguides.__backend.dto.LessonDto;
import net.javaguides.__backend.entity.Lesson;

public class LessonMapper {

    public static LessonDto mapToLessonDto(Lesson lesson) {
        return new LessonDto(
                lesson.getId(),
                lesson.getName(),
                lesson.getDuration(),
                lesson.isGroup()
        );
    }

    public static Lesson mapToLesson(LessonDto lessonDto) {
        return new Lesson(
                lessonDto.getId(),
                lessonDto.getName(),
                lessonDto.getDuration(),
                lessonDto.isGroup()
        );
    }
}

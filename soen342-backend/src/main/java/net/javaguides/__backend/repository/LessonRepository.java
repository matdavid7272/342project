package net.javaguides.__backend.repository;

import net.javaguides.__backend.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    // Additional custom queries, if any, can be added here.
}

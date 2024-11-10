package net.javaguides.__backend.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonDto {

    private Long id;
    private String name;
    private int duration;

    @Column(name = "is_group") // Ensure the field name matches the entity column name
    private boolean isGroup;  // Change to match entity class
}

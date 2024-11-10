package net.javaguides.__backend.dto;

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
    private boolean isGroup;
}

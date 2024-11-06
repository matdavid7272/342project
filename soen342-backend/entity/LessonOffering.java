package net.javaguides.__backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LessonOffering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Location location; // Location associated with this lesson offering.

    @Enumerated(EnumType.STRING)
    private LessonType lessonType; // Lesson type chosen from the enumeration.

    private boolean isPublic; // Whether the lesson offering is public.

    private boolean publiclyViewable; // Whether it is publicly viewable.

    private boolean available; // Whether the lesson offering is available.

    // Other fields and methods can be added as needed.
}

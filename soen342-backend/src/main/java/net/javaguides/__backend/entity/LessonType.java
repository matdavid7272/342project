package net.javaguides.__backend.entity;

public enum LessonType {

    YOGA("Yoga"),
    SWIMMING("Swimming"),
    SOCCER("Soccer"),
    BASKETBALL("Basketball"),
    TENNIS("Tennis"),
    BOXING("Boxing"),
    PILATES("Pilates"),
    RUNNING("Running"),
    DANCE("Dance"),
    CROSSFIT("CrossFit"),
    GYMNASTICS("Gymnastics"),
    MARTIAL_ARTS("Martial Arts"),
    SURFING("Surfing"),
    SKATEBOARDING("Skateboarding"),
    ROCK_CLIMBING("Rock Climbing"),
    BASEBALL("Baseball"),
    FOOTBALL("Football"),
    VOLLEYBALL("Volleyball"),
    BADMINTON("Badminton"),
    GOLF("Golf");

    private final String lessonName;

    // Constructor to assign the lesson name
    LessonType(String lessonName) {
        this.lessonName = lessonName;
    }

    // Getter to retrieve the lesson name
    public String getLessonName() {
        return lessonName;
    }

    @Override
    public String toString() {
        return lessonName;
    }
}
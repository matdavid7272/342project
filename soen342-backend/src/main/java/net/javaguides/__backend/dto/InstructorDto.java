package net.javaguides.__backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorDto {

    private Long Id;
    private String lastname;
    private String firstname;
    private String email;
    private int age;
    private String specialization;

}

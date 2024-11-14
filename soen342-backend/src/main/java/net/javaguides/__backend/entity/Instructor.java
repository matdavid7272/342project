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
public class Instructor extends User {

    @Column(name = "specialization")
    private String specialization;

    public Instructor(Long id, String lastname, String firstname, String email, int age, String specialization) {
        super(id, lastname, firstname, email, age);
        this.specialization = specialization;
    }

}

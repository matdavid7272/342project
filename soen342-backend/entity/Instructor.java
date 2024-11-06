package net.javaguides.__backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@AllArgsConstructor
@Entity
public class Instructor extends User {

    @Column(name = "specialization")
    private String specialization;

}

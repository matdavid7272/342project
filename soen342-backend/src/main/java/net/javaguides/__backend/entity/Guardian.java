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
public class Guardian extends User {

    public Guardian(Long id, String lastname, String firstname, String email, int age) {
        super(id, lastname, firstname, email, age);
    }
}

package net.javaguides.__backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@AllArgsConstructor
@Entity
public class Client extends User {

    public Client(Long id, String lastname, String firstname, String email, int age) {
        super(id, lastname, firstname, email, age);
    }
}

package net.javaguides.__backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ClientDto {

    private Long id;
    private String lastName;
    private String firstName;
    private String email;
    private int age;

    public String getFirstname() {
        return firstName;
    }

    // Setter for firstName
    public void setFirstname(String firstName) {
        this.firstName = firstName;
    }

    // Getter for lastName
    public String getLastname() {
        return lastName;
    }

    // Setter for lastName
    public void setLastname(String lastName) {
        this.lastName = lastName;
    }

    // Getter and setter for email
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getter and setter for age
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // Getter and setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

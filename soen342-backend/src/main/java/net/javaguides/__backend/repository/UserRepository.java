package net.javaguides.__backend.repository;

import net.javaguides.__backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    // Custom query method to find a user by email (assuming you have an email field)
    //Optional<User> findByEmail(String email);

    // You can also add other custom methods, such as:
    // List<User> findByRole(String role);
}

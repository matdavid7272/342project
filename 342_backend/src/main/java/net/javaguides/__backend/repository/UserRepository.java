package net.javaguides.__backend.repository;

import net.javaguides.__backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

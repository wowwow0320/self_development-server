package project.self_development.routine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.self_development.routine.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}

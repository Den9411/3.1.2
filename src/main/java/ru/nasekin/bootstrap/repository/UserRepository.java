package ru.nasekin.bootstrap.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.nasekin.bootstrap.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

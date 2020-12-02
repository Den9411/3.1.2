package ru.nasekin.bootstrap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nasekin.bootstrap.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}

package ru.citydom.testwork.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.citydom.testwork.entity.UserRole;

public interface RoleDao  extends JpaRepository<UserRole, Long> {

    UserRole findAllByName(String name);
}

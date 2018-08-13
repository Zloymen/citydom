package ru.citydom.testwork.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.citydom.testwork.entity.User;

public interface UserDao extends JpaRepository<User, Long> {

    User getByUsername(String username);

    User getById(Long id);
}

package ru.citydom.testwork.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.citydom.testwork.entity.User;

public interface UserService extends UserDetailsService {

    User create(String username, String password, String grant );

    User getByUsername(String username);

    User getCurrentUser();

    boolean currentUserIsAdmin();

}

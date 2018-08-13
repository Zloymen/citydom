package ru.citydom.testwork.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ru.citydom.testwork.annotation.ReadOnlyTransactional;
import ru.citydom.testwork.dao.RoleDao;
import ru.citydom.testwork.dao.UserDao;
import ru.citydom.testwork.entity.User;
import ru.citydom.testwork.entity.UserRole;
import ru.citydom.testwork.error.ServiceException;

import static ru.citydom.testwork.error.ErrorEnum.ROLE_NOT_FOUND;


@Slf4j
@ReadOnlyTransactional
public class UserServiceImpl implements  UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserDetails user = userDao.getByUsername(username);
        if(user == null) throw new UsernameNotFoundException("User not found");
        return user;
    }


    @Transactional
    @Override
    public User create(String username, String password, String grant ) {
        UserRole role = roleDao.findAllByName(grant);
        if(role == null) throw new ServiceException(ROLE_NOT_FOUND);
        User user = new User(username , passwordEncoder.encode(password), role);
        return userDao.save(user);
    }

    @Override
    public User getCurrentUser(){
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a == null || a instanceof AnonymousAuthenticationToken) {
            return null;
        }
        Object principal = a.getPrincipal();
        if (principal instanceof String &&  principal.equals("anonymousUser")) {
            return null;
        }


        return principal instanceof String ? userDao.getByUsername((String) principal) : userDao.getById((((User) principal).getId()));
    }

    @Override
    public User getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public boolean currentUserIsAdmin() {
        return getCurrentUser().getRoles().stream().anyMatch(item -> item.getName().equals("admin"));
    }
}
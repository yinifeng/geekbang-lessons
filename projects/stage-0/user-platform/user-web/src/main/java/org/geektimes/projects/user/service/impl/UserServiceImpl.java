package org.geektimes.projects.user.service.impl;
import java.sql.Connection;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.repository.DatabaseUserRepository;
import org.geektimes.projects.user.repository.UserRepository;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.projects.user.sql.DBConnectionManager;
import org.geektimes.projects.user.util.DataSourceUtils;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    
    public UserServiceImpl() {
        DBConnectionManager dbConnectionManager = new DBConnectionManager();
        dbConnectionManager.setConnection(DataSourceUtils.getConnection());
        this.userRepository = new DatabaseUserRepository(dbConnectionManager);
    }

    @Override
    public boolean register(User user) {
        userRepository.save(user);
        return false;
    }

    @Override
    public boolean deregister(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User queryUserById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public User queryUserByNameAndPassword(String name, String password) {
        return userRepository.getByNameAndPassword(name,password);
    }
}

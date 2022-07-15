package com.example.serverlbee.service.user;

import com.example.serverlbee.entity.Role;
import com.example.serverlbee.entity.User;

public interface UserService {
    User signup(User user);

    User save(User user);

    Role saveRole(Role role);

    User getUserByPhone(String phonenumber);

    void addRoleToUser(String phonenumber, String rolename);

    int activeUserByPhonenumber(String phonenumber);

    int deleteUserByPhonenumber(String phonenumber);
}
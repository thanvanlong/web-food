package com.example.serverlbee.service.user;

import com.example.serverlbee.entity.*;
import com.example.serverlbee.repo.*;
import com.example.serverlbee.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;
//    @Autowired
//    ProductRepo productRepo;
//    @Autowired
//    CategoryRepo categoryRepo;
//    @Autowired
//    DishRepo dishRepo;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = userRepo.findByPhone(phoneNumber);
        if(user == null){
            throw new UsernameNotFoundException("User not found in the database");
        }
        return user;
    }
    @Override
    public User signup(User user) {
        if(userRepo.findByPhone(user.getPhone()) != null){
            return null;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return user;
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return user;
    }

    @Override
    public Role saveRole(Role role) {
        roleRepo.save(role);
        return role;
    }

    @Override
    public User getUserByPhone(String phonenumber) {
        return userRepo.findByPhone(phonenumber);
    }

    @Override
    public void addRoleToUser(String phonenumber, String rolename) {
        User user = userRepo.findByPhone(phonenumber);
        Role role = roleRepo.findByName(rolename);
        Collection<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        userRepo.save(user);
    }

    @Override
    public int activeUserByPhonenumber(String phonenumber) {
        User user = userRepo.findByPhone(phonenumber);
        if(user == null){
            return 0;
        }
        user.setActived(true);
        userRepo.save(user);
        return 1;
    }

    @Override
    public int deleteUserByPhonenumber(String phonenumber) {
        User user = userRepo.findByPhone(phonenumber);
        userRepo.delete(user);
        return 1;
    }


}

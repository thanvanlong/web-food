package com.example.serverlbee.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class User implements UserDetails {
    @Id
    private String id;
    private String username;
    private String password;
    private String phone;
    private String address;
    private LocalDate dob;
    private boolean isActived;
    private boolean isBlocked;
    private Collection<Role> roles = new ArrayList<>();

    public User(String username, String password, String phone, String address, LocalDate dob, boolean isActived, boolean isBlocked, Collection<Role> roles) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.dob = dob;
        this.isActived = isActived;
        this.isBlocked = isBlocked;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> role = new ArrayList<>();
        roles.stream().forEach(role1 -> role.add(new SimpleGrantedAuthority(role1.getName())));
        return role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBlocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActived;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

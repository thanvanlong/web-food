package com.example.serverlbee.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.serverlbee.entity.User;
import com.example.serverlbee.entity.Error;
import com.example.serverlbee.repo.UserRepo;
import com.example.serverlbee.service.user.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserRepo userRepo;

    @PostMapping("/user/signup")
    public ResponseEntity<?> signup(@RequestBody User user){
        System.out.println(user.toString());

        user.setBlocked(false);
        user.setActived(false);
        User u = userService.signup(user);
        if(u == null){
            return ResponseEntity.ok(new Error(404, "Phonenumber is already token"));
        }else{
            userService.addRoleToUser(user.getPhone(), "ROLE_USER");
            return ResponseEntity.ok(u);
        }
    }

    @GetMapping("/user/{phonenumber}")
    public ResponseEntity<User> getUserByPhonenumber(@PathVariable String phonenumber){
        return ResponseEntity.ok(userService.getUserByPhone(phonenumber));
    }

    @PutMapping("/user/active/{phonenumber}")
    public ResponseEntity<?> activeUserByPhonenumber(@PathVariable String phonenumber){
        int rs = userService.activeUserByPhonenumber(phonenumber);
        System.out.println(phonenumber);
        if(rs == 1){
            return ResponseEntity.ok(new Error(200, "Update successfully"));
        }
        return ResponseEntity.ok(new Error(404, "Update failed"));
    }

    @PutMapping("/user/update")
    public ResponseEntity<User> updateUserInfo(@RequestBody User user){
        System.out.println(user);
        User u = userService.getUserByPhone(user.getPhone());
        u.setPassword(user.getPassword());
        u.setUsername(user.getUsername());
        u.setAddress(user.getAddress());

        userService.save(u);
        return ResponseEntity.ok(u);
    }

    @PutMapping("/user/forgot")
    public  ResponseEntity<?> forgotPassword(@RequestBody User user){
        System.out.println(user);
        User u = userService.getUserByPhone(user.getPhone());
        System.out.println(u);
        if(u == null){
            return ResponseEntity.status(404).body(new Error(404, "SDT chua duoc dang ki vui long kiem tra lai!!!"));
        }else{
            u.setPassword(String.valueOf((int) (Math.random()*1000000 + 1000000)));
            user.setPassword(u.getPassword());
            userService.save(u);
            return ResponseEntity.status(200).body(user);
        }
    }

    @DeleteMapping("/user/{phonenumber}")
    public ResponseEntity<?> deleteUserByPhonenumber(@PathVariable String phonenumber){
        int rs = userService.deleteUserByPhonenumber(phonenumber);
        System.out.println(phonenumber);
        if(rs == 1){
            return ResponseEntity.ok(new Error(200, "Delete successfully"));
        }
        return ResponseEntity.ok(new Error(404, "Delete failed"));
    }

    @PostMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
            try {
                stream(request.getCookies()).forEach(cookie -> System.out.println(cookie.getName()+": " + cookie.getValue()));
                System.out.println(request.getRequestURI());
                List<Cookie> token =  stream(request.getCookies()).filter(cookie -> cookie.getName().equals("refresh_token")).collect(Collectors.toList());
                String refresh_token = token.get(0).getValue();
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refresh_token);
                String phonenNumber = decodedJWT.getSubject();
                User user = userService.getUserByPhone(phonenNumber);
                String access_token = JWT.create()
                        .withSubject(user.getPhone() + "-" + user.getUsername() + "-" + user.getAddress())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 1000))
                        .withIssuer(request.getRequestURI())
                        .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            }catch (Exception e){
                response.setHeader("error", e.getMessage());
                Map<String, String> tokens = new HashMap<>();
                tokens.put("error_message", (e.getMessage() + "cookies"));
                response.setStatus(400);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }
    }


}

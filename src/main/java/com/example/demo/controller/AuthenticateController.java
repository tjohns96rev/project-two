package com.example.demo.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.LoginInfo;
import com.example.demo.service.UserService;

@RestController
public class AuthenticateController {

    private static Logger authenticateLogger = LoggerFactory.getLogger(AuthenticateController.class);
    @Autowired
    private UserService userService;

    @ExceptionHandler
    public ResponseEntity<String> loginFailed(Error e) {
        authenticateLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginInfo loginInfo, HttpSession session) {
        if (session.getAttribute("isLoggedIn") != null && (Boolean) session.getAttribute("isLoggedIn")) {
            return new ResponseEntity<>("You are already logged in. To switch accounts please logout first.",
                    HttpStatus.OK);
        }
        if (!userService.login(loginInfo)) {
            throw new Error("Sorry those credentials don't match any existing account.");
        }
        session.setAttribute("isLoggedIn", true);
        return new ResponseEntity<>("Login Successful", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.setAttribute("isLoggedIn", false);
        return new ResponseEntity<>("Logged out", HttpStatus.OK);
    }
}

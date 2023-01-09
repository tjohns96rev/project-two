package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Moon;
import com.example.demo.exceptions.EntityNotFound;
import com.example.demo.exceptions.NotAuthorizedException;
import com.example.demo.service.MoonService;

@RestController
public class MoonController {

    private static Logger moonLogger = LoggerFactory.getLogger(MoonController.class);

    @Autowired
    private MoonService moonService;

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<String> entityNotFound(EntityNotFound e) {
        moonLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> notAuthorized(NotAuthorizedException e) {
        moonLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/api/moons")
    public ResponseEntity<List<Moon>> findAll(HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null || !(Boolean) session.getAttribute("isLoggedIn")) {
            throw new NotAuthorizedException("You must login to perform this action.");
        }
        return new ResponseEntity<>(moonService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/moon/id/{id}")
    public ResponseEntity<Moon> findById(@PathVariable int id, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null || !(Boolean) session.getAttribute("isLoggedIn")) {
            throw new NotAuthorizedException("You must login to perform this action.");
        }
        return new ResponseEntity<>(this.moonService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/api/break")
    public ResponseEntity<String> breakStuff() {
        return new ResponseEntity<>("Uh oh", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/api/moon/{name}")
    public ResponseEntity<Moon> findByName(@PathVariable String name, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null || !(Boolean) session.getAttribute("isLoggedIn")) {
            throw new NotAuthorizedException("You must login to perform this action.");
        }
        return new ResponseEntity<>(this.moonService.findByName(name), HttpStatus.OK);
    }

    @PostMapping("/api/moon")
    public ResponseEntity<String> createMoon(@RequestBody Moon newMoon, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null || !(Boolean) session.getAttribute("isLoggedIn")) {
            throw new NotAuthorizedException("You must login to perform this action.");
        }
        String message = this.moonService.createMoon(newMoon);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/moon/id/{id}")
    public ResponseEntity<String> deleteMoon(@PathVariable int id, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null || !(Boolean) session.getAttribute("isLoggedIn")) {
            throw new NotAuthorizedException("You must login to perform this action.");
        }
        String message = this.moonService.deleteById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/api/planet/moons/{ownerId}")
    public ResponseEntity<List<Moon>> findByOwnerId(@PathVariable int ownerId, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null || !(Boolean) session.getAttribute("isLoggedIn")) {
            throw new NotAuthorizedException("You must login to perform this action.");
        }
        return new ResponseEntity<>(moonService.findByOwnerId(ownerId), HttpStatus.OK);
    }

}

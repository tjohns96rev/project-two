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

import com.example.demo.entities.Planet;
import com.example.demo.exceptions.EntityNotFound;
import com.example.demo.service.PlanetService;
import com.example.demo.exceptions.NotAuthorizedException;

@RestController
public class PlanetController {

    private static Logger planetLogger = LoggerFactory.getLogger(PlanetController.class);

    @Autowired
    private PlanetService planetService;

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<String> entityNotFound(EntityNotFound e) {
        planetLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> notAuthorized(NotAuthorizedException e) {
        planetLogger.error(e.getLocalizedMessage(), e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/api/planets")
    public ResponseEntity<List<Planet>> findAll(HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null || !(Boolean) session.getAttribute("isLoggedIn")) {
            throw new NotAuthorizedException("You must login to perform this action.");
        }
        return new ResponseEntity<>(planetService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/planet/id/{id}")
    public ResponseEntity<Planet> findById(@PathVariable int id, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null || !(Boolean) session.getAttribute("isLoggedIn")) {
            throw new NotAuthorizedException("You must login to perform this action.");
        }
        return new ResponseEntity<>(this.planetService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/api/planet/{name}")
    public ResponseEntity<Planet> findByName(@PathVariable String name, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null || !(Boolean) session.getAttribute("isLoggedIn")) {
            throw new NotAuthorizedException("You must login to perform this action.");
        }
        return new ResponseEntity<>(this.planetService.findByName(name), HttpStatus.OK);
    }

    @PostMapping("/api/planet")
    public ResponseEntity<String> createPlanet(@RequestBody Planet newPlanet, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null || !(Boolean) session.getAttribute("isLoggedIn")) {
            throw new NotAuthorizedException("You must login to perform this action.");
        }
        String message = this.planetService.createPlanet(newPlanet);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/planet/id/{id}")
    public ResponseEntity<String> deletePlanet(@PathVariable int id, HttpSession session) {
        if (session.getAttribute("isLoggedIn") == null || !(Boolean) session.getAttribute("isLoggedIn")) {
            throw new NotAuthorizedException("You must login to perform this action.");
        }
        String message = this.planetService.deleteById(id);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}

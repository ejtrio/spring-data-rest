/**
 * A controller like this is capable of intercepting http requests to repositories
 * allowing custom functionality
 */
//package com.ejtrio.springdatarest.adapter.controllers;
//
//import com.ejtrio.springdatarest.infrastructure.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.rest.webmvc.RepositoryRestController;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//
//@RepositoryRestController
//public class UserController {
//    @Autowired
//    UserRepository userRepository;
//
//    @GetMapping(value = "/users/{id}")
//    public ResponseEntity getUser(@PathVariable Long id) {
//        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
//    }
//}

package com.directory.controller;

import java.util.*;

import javax.validation.Valid;

import com.directory.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.directory.model.User;
import com.directory.repository.UserRepository;

/**
 * Rest controller, allow POST, PUT, GET and DELETE operations
 */
@RestController
@RequestMapping()
public class UserController {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
    UserService userService;


    @PostMapping("/user")
    public ResponseEntity addUser(@RequestBody @Valid User user) {
    	LOGGER.info("start addUser for "+user.toString());
    	try {
            return new ResponseEntity(userService.create(user), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity(e,  HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
 
    @GetMapping("/user/{id}")
    public ResponseEntity getUser(@PathVariable("id") long id) {
    	LOGGER.info("start getUser for id "+ id);
        Optional<User> user = userService.getById(id);
        return  user.isPresent() ? new ResponseEntity(user.get(), HttpStatus.OK): new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/user/{id}")
    public ResponseEntity updateUser(@PathVariable("id") long id, @RequestBody @Valid User user) {
    	LOGGER.info("start updateUser for id "+ id);
    	user.setId(id);
        try {
            return new ResponseEntity(userService.update(user), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity(e.getMessage(),  HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
         
    @DeleteMapping("user/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") long id) {
    	LOGGER.info("start deleteUser for id "+ id);
    	return new ResponseEntity((userService.delete(id)?HttpStatus.OK :HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/users")
    public ResponseEntity<List<User>> findAll(){
        LOGGER.info("start findAll");
        List<User> users = userService.getAll();
    	HttpStatus httpStatus = (users.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    	ResponseEntity responseEntity = new ResponseEntity(users, httpStatus);
    	return responseEntity;
    }
    
}

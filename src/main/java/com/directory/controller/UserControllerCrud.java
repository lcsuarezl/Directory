package com.directory.controller;

import java.util.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.directory.model.User;
import com.directory.repository.UserRepository;

@Controller
@RequestMapping()
public class UserControllerCrud {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(UserControllerCrud.class);

	
	@Autowired
	UserRepository userRepository;

     
    @PostMapping("user")
    public ResponseEntity addUser(@RequestBody @Valid User user) {
    	LOGGER.info("start addUser for "+user.toString());
    	try {
            return new ResponseEntity(userRepository.save(user), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity(e,  HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
 
    @GetMapping("/user/{id}")
    public ResponseEntity getUser(@PathVariable("id") long id) {
    	LOGGER.info("start getUser for id "+ id);
        Optional<User> user = userRepository.findById(id);
        return  user.isPresent() ? new ResponseEntity(user.get(), HttpStatus.OK): new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("/user/{id}")
    public ResponseEntity updateUser(@PathVariable("id") long id, @RequestBody @Valid User user) {
    	LOGGER.info("start updateUser for id "+ id);
    	user.setId(id);
        try {
            return new ResponseEntity(userRepository.save(user), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity(e.getMessage(),  HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
         
    @DeleteMapping("user/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") long id) {
    	LOGGER.info("start deleteUser for id "+ id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            userRepository.delete(user.get());
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    
    @GetMapping("/users")
    public ResponseEntity<List<User>> findAll(){
    	List<User> users = new ArrayList<>();
    	Iterable<User> userIterator = userRepository.findAll();
    	userIterator.forEach( user ->  users.add(user));
    	HttpStatus httpStatus = (users.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    	ResponseEntity responseEntity = new ResponseEntity(users, httpStatus);
    	return responseEntity;
    }
    
}

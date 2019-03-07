package com.directory.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.directory.model.User;
import com.directory.repository.UserRepository;

@Controller
public class UserController {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	
	@Autowired
	UserRepository userRepository;
     
    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "user-add";
    }
    
    @GetMapping("/index")
    public String showV2(User user) {
        return "index";
    }
     
    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
    	LOGGER.info("start addUser for "+user.toString());
        if (result.hasErrors()) {
            return "user-add";
        }
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }
 
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
    	LOGGER.info("start showUpdateForm for id "+ id);
        User user = userRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        LOGGER.info(user.toString());
        model.addAttribute("user", user);
        return "user-update";
    }
    
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, 
      BindingResult result, Model model) {
    	LOGGER.info("start updateUser for id "+ id);
        if (result.hasErrors()) {
            user.setId(id);
            return "user-update";
        }
        
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }
         
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
    	LOGGER.info("start deleteUser for id "+ id);
        User user = userRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }
}

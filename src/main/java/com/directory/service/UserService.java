package com.directory.service;


import com.directory.model.User;
import com.directory.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User create(User user){
        return userRepository.save(user);
    }

    public boolean delete(long id){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            userRepository.delete(user.get());
            return true;
        }
        return false;
    }

    public User update(User user){
        return userRepository.save(user);
    }

    public Optional<User> getById(long id){
        Optional<User> user = userRepository.findById(id);
        return  user;
    }

    public List<User> getAll(){
        List<User> users = new ArrayList<>();
        Iterable<User> userIterator = userRepository.findAll();
        userIterator.forEach( user ->  users.add(user));
        return users;
    }
}

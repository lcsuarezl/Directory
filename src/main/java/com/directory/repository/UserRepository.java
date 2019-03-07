package com.directory.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.directory.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}

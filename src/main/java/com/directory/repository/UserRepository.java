package com.directory.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.directory.model.User;

/**
 * Data reposotory, uses H2 Database
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}

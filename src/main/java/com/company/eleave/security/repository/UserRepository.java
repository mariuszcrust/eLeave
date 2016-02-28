package com.company.eleave.security.repository;

import org.springframework.data.repository.CrudRepository;

import com.company.eleave.security.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {

}

package com.crud.bo.interviewtest.models.dao;

import com.crud.bo.interviewtest.models.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface IUserDao extends CrudRepository<User, Long> {
    public User findByUsername(String username);
}

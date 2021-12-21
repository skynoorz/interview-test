package com.crud.bo.interviewtest.models.services;

import com.crud.bo.interviewtest.models.dao.IUserDao;
import com.crud.bo.interviewtest.models.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private IUserDao userDao;


    public List<User> findAll() {
        return (List<User>) this.userDao.findAll();
    }

    public User findById(Long id) {
        return this.userDao.findById(id).orElse(null);
    }

    public User save(User user) {
        return this.userDao.save(user);
    }

    public void delete(Long id) {
        this.userDao.deleteById(id);
    }
}

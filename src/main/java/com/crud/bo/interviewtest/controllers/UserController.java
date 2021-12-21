package com.crud.bo.interviewtest.controllers;

import com.crud.bo.interviewtest.models.entity.Role;
import com.crud.bo.interviewtest.models.entity.User;
import com.crud.bo.interviewtest.models.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> index(){
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        User user = null;
        Map<String, Object> response = new HashMap<>();
        try {
            user = userService.findById(id);
        } catch (DataAccessException e) {
            response.put("message", "Error querying to DB, or no connection.");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (user == null) {
            response.put("message", "The user with id: ".concat(id.toString().concat(" doesn't exist in DB")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody User user) {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(2L,"SUPERVISOR"));
        User userNew;
        Map<String, Object> response = new HashMap<>();
        try {
            user.setRoles(roles);
            userNew = userService.save(user);
        } catch (DataAccessException e) {
            response.put("message", "Error creating the new user");
            response.put("error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "User created successfully");
        response.put("user", userNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            userService.delete(id);
        } catch (DataAccessException e) {
            response.put("message", "Error deleting user with id: " + id);
            response.put("error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("message", "User with id: "+id + " was successfully deleted");
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try{
            User usr = userService.findById(id);
            if (usr !=null) {
                usr.setBalance(user.getBalance());
                usr.setBirthday(user.getBirthday());
                usr.setUsername(user.getUsername());
//                usr.setPassword(usr.getPassword());
                usr.setJoining(user.getJoining());
                userService.save(usr);
                response.put("mensaje", "User actualizado con exito");
                response.put("user", usr);
            }else {
                response.put("mensaje", "Error al encontrar el user con id: " + id);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }catch (DataAccessException e) {
            response.put("mensaje", "Error al actualizar el user con id: " + id);
            response.put("error", e.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
}

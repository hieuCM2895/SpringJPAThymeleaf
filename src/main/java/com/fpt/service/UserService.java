package com.fpt.service;


import com.fpt.dto.UserDTO;
import com.fpt.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User save(UserDTO userDTO);

    UserDetails loadUserById(int userId);

    UserDTO findUserByLastName(String lastName);
}

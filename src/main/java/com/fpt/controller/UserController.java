package com.fpt.controller;

import com.dto.UserDTO;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class UserController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserDTO userDTO() {
        return new UserDTO();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "Home";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/Home";
        } else {
            userService.save(userDTO);
            return "redirect:/registration?success";
        }
    }

    @GetMapping("/hieu/fpt")
    public String check() {
        return "Home";
    }

    @GetMapping("/hieu")
    public String check1() {
        return "Index";
    }
}

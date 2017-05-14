package com.portfl.controller;

import com.portfl.model.User;
import com.portfl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
@SessionAttributes("principal")
@RequestMapping("/")
public class AdviceController {
    @Autowired
    private UserService userService;

    @ModelAttribute("principal")
    public String getPrincipal(){
        User user = userService.getUser();
        if(user != null) {
            return user.getUsername();
        } else {
            return "";
        }
    }

    @ModelAttribute("current_user_id")
    public Long getCurrentUser(){
        User user = userService.getUser();
        if(user != null) {
            return user.getId();
        } else {
            return -1L;
        }
    }

    @ModelAttribute("current_user_name")
    public String getCurrentName(){
        User user = userService.getUser();
        if(user != null) {
            return user.getFirstName() + " " + user.getLastName();
        } else {
            return "";
        }
    }
}
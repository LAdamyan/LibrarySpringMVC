package com.epam.libraryspringmvc.controller;

import com.epam.libraryspringmvc.manager.UserManager;
import com.epam.libraryspringmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/allUsers")
public class AllUsersController {

    @Autowired
    private UserManager userManager;

    @GetMapping
    public String showAllUsers(Model model){
        List<User> users = userManager.getAll();
        model.addAttribute("users",users);

        return "allUsers";
    }
}

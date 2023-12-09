package com.epam.libraryspringmvc.controller;

import com.epam.libraryspringmvc.manager.UserManager;
import com.epam.libraryspringmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editUsers")
public class EditUserController {

    @Autowired
    private UserManager userManager;

    @GetMapping
    public String showEditUserPage(@RequestParam("userId") int userId, Model model) {
        User user =(User) userManager.getById(userId);
        if (user != null) {
            model.addAttribute("userToEdit", user);
            return "editUsers";
        }
        return "redirect:/allUsers";
    }

    @PostMapping
    public String editUser(@RequestParam("userId") int userId,
                           @RequestParam("name") String name,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("email") String email) {
        User user =(User) userManager.getById(userId);
        if (user != null) {
            user.setName(name);
            user.setLastName(lastName);
            user.setEmail(email);
            userManager.update(user);
        }
        return "redirect:/allUsers";
    }
}


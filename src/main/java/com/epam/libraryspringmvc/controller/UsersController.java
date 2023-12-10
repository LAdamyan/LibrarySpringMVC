package com.epam.libraryspringmvc.controller;

import com.epam.libraryspringmvc.manager.impl.BookManagerImpl;
import com.epam.libraryspringmvc.manager.impl.UserManagerImpl;
import com.epam.libraryspringmvc.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/allUsers")
public class UsersController {

    protected final UserManagerImpl userManager;
    protected final BookManagerImpl bookManager;

    public UsersController(UserManagerImpl userManager, BookManagerImpl bookManager) {
        this.userManager = userManager;
        this.bookManager = bookManager;
    }


    @GetMapping
    public String showAllUsers(Model model){
        model.addAttribute("users",userManager.getAll());
        return "allUsers";
    }
    @PostMapping
    public String  deleteUser(@RequestParam("userId") int userId){
        userManager.delete(userId);
        return "redirect:/allUsers";

    }
    @GetMapping("/edit")
    public String showEditUserPage(@RequestParam("userId") int userId, Model model) {
        User user = userManager.getById(userId);
        if (user != null) {
            model.addAttribute("userToEdit", user);
            return "editUsers";
        }
        return "redirect:/allUsers";
    }
    @PostMapping("/edit")
    public String editUser(@RequestParam("userId") int userId,
                           @RequestParam("name") String name,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("email") String email) {
        User user = userManager.getById(userId);
        if (user != null) {
            user.setName(name);
            user.setLastName(lastName);
            user.setEmail(email);
            userManager.update(user);
        }
        return "redirect:/allUsers";
    }
}

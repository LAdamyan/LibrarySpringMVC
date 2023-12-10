package com.epam.libraryspringmvc.controller;

import com.epam.libraryspringmvc.manager.impl.UserManagerImpl;
import com.epam.libraryspringmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/register")
public class RegisterController {

    protected final UserManagerImpl userManager;
    @Autowired
    public RegisterController(UserManagerImpl userManager) {
        this.userManager = userManager;
    }


    @GetMapping
    public String showPageRegister(){
        return "register";
    }
    @PostMapping("/register")
    public String register(@RequestParam("name") String name,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("confirmPassword") String confirmPassword,
                           Model model) {

        if (!password.equals(confirmPassword)) {
            model.addAttribute("passwordMatchError", "Password did not match with confirm password");
            return "redirect:/register";
        }

        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setConfirmPassword(confirmPassword);

        userManager.save(user);

        return "redirect:/";
    }
}

package com.epam.libraryspringmvc.controller;

import com.epam.libraryspringmvc.manager.impl.UserManagerImpl;
import com.epam.libraryspringmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserManagerImpl userManager;

    @PostMapping
    public String register(
            @RequestParam("name") String name,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword,
            RedirectAttributes redirectAttributes) {

        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("passwordMatchError", "Password did not match with confirm password");
            return "redirect:/registration";
        }

        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setConfirmPassword(confirmPassword);

        userManager.save(user);

        return "redirect:/login";
    }
}

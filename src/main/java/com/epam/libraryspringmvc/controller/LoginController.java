package com.epam.libraryspringmvc.controller;

import com.epam.libraryspringmvc.manager.impl.UserManagerImpl;
import com.epam.libraryspringmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.epam.libraryspringmvc.model.UserRole.ADMIN;
import static com.epam.libraryspringmvc.model.UserRole.USER;


@Controller
@RequestMapping("/login")
public class LoginController {

    protected final UserManagerImpl userManager;
    @Autowired
    LoginController(UserManagerImpl userManager) {
        this.userManager = userManager;
    }

    @GetMapping
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model) {
        User user = userManager.getByEmailAndPassword(email, password);
        if (user != null) {
            model.addAttribute("user", user);
            if (user.getUserRole() == ADMIN) {
                return "redirect:/admin";
            } else if (user.getUserRole() == USER) {
                return "redirect:/dashboard";
            }
        } else {
            model.addAttribute("loginError", "The email and password you entered are not correct.");
            return "redirect:/";
        }
       return  "login";
    }
}

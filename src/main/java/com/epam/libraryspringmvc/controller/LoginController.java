package com.epam.libraryspringmvc.controller;

import com.epam.libraryspringmvc.manager.impl.UserManagerImpl;
import com.epam.libraryspringmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.epam.libraryspringmvc.model.UserRole.ADMIN;
import static com.epam.libraryspringmvc.model.UserRole.USER;


@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserManagerImpl userManager;

    @PostMapping
    public String login(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        User user = userManager.getByEmailAndPassword(email, password);
        if (user != null) {
            request.getSession().setAttribute("user", user);
            if (user.getUserRole() == ADMIN) {
                return "redirect:/admin";
            } else if (user.getUserRole() == USER) {
                return "redirect:/dashboard";
            }
        } else {
            redirectAttributes.addFlashAttribute("loginError", "The email and password you entered are not correct.");
        }

        return "redirect:/";
    }
}


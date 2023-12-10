package com.epam.libraryspringmvc.controller;

import com.epam.libraryspringmvc.manager.impl.BookManagerImpl;
import com.epam.libraryspringmvc.manager.impl.UserManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserManagerImpl userManager;

    private final BookManagerImpl bookManager;

    @Autowired
    public AdminController(UserManagerImpl userManager, BookManagerImpl bookManager) {
        this.userManager = userManager;
        this.bookManager = bookManager;
    }

    @GetMapping("/admin")
    public String showAdminPage(Model model) {
        model.addAttribute("users", userManager.getAll());
        model.addAttribute("unassignedBooks", bookManager.getAllUnassignedBooks());
        return "admin";
    }


}

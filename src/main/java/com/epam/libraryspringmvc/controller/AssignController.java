package com.epam.libraryspringmvc.controller;

import com.epam.libraryspringmvc.manager.BookManager;
import com.epam.libraryspringmvc.manager.UserManager;

import com.epam.libraryspringmvc.manager.impl.BookManagerImpl;
import com.epam.libraryspringmvc.manager.impl.UserManagerImpl;
import com.epam.libraryspringmvc.model.Book;
import com.epam.libraryspringmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/assign")
public class AssignController {


    private UserManagerImpl userManager;

    private BookManagerImpl bookManager;
    @Autowired
    public AssignController(UserManagerImpl userManager, BookManagerImpl bookManager) {
        this.userManager = userManager;
        this.bookManager = bookManager;
    }

    @PostMapping
    public String assignBook(@RequestParam("selectedUser") int selectedUser,
                             @RequestParam("selectedBook") int selectedBook,
                             Model model) {
        User user = userManager.getById(selectedUser);
        if (user != null) {
            Book book = bookManager.getById(selectedBook);
            if (book != null && book.getUserId() == 0) {
                book.setUserId(selectedUser);
                bookManager.update(book);
                model.addAttribute("successAssign", "Successfully assigned!");
            }
        }
        return "redirect:/admin";
    }

}
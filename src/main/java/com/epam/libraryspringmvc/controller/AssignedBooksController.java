package com.epam.libraryspringmvc.controller;

import com.epam.libraryspringmvc.manager.BookManager;
import com.epam.libraryspringmvc.manager.impl.BookManagerImpl;
import com.epam.libraryspringmvc.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/assignedBooks")
public class AssignedBooksController {


    private BookManagerImpl bookManager;
    @Autowired
    public AssignedBooksController(BookManagerImpl bookManager) {
        this.bookManager = bookManager;
    }

    @GetMapping
    public String showAllAssignedBooks(Model model){
       model.addAttribute("assignedBooks",bookManager.getAllAssignedBooks());
       return "assignedBooks";
    }
}

package com.epam.libraryspringmvc.controller;

import com.epam.libraryspringmvc.manager.BookManager;
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

    @Autowired
    private BookManager bookManager;

    @GetMapping
    public String showAllAssignedBooks(Model model){
        List<Book> assignedBooks = bookManager.getAllUnassignedBooks();
        model.addAttribute("assignedBooks",assignedBooks);

        return "assignedBooks";
    }
}

package com.epam.libraryspringmvc.controller;

import com.epam.libraryspringmvc.manager.BookManager;
import com.epam.libraryspringmvc.manager.UserManager;
import com.epam.libraryspringmvc.model.Book;
import com.epam.libraryspringmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {


   @Autowired
   private UserManager userManager;

   @Autowired
    private BookManager bookManager;

   @GetMapping
   public String showAdminPage(Model model){

       List<User>users = userManager.getAll();
       model.addAttribute("users",users);

       List<Book> unassignedBooks = bookManager.getAllUnassignedBooks();
       model.addAttribute("unassignedBooks", unassignedBooks);

       return "admin";

   }


}

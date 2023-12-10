package com.epam.libraryspringmvc.controller;

import com.epam.libraryspringmvc.manager.impl.BookManagerImpl;
import com.epam.libraryspringmvc.manager.impl.UserManagerImpl;
import com.epam.libraryspringmvc.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/allBooks")
public class BooksController {
    protected final UserManagerImpl userManager;
    protected final BookManagerImpl bookManager;
    @Autowired
    public BooksController(UserManagerImpl userManager, BookManagerImpl bookManager) {
        this.userManager = userManager;
        this.bookManager = bookManager;
    }

    @GetMapping
    public String showAllBooks(Model model){
        model.addAttribute("books",bookManager.getAll());
        return "allBooks";
    }
    @GetMapping
    public String showAllBookPage(@RequestParam("bookId") int bookId, Model model) {
        Book book = bookManager.getById(bookId);
        if (book != null) {
            model.addAttribute("bookToEdit", book);
            return "editBooks";
        }
        return "redirect:/allBooks";
    }
    @PostMapping
    public String  deleteBook(@RequestParam("bookId") int bookId){
        bookManager.delete(bookId);
        return "redirect:/allBooks";

    }
    @PostMapping("/editBooks")
    public String editBook(@RequestParam("bookId") int bookId,
                           @RequestParam("bookName") String bookName,
                           @RequestParam("authorName") String authorName) {
        Book book =  bookManager.getById(bookId);
        if (book != null) {
            book.setBookName(bookName);
            book.setAuthorName(authorName);
            bookManager.update(book);
        }
        return "redirect:/allBooks";
    }

}

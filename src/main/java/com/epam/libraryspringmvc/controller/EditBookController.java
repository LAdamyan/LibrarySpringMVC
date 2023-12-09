package com.epam.libraryspringmvc.controller;


import com.epam.libraryspringmvc.manager.BookManager;
import com.epam.libraryspringmvc.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/editBooks")
public class EditBookController {

    @Autowired
    private BookManager bookManager;

    @GetMapping
    public String showEditBookPage(@RequestParam("bookId") int bookId, Model model) {
        Book book = (Book) bookManager.getById(bookId);
        if (book != null) {
            model.addAttribute("bookToEdit", book);
            return "editBooks"; // Assuming the view name is "editBooks.jsp"
        }
        return "redirect:/allBooks"; // Redirect to allBooks page if book is not found
    }

    @PostMapping
    public String editBook(@RequestParam("bookId") int bookId,
                           @RequestParam("bookName") String bookName,
                           @RequestParam("authorName") String authorName) {
        Book book = (Book) bookManager.getById(bookId);
        if (book != null) {
            book.setBookName(bookName);
            book.setAuthorName(authorName);
            bookManager.update(book);
        }
        return "redirect:/allBooks";
    }
}

package com.epam.libraryspringmvc.manager;

import com.epam.libraryspringmvc.model.Book;

import java.util.List;

public interface BookManager <I,E> extends Manager<I,E> {

    List<E> getAllUnassignedBooks();
    List<E> getAllAssignedBooks();
}

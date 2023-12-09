package com.epam.libraryspringmvc.manager;

import com.epam.libraryspringmvc.model.User;

import java.util.List;

public interface Manager<I, E> {

    E getById(I id);

    List<E> getAll ();

    void save(E entity);

    void update(E entity);

    void delete(I id);
}

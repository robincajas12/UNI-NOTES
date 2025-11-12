package com.progAvanzada.servicios.inter;

import com.progAvanzada.db.Book;

import java.util.List;
import java.util.Optional;

public interface ServicioBooks {
    Optional<Book> findById(String isbn);
    List<Book> findAll();
    void updateBook(Book book);
    void insert(Book book);
    void delete(String isbn);
}

package com.pavanzada.impl;

import com.pavanzada.db.Book;
import com.pavanzada.inter.ServicioBooks;
import io.helidon.dbclient.DbClient;
import io.helidon.dbclient.DbRow;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ServiceBooks implements ServicioBooks {
    @Inject
    DbClient dbClient;

    @Override
    public Optional<Book> findById(String isbn) {
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        Stream<DbRow> rows = dbClient.execute().createQuery("select * from book").execute();
        return List.of();
    }

    @Override
    public void updateBook(Book book) {

    }

    @Override
    public void insert(Book book) {

    }

    @Override
    public void delete(String isbn) {

    }
}

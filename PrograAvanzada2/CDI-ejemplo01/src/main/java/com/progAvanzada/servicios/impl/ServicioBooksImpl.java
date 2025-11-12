package com.progAvanzada.servicios.impl;

import com.progAvanzada.db.Book;
import com.progAvanzada.servicios.inter.ServicioBooks;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
@ApplicationScoped
public class ServicioBooksImpl implements ServicioBooks
{
    @Inject
    DataSource dataSource;
    @Override
    public Optional<Book> findById(String isbn) {

        try(Connection connection= dataSource.getConnection()) {
            var st = connection.prepareStatement("SELECT * FROM book WHERE isbn = ?");
            st.setString(1,isbn);
            ResultSet resultSet =  st.executeQuery();
            if (resultSet.next())
            {
                return Optional.of(Book
                        .builder()
                                .isbn(resultSet.getString("isbn"))
                                .title(resultSet.getString("title"))
                                .price(resultSet.getBigDecimal("price"))
                                .version(resultSet.getInt("version"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> list = new LinkedList<>();
        try(Connection connection= dataSource.getConnection()) {
            var st = connection.prepareStatement("SELECT * FROM book");
            ResultSet resultSet =  st.executeQuery();
            while (resultSet.next())
            {
                list.add(Book
                        .builder()
                        .isbn(resultSet.getString("isbn"))
                        .title(resultSet.getString("title"))
                        .price(resultSet.getBigDecimal("price"))
                        .version(resultSet.getInt("version"))
                        .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    @Override
    public void updateBook(Book book) {
    }

    @Override
    public void insert(Book book) {
        try(Connection connection= dataSource.getConnection()) {
            var st = connection.prepareStatement("INSERT INTO book(isbn, title, price, version) VALUE(?,?,?,?)");
            st.setString(1, book.getIsbn());
            st.setString(2, book.getTitle());
            st.setBigDecimal(3, book.getPrice());
            st.setInt(4, book.getVersion());
            ResultSet resultSet = st.executeQuery();
        }catch (Exception e){

        }
    }

    @Override
    public void delete(String isbn) {

    }


}

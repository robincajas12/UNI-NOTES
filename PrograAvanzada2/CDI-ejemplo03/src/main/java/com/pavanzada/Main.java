package com.pavanzada;

import com.pavanzada.db.Book;
import io.helidon.config.Config;
import io.helidon.dbclient.DbClient;
import io.helidon.dbclient.DbRow;

import java.math.BigDecimal;
import java.util.stream.Stream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("Hola Mundo");
        Config config = Config.create();
        Config dbConfig = config.get("db");
        DbClient dbClient = DbClient
                .builder()
                .config(dbConfig)
                .build();
        System.out.println(dbConfig.get("connection.url").asString().get());
        Stream<DbRow> rows = dbClient.execute().createQuery("select * from book").execute();
        //dbClient.execute().createInsert("insert into book (isbn, title, price, version) values (?,?,?,?)")
           //     .params("10101010101010" ,"los res chanchitos3",BigDecimal.valueOf(5), 2).execute();
        dbClient.execute().createNamedQuery("select-all-books").execute().map(row -> {
            Book book = Book.builder()
                    .isbn(row.column("isbn").getString())
                    .price(row.column("price").as(BigDecimal.class).get())
                    .title(row.column("title").getString())
                    .version(row.column("version").getInt())
                    .build();
            return book;
        }).forEach(System.out::println);
        rows.map(row -> {
           Book book = Book.builder()
                   .isbn(row.column("isbn").getString())
                   .price(row.column("price").as(BigDecimal.class).get())
                   .title(row.column("title").getString())
                   .version(row.column("version").getInt())
                   .build();
           return book;
        }).forEach(System.out::println);



    }
}
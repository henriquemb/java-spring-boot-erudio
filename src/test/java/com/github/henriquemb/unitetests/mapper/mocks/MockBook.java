package com.github.henriquemb.unitetests.mapper.mocks;

import com.github.henriquemb.data.dto.BookDTO;
import com.github.henriquemb.model.Book;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockBook {
    private static final Date FIXED_DATE = new Date();

    public List<Book> mockEntityList() {
        List<Book> books = new ArrayList<>();
        for (int i = 1; i <= 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }
    
    public Book mockEntity(Integer number) {
        Book book = new Book();
        book.setId(number.longValue());
        book.setTitle("Title" + number);
        book.setAuthor("Author" + number);
        book.setLaunchDate(FIXED_DATE);
        book.setPrice(BigDecimal.valueOf(number * 14523.48));
        return book;
    }

    public BookDTO mockDTO(Integer number) {
        BookDTO book = new BookDTO();
        book.setId(number.longValue());
        book.setTitle("Title" + number);
        book.setAuthor("Author" + number);
        book.setLaunchDate(FIXED_DATE);
        book.setPrice(BigDecimal.valueOf(number * 14523.48));
        return book;
    }
}
package com.example.service;

import com.example.dto.bookDto.BookDto;

import java.util.List;

public interface BookService {

    BookDto createBook(BookDto bookDto);

    BookDto findBookById(Long bookId);

    List<BookDto> getAllBooks();

    void deleteBookById(Long bookId);
}

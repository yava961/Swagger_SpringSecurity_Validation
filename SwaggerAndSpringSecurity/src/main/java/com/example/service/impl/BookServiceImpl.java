package com.example.service.impl;

import com.example.dto.bookDto.BookDto;
import com.example.entity.Book;
import com.example.exeption.NotFoundException;
import com.example.repository.BookRepo;
import com.example.service.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepo bookRepo;
    private final ModelMapper modelMapper;

    @Override
    public BookDto createBook(BookDto bookDto) {
        Book creatingBook = modelMapper.map(bookDto, Book.class);
        return modelMapper.map(bookRepo.save(creatingBook), BookDto.class);
    }

    @Override
    public BookDto findBookById(Long bookId) {
        return modelMapper.map(bookRepo.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with this id not found")), BookDto.class);
    }

    @Override
    public List<BookDto> getAllBooks() {
        return StreamSupport.stream(bookRepo.findAll().spliterator(), false)
                .map(book -> modelMapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBookById(Long bookId) {
        if (bookRepo.existsById(bookId)) {
            bookRepo.deleteById(bookId);
        } else {
            throw new RuntimeException("Book with this id not found");
        }
    }

}

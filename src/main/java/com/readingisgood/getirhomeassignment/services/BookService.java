package com.readingisgood.getirhomeassignment.services;

import com.readingisgood.getirhomeassignment.enities.Book;
import com.readingisgood.getirhomeassignment.exception.CustomException;
import com.readingisgood.getirhomeassignment.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;

    @Transactional
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public Book updateQuantityForBook(Long bookId, Integer value) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            Book book1 = book.get();
            book1.setStock(value);

            return bookRepository.save(book1);
        }
        throw new CustomException(String.format("Book id %s does not exist", bookId));
    }

    public Book findBookById(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

        return book.orElseThrow(NoSuchElementException::new);
    }
}

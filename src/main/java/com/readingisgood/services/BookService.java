package com.readingisgood.services;

import com.readingisgood.daos.enities.Book;
import com.readingisgood.exception.ServiceException;
import com.readingisgood.repositories.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

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
        throw new ServiceException(String.format("Book id %s does not exist", bookId));
    }

    public Book findBookById(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

        return book.orElseThrow(NoSuchElementException::new);
    }
}

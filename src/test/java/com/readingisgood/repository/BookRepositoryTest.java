package com.readingisgood.repository;

import com.readingisgood.daos.enities.Book;
import com.readingisgood.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void saveBookTest() {
        Book book = Book.builder()
                .name("Javascript")
                .publisher("O'rielly")
                .stock(10)
                .build();

        bookRepository.save(book);

        assertThat(book.getId()).isGreaterThan(0);
    }

    @Test
    public void updateBookTest() {
        Book book = bookRepository.findById(1L).get();
        book.setStock(13);
        Book updatedBook = bookRepository.save(book);

        assertThat(updatedBook.getStock()).isEqualTo(13);
    }
}

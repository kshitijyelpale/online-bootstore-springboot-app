package com.readingisgood.repositories;

import com.readingisgood.getirhomeassignment.enities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}

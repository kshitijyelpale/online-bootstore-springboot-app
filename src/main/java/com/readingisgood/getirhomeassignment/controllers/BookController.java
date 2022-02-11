package com.readingisgood.getirhomeassignment.controllers;

import com.readingisgood.getirhomeassignment.enities.Book;
import com.readingisgood.getirhomeassignment.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<?> saveBook(@RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);

        EntityModel<Book> resource = EntityModel.of(savedBook);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class)
                .findBookById(savedBook.getId())).withSelfRel());

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/quantity/{value}")
    public ResponseEntity<?> updateQuantity(@PathVariable("id") Long bookId, @PathVariable("value") Integer value) {
        Book book = bookService.updateQuantityForBook(bookId, value);
        EntityModel<Book> resource = EntityModel.of(book);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class)
                .findBookById(book.getId())).withSelfRel());

        return ResponseEntity.ok(resource);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findBookById(@PathVariable("id") Long bookId) {
        Book book = bookService.findBookById(bookId);
        EntityModel<Book> resource = EntityModel.of(book);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BookController.class)
                .findBookById(book.getId())).withSelfRel());

        return ResponseEntity.ok(resource);
    }
}

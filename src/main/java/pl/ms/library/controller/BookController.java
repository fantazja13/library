package pl.ms.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ms.library.book.BookDto;
import pl.ms.library.book.BookEntity;
import pl.ms.library.book.IBookService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    private IBookService bookService;

    @GetMapping(path = "/{id:\\d+}")
    public ResponseEntity<?> getBook(@PathVariable final Long id) {
        Optional<BookEntity> optBook = bookService.findById(id);
        if (optBook.isPresent()) {
            return new ResponseEntity<>(optBook.get(), HttpStatus.OK);
        } else {
            Map<String, Object> responseBody = new LinkedHashMap<>();
            String message = "Book with id \"" + id + "\" not found";
            responseBody.put("timestamp", LocalDateTime.now().toString());
            responseBody.put("message", message);
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllBooks() {
        List<BookEntity> books = bookService.findAll();
        if (books.isEmpty()) {
            Map<String, Object> responseBody = new LinkedHashMap<>();
            String message = "There are no books in library";
            responseBody.put("timestamp", LocalDateTime.now().toString());
            responseBody.put("message", message);
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(books, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<?> addBook(@RequestBody @Valid final BookDto bookDto) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now().toString());
        String message;
        if (bookService.validateNewBook(bookDto)) {
            BookEntity savedBook = bookService.createNewBookFromDto(bookDto);
            message = "Book successfully created.";
            responseBody.put("message", message);
            responseBody.put("bookId", savedBook.getId().toString());
            return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
        } else {
            message = "Please provide valid data. ISBN must be unique or null, title and authors must not be empty." +
                    "If authors are unknown please enter \"unknown\".";
            responseBody.put("message", message);
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{id:\\d+}")
    public ResponseEntity<?> deleteBook(@PathVariable final Long id) {
        Map<String, String> responseBody = new LinkedHashMap<>();
        String message;
        responseBody.put("timestamp", LocalDateTime.now().toString());
        if (bookService.findById(id).isPresent()) {
            bookService.deleteById(id);
            message = "Book with id \"" + id + "\" successfully deleted";
            responseBody.put("message", message);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } else {
            message = "Book with id \"" + id + "\" not found";
            responseBody.put("message", message);
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }
    }

    @Autowired
    public BookController(IBookService bookService) {
        this.bookService = bookService;
    }
}

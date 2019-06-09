package pl.ms.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ms.library.rental.IRentalService;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class LibraryController {

    private IRentalService rentalService;

    @GetMapping(path = "borrow/user/{userId:\\d+}/book/{bookId:\\d+}")
    public ResponseEntity<?> borrowBookByUser(@PathVariable(name = "userId") final Long userId,
                                              @PathVariable(name = "bookId") final Long bookId) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now().toString());
        String message;
        if (rentalService.borrowBook(userId, bookId)) {
            message = "Book with id \"" +bookId +"\" successfully borrowed by user with id \"" + userId + "\".";
            responseBody.put("message", message);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } else {
            message = "Cannot borrow book with id \"" + bookId + "\" by user with id \"" + userId +
                    "\". Book may not be available or user has not payed last charge.";
            responseBody.put("message", message);
            return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
        }
    }

    @GetMapping(path = "return/user/{userId:\\d+}/book/{bookId:\\d+}")
    public ResponseEntity<?> returnBookByUser(@PathVariable(name = "userId") final Long userId,
                                              @PathVariable(name = "bookId") final Long bookId) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now().toString());
        String message;
        if (rentalService.returnBook(userId, bookId)) {
            message = "Book with id \"" + bookId + "\" returned.";
            responseBody.put("message", message);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } else {
            message = "Cannot return book with id \"" + bookId + "\" by user with id \""
                    + userId + "\". Please check provided ids.";
            responseBody.put("message", message);
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

    @Autowired
    public LibraryController(IRentalService rentalService) {
        this.rentalService = rentalService;
    }
}

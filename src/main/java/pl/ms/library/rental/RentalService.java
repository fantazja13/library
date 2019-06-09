package pl.ms.library.rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ms.library.book.BookEntity;
import pl.ms.library.book.IBookService;
import pl.ms.library.charge.IChargingService;
import pl.ms.library.user.IUserService;
import pl.ms.library.user.UserEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RentalService implements IRentalService {

    private IUserService userService;
    private IBookService bookService;
    private IChargingService chargingService;

    @Override
    public boolean borrowBook(Long userId, Long bookId) {
        Optional<BookEntity> optBook = bookService.findById(bookId);
        Optional<UserEntity> optUser = userService.findById(userId);
        if (!(optBook.isPresent())) return false;
        if (!(optUser.isPresent())) return false;
        BookEntity book = optBook.get();
        UserEntity user = optUser.get();
        if (!(book.isAvailable())) return false;
        if (!(user.getChargeAmount().equals(BigDecimal.ZERO))) return false;
        book.setAvailable(false);
        book.setCurrentUser(user);
        book.setBorrowingTime(LocalDateTime.now());
        user.addToBorrowedBook(book);
        userService.save(user);
        bookService.save(book);
        return true;
    }

    @Override
    public boolean returnBook(Long userId, Long bookId) {
        Optional<BookEntity> optBook = bookService.findById(bookId);
        Optional<UserEntity> optUser = userService.findById(userId);
        if (!(optBook.isPresent())) return false;
        if (!(optUser.isPresent())) return false;
        BookEntity book = optBook.get();
        UserEntity user = optUser.get();
        if (!(user.getBorrowedBooks().contains(book))) return false;
        if (!(book.getCurrentUser().equals(user))) return false;
        user.removeFromBorrowedBook(book);
        BigDecimal charge = user.getChargeAmount().add(chargingService.calculateChargeForBook(book));
        user.setChargeAmount(charge);
        book.setAvailable(true);
        book.setBorrowingTime(null);
        book.setCurrentUser(null);
        bookService.save(book);
        userService.save(user);
        return true;
    }

    @Autowired
    public RentalService(IUserService userService, IBookService bookService, IChargingService chargingService) {
        this.userService = userService;
        this.bookService = bookService;
        this.chargingService = chargingService;
    }
}

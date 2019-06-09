package pl.ms.library.rental;

import org.h2.engine.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.ms.library.book.BookEntity;
import pl.ms.library.book.IBookService;
import pl.ms.library.charge.IChargingService;
import pl.ms.library.user.IUserService;
import pl.ms.library.user.UserEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RentalServiceTest {

    @Mock
    private IUserService userService;

    @Mock
    private IBookService bookService;

    @Mock
    private IChargingService chargingService;

    private IRentalService rentalService;

    @Before
    public void setUp() {
        this.rentalService = new RentalService(userService, bookService, chargingService);
    }

    @Test
    public void borrowBookPositiveTest() {
        UserEntity user = new UserEntity();
        BookEntity book = new BookEntity();
        user.setChargeAmount(BigDecimal.ZERO);
        book.setAvailable(true);
        Long bookId = 1L;
        Long userId = 1L;
        when(bookService.findById(bookId)).thenReturn(Optional.of(book));
        when(userService.findById(userId)).thenReturn(Optional.of(user));
        assertTrue(rentalService.borrowBook(userId, bookId));
    }

    @Test
    public void borrowBook_UserNotExistsTest() {
        BookEntity book = new BookEntity();
        book.setAvailable(true);
        Long bookId = 1L;
        Long userId = 1L;
        when(bookService.findById(bookId)).thenReturn(Optional.of(book));
        when(userService.findById(userId)).thenReturn(Optional.empty());
        assertFalse(rentalService.borrowBook(userId, bookId));
    }

    @Test
    public void borrowBook_BookNotExistsTest() {
        UserEntity user = new UserEntity();
        user.setChargeAmount(BigDecimal.ZERO);
        Long bookId = 1L;
        Long userId = 1L;
        when(bookService.findById(bookId)).thenReturn(Optional.empty());
        when(userService.findById(userId)).thenReturn(Optional.of(user));
        assertFalse(rentalService.borrowBook(userId, bookId));
    }

    @Test
    public void borrowBook_UserNotPayedChargeTest() {
        UserEntity user = new UserEntity();
        BookEntity book = new BookEntity();
        user.setChargeAmount(BigDecimal.valueOf(0.1));
        book.setAvailable(true);
        Long bookId = 1L;
        Long userId = 1L;
        when(bookService.findById(bookId)).thenReturn(Optional.of(book));
        when(userService.findById(userId)).thenReturn(Optional.of(user));
        assertFalse(rentalService.borrowBook(userId, bookId));
    }

    public void borrowBook_BookIsUnavailableTest() {
        UserEntity user = new UserEntity();
        BookEntity book = new BookEntity();
        user.setChargeAmount(BigDecimal.ZERO);
        book.setAvailable(false);
        Long bookId = 1L;
        Long userId = 1L;
        when(bookService.findById(bookId)).thenReturn(Optional.of(book));
        when(userService.findById(userId)).thenReturn(Optional.of(user));
        assertFalse(rentalService.borrowBook(userId, bookId));
    }

    @Test
    public void returnBook() {
    }
}
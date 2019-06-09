package pl.ms.library.charge;

import org.junit.Assert;
import org.junit.Test;
import pl.ms.library.book.BookEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ChargingServiceTest {

    private IChargingService chargingService = new ChargingService();

    @Test
    public void calculateChargeFor1DayRental() {
        BookEntity book = new BookEntity();
        book.setBorrowingTime(LocalDateTime.now().minus(1, ChronoUnit.DAYS));
        BigDecimal charge = chargingService.calculateChargeForBook(book);
        Assert.assertEquals(BigDecimal.valueOf(2), charge);
    }

    @Test
    public void calculateChargeFor7DaysRental() {
        BookEntity book = new BookEntity();
        book.setBorrowingTime(LocalDateTime.now().minus(7, ChronoUnit.DAYS));
        BigDecimal charge = chargingService.calculateChargeForBook(book);
        Assert.assertEquals(BigDecimal.valueOf(2), charge);
    }

    @Test
    public void calculateChargeFor10DaysRental() {
        BookEntity book = new BookEntity();
        book.setBorrowingTime(LocalDateTime.now().minus(10, ChronoUnit.DAYS));
        BigDecimal charge = chargingService.calculateChargeForBook(book);
        Assert.assertEquals(BigDecimal.valueOf(3.5), charge);
    }
}
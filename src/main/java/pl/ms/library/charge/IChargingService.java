package pl.ms.library.charge;

import pl.ms.library.book.BookEntity;

import java.math.BigDecimal;

public interface IChargingService {

    int STANDING_CHARGE_DAYS = 7;
    BigDecimal STANDING_CHARGE_AMOUNT = BigDecimal.valueOf(2);
    BigDecimal PENALTY_CHARGE_AMOUNT = BigDecimal.valueOf(0.5);

    BigDecimal calculateChargeForBook(BookEntity book);
}

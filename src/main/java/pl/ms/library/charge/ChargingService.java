package pl.ms.library.charge;

import org.springframework.stereotype.Service;
import pl.ms.library.book.BookEntity;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class ChargingService implements IChargingService {

    @Override
    public BigDecimal calculateChargeForBook(BookEntity book) {
        Duration duration = Duration.between(book.getBorrowingTime(), LocalDateTime.now());
        long days = duration.toDays();
        BigDecimal amount = IChargingService.STANDING_CHARGE_AMOUNT;
        if (days <= IChargingService.STANDING_CHARGE_DAYS) {
            return amount;
        } else {
            return amount.add(BigDecimal.valueOf(days - IChargingService.STANDING_CHARGE_DAYS)
                              .multiply(IChargingService.PENALTY_CHARGE_AMOUNT));
        }
    }
}

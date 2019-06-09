package pl.ms.library.rental;

import java.math.BigDecimal;

public interface IRentalService {

    boolean borrowBook(Long userId, Long bookId);

    boolean returnBook(Long userId, Long bookId);
}

package pl.ms.library.book;

import java.util.List;
import java.util.Optional;

public interface IBookService {
    BookEntity save(BookEntity book);

    Optional<BookEntity> findById(Long id);

    List<BookEntity> findAllById(List<Long> ids);

    List<BookEntity> findAllByTitle(String title);

    List<BookEntity> findAllByAuthor(String author);

    List<BookEntity> findAll();

    Optional<BookEntity> findByISBN(String isbn);

    void deleteById(Long id);

    boolean validateNewBook(BookDto bookDto);

    BookEntity createNewBookFromDto(BookDto bookDto);
}

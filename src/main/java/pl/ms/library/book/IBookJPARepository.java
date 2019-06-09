package pl.ms.library.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IBookJPARepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findAllByTitleIgnoreCase(String title);

    List<BookEntity> findAllByAuthorsIgnoreCase(String author);

    Optional<BookEntity> findByIsbn(String isbn);


}

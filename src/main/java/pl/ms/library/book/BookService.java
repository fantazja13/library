package pl.ms.library.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService implements IBookService {

    private IBookJPARepository bookRepository;

    @Override
    public BookEntity save(BookEntity book) {
        return bookRepository.save(book);
    }

    @Override
    public Optional<BookEntity> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public List<BookEntity> findAllById(List<Long> ids) {
        return findAllById(ids);
    }

    @Override
    public List<BookEntity> findAllByTitle(String title) {
        return bookRepository.findAllByTitleIgnoreCase(title);
    }

    @Override
    public List<BookEntity> findAllByAuthor(String author) {
        return bookRepository.findAllByAuthorsIgnoreCase(author);
    }

    @Override
    public List<BookEntity> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<BookEntity> findByISBN(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public boolean validateNewBook(BookDto bookDto) {
        if (bookRepository.findByIsbn(bookDto.getISBN()).isPresent()) {
            return false;
        }
        List<BookEntity> books = findAllByTitle(bookDto.getTitle());
        for (BookEntity book : books) {
            if (book.getAuthors().equals(bookDto.getAuthors())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public BookEntity createNewBookFromDto(BookDto bookDto) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setAuthors(bookDto.getAuthors());
        bookEntity.setTitle(bookDto.getTitle());
        bookEntity.setIsbn(bookDto.getISBN());
        bookEntity.setAvailable(true);
        return bookEntity;
    }

    @Autowired
    public BookService(IBookJPARepository bookRepository) {
        this.bookRepository = bookRepository;
    }

}

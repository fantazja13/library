package pl.ms.library.book;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    private IBookJPARepository bookRepository;
    private IBookService bookService;

    @Before
    public void setUp() {
        this.bookService = new BookService(bookRepository);
    }

    @Test
    public void validateNewBookIsbnExistsTest() {
        String isbn = "978-83-8008-211-3";
        BookEntity book = new BookEntity();
        book.setIsbn(isbn);
        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.of(book));
        BookDto dto = new BookDto();
        dto.setISBN(isbn);
        assertFalse(bookService.validateNewBook(dto));
    }

    @Test
    public void validateNewBookIsbnNotExistsTest() {
        String isbn = "978-83-8008-211-3";
        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.empty());
        BookDto dto = new BookDto();
        dto.setISBN(isbn);
        assertTrue(bookService.validateNewBook(dto));
    }

    @Test
    public void validateNewBookTitleAndAuthorsExistsTest() {
        BookEntity book = new BookEntity();
        String title = "Tytuł1";
        List<String> authors = Arrays.asList("Autor1", "Autor2");
        book.setTitle(title);
        book.setAuthors(authors);
        when(bookRepository.findAllByTitleIgnoreCase(title)).thenReturn(Arrays.asList(book));
        BookDto dto = new BookDto();
        dto.setTitle(title);
        dto.setAuthors(authors);
        assertFalse(bookService.validateNewBook(dto));
    }

    @Test
    public void validateNewBookTitleAndAuthorsNotExistsTest() {
        BookEntity book = new BookEntity();
        String title = "Tytuł1";
        List<String> authors = Arrays.asList("Autor1", "Autor2");
        book.setTitle(title);
        book.setAuthors(authors);
        when(bookRepository.findAllByTitleIgnoreCase(title)).thenReturn(Arrays.asList(book));
        BookDto dto = new BookDto();
        dto.setTitle(title);
        dto.setAuthors(Arrays.asList("Autor1", "Autor4"));
        assertTrue(bookService.validateNewBook(dto));
    }

    @Test
    public void createNewBookFromDto() {
        BookDto dto = new BookDto();
        dto.setTitle("ABCD");
        dto.setAuthors(Arrays.asList("Autor1", "Autor2"));
        dto.setISBN("978-83-8008-211-3");
        BookEntity book = bookService.createNewBookFromDto(dto);
        assertEquals("ABCD", book.getTitle());
        assertEquals(Arrays.asList("Autor1", "Autor2"), book.getAuthors());
        assertEquals("978-83-8008-211-3", book.getIsbn());
    }
}
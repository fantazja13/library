package pl.ms.library.book;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@DataJpaTest
public class IBookJPARepositoryTest {

    @Autowired
    private IBookJPARepository bookRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Before
    public void loadData() {
        BookEntity b1 = new BookEntity();
        b1.setTitle("Tytuł numer 1");
        b1.setIsbn("978-83-8008-211-3");
        b1.setAuthors(Arrays.asList("Autor pierwszy", "Autor Drugi", "trzeci autor"));
        BookEntity b2 = new BookEntity();
        b2.setTitle("Tytuł NUMER 2");
        b2.setIsbn("978-83-775-8255-8");
        b2.setAuthors(Arrays.asList("autor Pierwszy", "autor czwarty"));
        BookEntity b3 = new BookEntity();
        b3.setTitle("Tytuł numer 2");
        b3.setIsbn("978-83-749-5905-6");
        b3.setAuthors(Arrays.asList("autor Pierwszy", "autor DRUGI", "autor setny"));
        entityManager.persist(b1);
        entityManager.persist(b2);
        entityManager.persist(b3);
    }

    @Test
    public void testFindAllByTitleIgnoreCase() {
        List<BookEntity> books = bookRepository.findAllByTitleIgnoreCase("TYTUŁ NUMER 2");
        Assert.assertEquals(2, books.size());
    }

    @Test
    public void testFindAllByAuthorIgnoreCase() {
        List<BookEntity> books = bookRepository.findAllByAuthorsIgnoreCase("Autor drugi");
        List<String> titles = books.stream().map(BookEntity::getTitle).collect(Collectors.toList());
        Assert.assertEquals(2, books.size());
        Assert.assertTrue(titles.contains("Tytuł numer 1"));
        Assert.assertTrue(titles.contains("Tytuł numer 2"));
    }

    @Test
    public void testFindByISBN() {
        Optional<BookEntity> optBook = bookRepository.findByIsbn("978-83-775-8255-8");
        Assert.assertTrue(optBook.isPresent());
        Assert.assertEquals(optBook.get().getTitle(),"Tytuł NUMER 2");
    }
}
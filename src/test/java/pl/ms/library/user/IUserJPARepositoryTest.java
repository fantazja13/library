package pl.ms.library.user;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class IUserJPARepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    IUserJPARepository userRepository;

    @Before
    public void loadData() {
        UserEntity user = new UserEntity("username@email.com");
        entityManager.persist(user);
    }

    @Test
    public void testFindByUsername() {
        Optional<UserEntity> optUser = userRepository.findByUsername("username@email.com");
        Assert.assertTrue(optUser.isPresent());
        Assert.assertEquals("username@email.com", optUser.get().getUsername());
    }
}
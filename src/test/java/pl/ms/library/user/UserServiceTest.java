package pl.ms.library.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private IUserJPARepository userRepository;
    private IUserService userService;

    @Before
    public void setUp() {
        this.userService = new UserService(userRepository);
    }

    @Test
    public void isUsernameTakenPositive() {
        String username = "test@test.com";
        UserEntity user = new UserEntity(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        assertTrue(userService.isUsernameTaken(user.getUsername()));
    }

    @Test
    public void isUsernameTakenNegative() {
        String username = "test@test.com";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        assertFalse(userService.isUsernameTaken(username));
    }

    @Test
    public void createNewUser() {
        String username = "test@test.com";
        UserEntity user = userService.createNewUser(username);
        assertNotNull(user);
        assertEquals(username, user.getUsername());
    }
}
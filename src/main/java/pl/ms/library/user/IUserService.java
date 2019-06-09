package pl.ms.library.user;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    UserEntity save(UserEntity user);

    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findAll();

    void deleteUser(UserEntity user);

    void deleteById(Long id);

    boolean isUsernameTaken(String username);

    UserEntity createNewUser(String username);
}

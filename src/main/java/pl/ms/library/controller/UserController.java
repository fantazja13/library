package pl.ms.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ms.library.user.IUserService;
import pl.ms.library.user.UserEntity;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private IUserService userService;

    @GetMapping(path = "/{id:\\d+}")
    public ResponseEntity<?> getUser(@PathVariable final Long id) {
        Optional<UserEntity> optUser = userService.findById(id);
        if (optUser.isPresent()) {
            return new ResponseEntity<>(optUser.get(), HttpStatus.OK);
        } else {
            String message = "User with id \"" + id + "\" not found";
            Map<String, String> responseBody = new LinkedHashMap<>();
            responseBody.put("timestamp", LocalDateTime.now().toString());
            responseBody.put("message", message);
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Email final String username) {
        Map<String, String> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now().toString());
        if (userService.isUsernameTaken(username)) {
            responseBody.put("message", "This username is already taken");
            return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
        } else {
            UserEntity user = userService.createNewUser(username);
            userService.save(user);
            responseBody.put("message", "User successfully created.");
            responseBody.put("userId", user.getId().toString());
            return new ResponseEntity<>(responseBody, HttpStatus.CREATED);
        }
    }

    @DeleteMapping(path = "/{id:\\d+}")
    public ResponseEntity<?> deleteUser(@PathVariable final Long id) {
        Map<String, String> responseBody = new LinkedHashMap<>();
        String message;
        responseBody.put("timestamp", LocalDateTime.now().toString());
        if (userService.findById(id).isPresent()) {
            userService.deleteById(id);
            message = "User with id \"" + id + "\" successfully deleted";
            responseBody.put("message", message);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } else {
            message = "User with id \"" + id + "\" not found";
            responseBody.put("message", message);
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }
    }

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }
}

package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.controller.validation.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController extends BasicController<User> {

    public UserController() {
        super("User");
    }

    @PostMapping
    public User addNewUser(@Valid @RequestBody User user) {
        return super.addNew(renameOnLogin(user));
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        return super.update(renameOnLogin(user));
    }

    @GetMapping
    public List<User> getAllUsers() {
        return super.getAll();
    }

    /**
     * метод для подстановки логина пользователя в качестве имени при незаполненном поле Name
     * @param user экз. User из запроса
     * @return экз. User с заполенным полем Name
     * @throws ValidationException исключение
     */
    private User renameOnLogin(User user) throws ValidationException {
        try {
            String userName = user.getName();
            if (userName == null || userName.isBlank()) {
                user.setName(user.getLogin());
                BasicController.getLog().info("User.name received is empty. User renamed based on Login");
            }
        } catch (NullPointerException npe) {
            throw new ValidationException("Non correct User object received");
        }
        return user;
    }
}
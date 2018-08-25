package ekindergarten.controller;

import ekindergarten.domain.User;
import ekindergarten.model.UserDto;
import ekindergarten.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("login")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/signup")
    public User registerNewParent(@RequestBody @Valid UserDto userDto) {
        return userService.registerNewParent(userDto);
    }

}

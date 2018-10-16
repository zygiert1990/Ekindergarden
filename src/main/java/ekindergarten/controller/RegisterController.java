package ekindergarten.controller;

import ekindergarten.domain.User;
import ekindergarten.model.Response;
import ekindergarten.model.UserDto;
import ekindergarten.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("register")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/parent")
    public Response registerNewParent(@RequestBody @Valid UserDto userDto) {
        User user = userService.registerParent(userDto);

        return Response.of(Response.SUCCESS, user);
    }

}

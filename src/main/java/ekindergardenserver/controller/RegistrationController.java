package ekindergardenserver.controller;

import ekindergardenserver.domain.User;
import ekindergardenserver.model.UserDto;
import ekindergardenserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("register")
public class RegistrationController {

    @Autowired
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/parent")
    public User registerNewParent(@RequestBody @Valid UserDto userDto){
        return userService.registerNewParent(userDto);
    }

}

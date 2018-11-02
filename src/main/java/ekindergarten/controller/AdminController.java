package ekindergarten.controller;

import ekindergarten.domain.Child;
import ekindergarten.domain.User;
import ekindergarten.model.ChildDto;
import ekindergarten.model.UserDto;
import ekindergarten.service.ChildService;
import ekindergarten.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/rest/admin")
public class AdminController {

    private final ChildService childService;
    private final UserService userService;

    public AdminController(ChildService childService, UserService userService) {
        this.childService = childService;
        this.userService = userService;
    }

    @PostMapping(value = "/addChild")
    public Child addChild(@RequestBody @Valid ChildDto childDto) {
        return childService.addChild(childDto);
    }

    @PostMapping(value = "/registerTeacher")
    public User addTeacher(@RequestBody @Valid UserDto userDto) {
        return userService.registerTeacher(userDto);
    }

}
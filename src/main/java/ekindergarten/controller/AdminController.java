package ekindergarten.controller;

import ekindergarten.domain.Child;
import ekindergarten.domain.User;
import ekindergarten.service.ChildService;
import ekindergarten.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("admin")
public class AdminController {

    private final ChildService childService;
    private final UserService userService;

    public AdminController(ChildService childService, UserService userService) {
        this.childService = childService;
        this.userService = userService;
    }

    @PostMapping(value = "/parent/get/{id}/child/add")
    public Child addChild(@RequestBody @Valid Child child, @PathVariable("id") long id) {
        User user = userService.findUserById(id);
        return childService.addChild(child, user.getEmail());
    }

    @GetMapping(value = "/parent/get")
    public User findUserByCivilId(String civilId) {
        return userService.findUserByCivilId(civilId);
    }
}

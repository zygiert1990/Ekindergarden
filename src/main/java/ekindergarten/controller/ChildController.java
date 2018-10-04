package ekindergarten.controller;

import ekindergarten.domain.Child;
import ekindergarten.service.ChildService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("child")
public class ChildController {

    private final ChildService childService;

    public ChildController(ChildService childService) {
        this.childService = childService;
    }

    @PostMapping(value = "/add")
    public Child addChild(@RequestBody @Valid Child child) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return childService.addChild(child, authentication.getName());
    }
}

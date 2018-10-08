package ekindergarten.controller;

import ekindergarten.domain.Child;
import ekindergarten.service.ChildService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("parent")
public class ParentController {

    private final ChildService childService;

    public ParentController(ChildService childService) {
        this.childService = childService;
    }

    @GetMapping(value = "/getAll")
    public Set<Child> findAllParentChildren() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return childService.findAllParentChildren(authentication.getName());
    }

}

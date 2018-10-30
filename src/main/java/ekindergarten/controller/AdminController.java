package ekindergarten.controller;

import ekindergarten.domain.Child;
import ekindergarten.model.ChildDto;
import ekindergarten.service.ChildService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/rest/admin")
public class AdminController {

    private final ChildService childService;

    public AdminController(ChildService childService) {
        this.childService = childService;
    }

    @PostMapping(value = "/addChild")
    public Child addChild(@RequestBody @Valid ChildDto childDto) {
        return childService.addChild(childDto);
    }

}
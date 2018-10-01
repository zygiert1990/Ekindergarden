package ekindergarten.controller;

import ekindergarten.domain.Child;
import ekindergarten.service.ChildService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("child")
public class ChildController {

    private final ChildService childService;

    public ChildController(ChildService childService) {
        this.childService = childService;
    }

    @PostMapping(value = "/add/{id}")
    public Child addChild(@RequestBody @Valid Child child, @PathVariable("id") long id) {
        return childService.addChild(child, id);
    }
}

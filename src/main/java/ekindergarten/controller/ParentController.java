package ekindergarten.controller;

import ekindergarten.domain.Child;
import ekindergarten.model.BalanceDto;
import ekindergarten.model.MessageDto;
import ekindergarten.service.ChildService;
import org.apache.commons.io.IOUtils;
import org.assertj.core.util.Sets;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/rest/parent")
public class ParentController {
    private final ChildService childService;

    public ParentController(ChildService childService) throws IOException {
        this.childService = childService;
    }

    @GetMapping(value = "/getAll")
    public Set<Child> findAllParentChildren() {
        //TODO
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return childService.findAllParentChildren(authentication.getName());
        return Sets.newLinkedHashSet(new Child(1L, "Michal", "Sawluk", "11111111111", true, null, null, null));
    }

    @GetMapping("/getBalance/{childId}")
    public BalanceDto getUserBalanceStatusForSpecificChild(@PathVariable long childId) {
        //TODO
        return new BalanceDto("-220.11");
    }



}

package ekindergarten.controller;

import ekindergarten.domain.Child;
import ekindergarten.domain.TrustedPerson;
import ekindergarten.model.BalanceDto;
import ekindergarten.service.ChildService;
import ekindergarten.service.TrustedPersonService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/rest/parent")
public class ParentController {
    private final ChildService childService;
    private final TrustedPersonService trustedPersonService;

    public ParentController(ChildService childService, TrustedPersonService trustedPersonService) {
        this.childService = childService;
        this.trustedPersonService = trustedPersonService;
    }

    @GetMapping(value = "/getAll")
    public Set<Child> findAllParentChildren() {
        return childService.findAllParentChildren(getUserEmail());
    }

    @PostMapping(value = "/addTrustedPerson/{childId}")
    public TrustedPerson addTrustedPerson(
            @RequestBody @Valid TrustedPerson trustedPerson, @PathVariable long childId) {
        return trustedPersonService.addTrustedPerson(trustedPerson, childId);
    }

    @GetMapping("/getBalance/{childId}")
    public BalanceDto getUserBalanceStatusForSpecificChild(@PathVariable long childId) {
        //TODO
        return new BalanceDto("-220.11");
    }

    private String getUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}

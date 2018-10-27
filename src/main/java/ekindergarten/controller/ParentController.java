package ekindergarten.controller;

import ekindergarten.domain.Child;
import ekindergarten.model.BalanceDto;
import ekindergarten.model.Response;
import ekindergarten.service.ChildService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Set;

@RestController
@RequestMapping("/rest/parent")
public class ParentController {
    private final ChildService childService;

    public ParentController(ChildService childService) throws IOException {
        this.childService = childService;
    }

    @GetMapping(value = "/getAll")
    public Response findAllParentChildren() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<Child> children = childService.findAllParentChildren(authentication.getName());
        return Response.of(Response.SUCCESS, children);
    }

    @GetMapping("/getBalance/{childId}")
    public BalanceDto getUserBalanceStatusForSpecificChild(@PathVariable long childId) {
        //TODO
        return new BalanceDto("-220.11");
    }


}

package ekindergarten.controller;


import ekindergarten.domain.Child;
import ekindergarten.domain.Remark;
import ekindergarten.model.RemarkDto;
import ekindergarten.service.ChildService;
import ekindergarten.service.RemarkService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/teacher")
public class TeacherController {

    private final RemarkService remarkService;
    private final ChildService childService;

    public TeacherController(RemarkService remarkService, ChildService childService) {
        this.remarkService = remarkService;
        this.childService = childService;
    }

    @PostMapping(value = "/addRemark/{childId}")
    public Remark addRemark(@RequestBody RemarkDto remarkDto, @PathVariable long childId) {
        return remarkService.addRemark(remarkDto, getUserEmail(), childId);
    }

    @GetMapping(value = "/getAll")
    public List<Child> getAllChildren() {
        return childService.findAllChildren();
    }

    private String getUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}

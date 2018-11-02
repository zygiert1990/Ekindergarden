package ekindergarten.controller;


import ekindergarten.domain.Remark;
import ekindergarten.model.RemarkDto;
import ekindergarten.service.RemarkService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest/teacher")
public class TeacherController {

    private final RemarkService remarkService;

    public TeacherController(RemarkService remarkService) {
        this.remarkService = remarkService;
    }

    @PostMapping(value = "/addRemark/{childId}")
    public Remark addRemark(@RequestBody RemarkDto remarkDto, @PathVariable long childId) {
        return remarkService.addRemark(remarkDto, getUserEmail(), childId);
    }

    private String getUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}

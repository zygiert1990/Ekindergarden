package ekindergarten.controller;


import ekindergarten.domain.Child;
import ekindergarten.domain.Remark;
import ekindergarten.model.RemarkDto;
import ekindergarten.model.consultation.dto.request.CreateConsultationDto;
import ekindergarten.service.ChildService;
import ekindergarten.service.ConsultationService;
import ekindergarten.service.RemarkService;
import ekindergarten.utils.CurrentUserProvider;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/teacher")
public class TeacherController {

    private final RemarkService remarkService;
    private final ChildService childService;
    private final ConsultationService consultationService;

    public TeacherController(RemarkService remarkService, ChildService childService, ConsultationService consultationService) {
        this.remarkService = remarkService;
        this.childService = childService;
        this.consultationService = consultationService;
    }

    @PostMapping(value = "/addRemark/{childId}")
    public Remark addRemark(@RequestBody RemarkDto remarkDto, @PathVariable long childId) {
        return remarkService.addRemark(remarkDto, CurrentUserProvider.provideUserEmail(), childId);
    }

    @GetMapping(value = "/getAll")
    public List<Child> getAllChildren() {
        return childService.findAllChildren();
    }

    @GetMapping(value = "/deleteConsultation/{consId}")
    public void deleteConsById(@PathVariable long consId) {
        consultationService.deleteById(consId);
    }

    @PostMapping(value = "/addConsultation")
    public void addConsultation(@RequestBody CreateConsultationDto consultationDto) {
        consultationService.addConsultation(consultationDto);
    }



}

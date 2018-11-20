package ekindergarten.controller;

import ekindergarten.domain.Absence;
import ekindergarten.model.AbsenceRecordDto;
import ekindergarten.model.ChildProgressDto;
import ekindergarten.model.RemarkDto;
import ekindergarten.service.AbsenceService;
import ekindergarten.service.ProgressService;
import ekindergarten.service.RemarkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/childinfo")
public class ChildInfoController {

    private final RemarkService remarkService;
    private final AbsenceService absenceService;
    private final ProgressService progressService;

    public ChildInfoController(RemarkService remarkService, AbsenceService absenceService, ProgressService progressService) {
        this.remarkService = remarkService;
        this.absenceService = absenceService;
        this.progressService = progressService;
    }

    @GetMapping("/getAbsenceRecords/{childId}")
    public List<Absence> getAbsenceREcords(@PathVariable long childId) {
        return absenceService.getChildAbsences(childId);
    }

    @PostMapping(value = "/addAbsenceRecord/{childId}")
    public List<Absence> addOrUpdateAbsence(@RequestBody List<AbsenceRecordDto> absenceRecordDto, @PathVariable long childId) {
        return absenceService.addOrUpdateAbsence(absenceRecordDto, childId);
    }

    @GetMapping("/deleteAbsenceRecord/{absenceIds}/{childId}")
    public void deleteAbsence(@PathVariable Long[] absenceIds, @PathVariable long childId) {
        absenceService.deleteAbsence(absenceIds, childId);
    }

    @GetMapping("/getChildRemarks/{childId}")
    public List<RemarkDto> getChildRemarks(@PathVariable long childId) {
        return remarkService.getChildRemarks(childId);
    }

    @GetMapping(value = "/getChildRemarksWithAuthorNameAndSurname/{childId}")
    public List<RemarkDto> getRemarks(@PathVariable long childId) {
        return remarkService.getChildRemarksWithAuthorNameAndSurname(childId);
    }

    @GetMapping("/setAsRead/{remarkId}")
    public void setAsRead(@PathVariable long remarkId) {
        remarkService.setAsRead(remarkId);
    }

    @GetMapping("/getProgrressEvaluation/{childId}")
    public List<ChildProgressDto> getEvaluation(@PathVariable long childId) {
        return progressService.getEvaluationForSpecificChild(childId);
    }

}

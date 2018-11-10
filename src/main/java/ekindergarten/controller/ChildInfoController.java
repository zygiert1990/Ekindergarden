package ekindergarten.controller;

import ekindergarten.domain.Absence;
import ekindergarten.model.*;
import ekindergarten.service.AbsenceService;
import ekindergarten.service.RemarkService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rest/childinfo")
public class ChildInfoController {
    private List<Absence> absenceRecords = new ArrayList<>();
    private List<RemarkDto> childRemakrs = new ArrayList<>();
    private int lastId = 3;

    private final RemarkService remarkService;
    private final AbsenceService absenceService;

    public ChildInfoController(RemarkService remarkService, AbsenceService absenceService) {
        this.remarkService = remarkService;
        this.absenceService = absenceService;
    }

    @GetMapping("/getAbsenceRecords/{childId}")
    public List<Absence> getAbsenceREcords(@PathVariable long childId) {
        List<Absence> childAbsences = absenceService.getChildAbsences(childId);
        // after database will be filled with data this should be removed
        if (childAbsences == null) {
            if (absenceRecords.size() == 0) {
                absenceRecords.add(Absence.builder().id(1L).absenceDate(LocalDate.now().plusDays(1)).reason("Ból dupy").build());
                absenceRecords.add(Absence.builder().id(2L).absenceDate(LocalDate.now()).reason("Ból").build());
                absenceRecords.add(Absence.builder().id(3L).absenceDate(LocalDate.now().plusDays(2)).reason("Choroba").build());
            }

            return absenceRecords;
        }
        return childAbsences;
    }

    @PostMapping(value = "/addAbsenceRecord/{childId}")
    public void addOrUpdateAbsence(@RequestBody List<AbsenceRecordDto> absenceRecordDto, @PathVariable long childId) {
        absenceService.addOrUpdateAbsence(absenceRecordDto, childId);
    }

    @GetMapping("/deleteAbsenceRecord/{absenceIds}/{childId}")
    public void deleteAbsence(@PathVariable Long[] absenceIds, @PathVariable long childId) {
        absenceService.deleteAbsence(absenceIds, childId);
    }

    @GetMapping("/getChildRemarks/{childId}")
    public List<RemarkDto> getChildRemarks(@PathVariable long childId) {
        List<RemarkDto> childRemarks = remarkService.getChildRemarks(childId);
        // after database will be filled with data this should be removed
        if (childRemarks == null) {
            if (childRemakrs.size() == 0) {
                childRemakrs.add(new RemarkDto(1L, true, "Pani Zosia", "Był spoko", "some subject", false, LocalDate.now()));
                childRemakrs.add(new RemarkDto(2L, false, "Pani Zosia", "Zbił Jasia", "some subject", true, LocalDate.now()));
                childRemakrs.add(new RemarkDto(3L, false, "Pani Zosia", "Zbił Jasia", "some subject", false, LocalDate.now()));
            }
            return childRemakrs;
        }
        return childRemarks;
    }

    @GetMapping(value = "/getChildRemarksWithAuthorNameAndSurname/{childId}")
    public List<RemarkDto> getRemarks(@PathVariable long childId) {
        return remarkService.getChildRemarksWithAuthorNameAndSurname(childId);
    }

    @GetMapping("/setAsRead/{remarkId}")
    public void setAsRead(@PathVariable long remarkId) {
        remarkService.setAsRead(remarkId);
        // after database will be filled with data this should be removed
        for (RemarkDto remarkDto : childRemakrs) {
            if (remarkDto.getId() == remarkId) {
                childRemakrs.remove(remarkDto);
                childRemakrs.add(remarkDto.withRead(true));
            }
        }
    }

    @GetMapping("/getProgrressEvaluation/{childId}")
    private List<ChildProgressDto> getEvaluation(@PathVariable long childId) {
        return Arrays.asList(
                new ChildProgressDto(ProgressCategory.PHYSICAL, Arrays.asList(
                        new TaskGradeDto(ProgressTask.PHYSICAL_1, ProgressGrade.SOMETIMES),
                        new TaskGradeDto(ProgressTask.PHYSICAL_2, ProgressGrade.NO),
                        new TaskGradeDto(ProgressTask.PHYSICAL_3, ProgressGrade.YES),
                        new TaskGradeDto(ProgressTask.PHYSICAL_5, ProgressGrade.YES),
                        new TaskGradeDto(ProgressTask.PHYSICAL_4, ProgressGrade.SOMETIMES)
                )),
                new ChildProgressDto(ProgressCategory.MENTAL, Arrays.asList(
                        new TaskGradeDto(ProgressTask.MENTAL_1, ProgressGrade.SOMETIMES),
                        new TaskGradeDto(ProgressTask.MENTAL_2, ProgressGrade.YES),
                        new TaskGradeDto(ProgressTask.MENTAL_3, ProgressGrade.NO),
                        new TaskGradeDto(ProgressTask.MENTAL_5, ProgressGrade.NO),
                        new TaskGradeDto(ProgressTask.MENTAL_4, ProgressGrade.SOMETIMES)
                )),
                new ChildProgressDto(ProgressCategory.SOCIAL_AND_MORAL, Arrays.asList(
                        new TaskGradeDto(ProgressTask.MORAL_1, ProgressGrade.SOMETIMES),
                        new TaskGradeDto(ProgressTask.MORAL_2, ProgressGrade.YES),
                        new TaskGradeDto(ProgressTask.MORAL_5, ProgressGrade.YES),
                        new TaskGradeDto(ProgressTask.MORAL_3, ProgressGrade.NO),
                        new TaskGradeDto(ProgressTask.MORAL_4, ProgressGrade.NO))));
    }

}

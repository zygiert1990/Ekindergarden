package ekindergarten.controller;

import ekindergarten.model.*;
import ekindergarten.service.RemarkService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/rest/childinfo")
public class ChildInfoController {
    private List<AbsenceRecordDto> absenceRecords = new ArrayList<>();
    private List<RemarkDto> childRemakrs = new ArrayList<>();
    private int lastId = 3;

    private final RemarkService remarkService;

    public ChildInfoController(RemarkService remarkService) {
        this.remarkService = remarkService;
    }

    @GetMapping("/getAbsenceRecords/{childId}")
    public List<AbsenceRecordDto> getAbsenceREcords(@PathVariable String childId) {
        if (absenceRecords.size() == 0) {
            absenceRecords.add(new AbsenceRecordDto(1L, LocalDate.now().plusDays(1), "Ból dupy"));
            absenceRecords.add(new AbsenceRecordDto(2L, LocalDate.now(), "Ból"));
            absenceRecords.add(new AbsenceRecordDto(3L, LocalDate.now().plusDays(2), "Choroba"));
        }

        return absenceRecords;
    }

    @PostMapping("/addAbsenceRecord")
    public void saveOrUpdate(@RequestBody AbsenceRecordDto absenceRecordDto) {
        absenceRecords
                .removeIf(absenceRecord -> absenceRecord.getId() == absenceRecordDto.getId());
        absenceRecords.add(absenceRecordDto.withId(lastId++));
    }

    @GetMapping("/deleteAbsenceRecord/{absenceId}")
    public void deleteAbsence(@PathVariable long absenceId) {
        absenceRecords
                .removeIf(absenceRecord -> absenceRecord.getId() == absenceId);
    }

    @GetMapping("/getChildRemarks/{childId}")
    public List<RemarkDto> getChildRemarks(@PathVariable long childId) {
        List<RemarkDto> childRemarks = remarkService.getChildRemarks(childId);
        // after database will be filled with data this should be removed
        if (childRemarks == null) {
            if (childRemakrs.size() == 0) {
                childRemakrs.add(new RemarkDto(1L, true, "Pani Zosia", "Był spoko", false, LocalDate.now()));
                childRemakrs.add(new RemarkDto(2L, false, "Pani Zosia", "Zbił Jasia", true, LocalDate.now()));
                childRemakrs.add(new RemarkDto(3L, false, "Pani Zosia", "Zbił Jasia", false, LocalDate.now()));
            }
            return childRemakrs;
        }
        return childRemarks;
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

package ekindergarten.controller;

import ekindergarten.domain.AbsenceRecordDto;
import ekindergarten.domain.RemarkDto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rest/childinfo")
public class ChildInfoController {
    private List<AbsenceRecordDto> absenceRecords = new ArrayList<>();
    private List<RemarkDto> childRemakrs = new ArrayList<>();
    private int lastId = 3;

    @GetMapping("/getAbsenceRecords/{childId}")
    public List<AbsenceRecordDto> getAbsenceREcords(@PathVariable String childId) {
        if(absenceRecords.size() == 0) {
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
    public List<RemarkDto> getChildRemarks() {
        childRemakrs.add(new RemarkDto(1L,true, "Pani Zosia", "Był spoko", false, LocalDate.now()));
        childRemakrs.add(new RemarkDto(2L,false, "Pani Zosia", "Zbił Jasia", true, LocalDate.now()));

        return childRemakrs;
    }

    @GetMapping("/setAsRead/{remarkId}")
    public void setAsRead(@PathVariable long remarkId) {
        for (RemarkDto remarkDto : childRemakrs) {
            if(remarkDto.getId() == remarkId) {
                childRemakrs.remove(remarkDto);
                childRemakrs.add(remarkDto.withRead(true));
            }
        }
    }

}

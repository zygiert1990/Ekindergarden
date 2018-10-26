package ekindergarten.controller;

import ekindergarten.domain.AbsenceRecordDto;
import ekindergarten.domain.RemarkDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController("/rest/childinfo")
public class ChildInfoController {
    private List<AbsenceRecordDto> absenceRecords = new ArrayList<>();
    private List<RemarkDto> childRemakrs = new ArrayList<>();
    private int lastId = 3;
    private int ONE_DAY_IN_MILISECONDS = 86400000;

    @GetMapping("/getAbsenceRecords/{childId}")
    public List<AbsenceRecordDto> getAbsenceREcords(@PathVariable String childId) {
        absenceRecords.add(new AbsenceRecordDto(1L, System.currentTimeMillis() + ONE_DAY_IN_MILISECONDS, "Ból dupy"));
        absenceRecords.add(new AbsenceRecordDto(2L, System.currentTimeMillis() + (ONE_DAY_IN_MILISECONDS * 2), "Ból"));
        absenceRecords.add(new AbsenceRecordDto(3L, System.currentTimeMillis() + (ONE_DAY_IN_MILISECONDS * 3), "Choroba"));

        return absenceRecords;
    }

    @PostMapping("/addAbsenceRecord")
    public void addAbsenceRecord(@RequestBody AbsenceRecordDto absenceRecordDto) {
        absenceRecords.add(new AbsenceRecordDto(lastId++, absenceRecordDto.getAbsenceDate(), absenceRecordDto.getReason()));
    }

    @PostMapping("/modifyAbsence")
    public void modifyAbsence(@RequestBody AbsenceRecordDto absenceRecordDto) {
        absenceRecords
                .removeIf(absenceRecord -> absenceRecord.getId() == absenceRecordDto.getId());
        absenceRecords.add(absenceRecordDto);
    }

    @GetMapping("/getChildRemarks/{childId}")
    public List<RemarkDto> getChildRemarks() {
        childRemakrs.add(new RemarkDto(1L,true, "Pani Zosia", "Był spoko", false, System.currentTimeMillis()));
        childRemakrs.add(new RemarkDto(2L,false, "Pani Zosia", "Zbił Jasia", true, System.currentTimeMillis()+ ONE_DAY_IN_MILISECONDS));

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

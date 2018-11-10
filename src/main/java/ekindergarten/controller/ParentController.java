package ekindergarten.controller;

import ekindergarten.domain.Child;
import ekindergarten.domain.TrustedPerson;
import ekindergarten.model.consultation.dto.ConsultationsDto;
import ekindergarten.model.consultation.dto.request.BookConsultationDto;
import ekindergarten.model.consultation.dto.request.CreateConsultationDto;
import ekindergarten.service.ChildService;
import ekindergarten.service.ConsultationService;
import ekindergarten.service.PaymentService;
import ekindergarten.service.TrustedPersonService;
import ekindergarten.utils.CurrentUserProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/rest/parent")
public class ParentController {
    private final ChildService childService;
    private final TrustedPersonService trustedPersonService;
    private final PaymentService paymentService;
    private final ConsultationService consultationService;

    public ParentController(ChildService childService, TrustedPersonService trustedPersonService, PaymentService paymentService, ConsultationService consultationService) {
        this.childService = childService;
        this.trustedPersonService = trustedPersonService;
        this.paymentService = paymentService;
        this.consultationService = consultationService;
    }

    @GetMapping(value = "/getAll")
    public Set<Child> findAllParentChildren() {
        return childService.findAllParentChildren(CurrentUserProvider.provideUserEmail());
    }

    @GetMapping(value = "/getChild/{childId}")
    public Child getSpecificChildById(@PathVariable long childId) {
        return childService.getSpecificChildById(childId);
    }

    @PostMapping(value = "/addTrustedPerson/{childId}")
    public TrustedPerson addTrustedPerson(
            @RequestBody @Valid TrustedPerson trustedPerson, @PathVariable long childId) {
        return trustedPersonService.addTrustedPerson(trustedPerson, childId);
    }

    @GetMapping("/getBalance/{childId}")
    public BigDecimal getUserBalanceStatusForSpecificChild(@PathVariable long childId) {
        return paymentService.getPaymentByChildIdAndCurrentMonth(childId);
    }

    @GetMapping("/getTrustedPerson/{childId}")
    public Set<TrustedPerson> getTrustedPersonForSpecificChild(@PathVariable long childId) {
        return childService.getTrustedPeopleForSpecificChild(childId);
    }

    @GetMapping(value = "/deleteTrustedPerson/{childId}/{trustedPersonId}")
    public void deleteTrustedPerson(@PathVariable long childId, @PathVariable long trustedPersonId) {
        trustedPersonService.deleteTrustedPerson(childId, trustedPersonId);
    }

    @GetMapping(value = "/getChildAdditionalInfo/{childId}")
    public String getChildAdditionalInfo(@PathVariable long childId) {
        return childService.getChildAdditionalInfo(childId);
    }

    @GetMapping(value = "/setChildAdditionalInfo/{childId}/{childInfo}")
    public void setChildAdditionalInfo(@PathVariable long childId, @PathVariable String childInfo) {
        childService.setChildAdditionalInfo(childId, childInfo);
    }

    @GetMapping (value = "/getAvailableConsultations")
    public List<ConsultationsDto> getAvailableConsultations() {
        return consultationService.getAvailableConsultations();
    }

    @GetMapping (value = "/getChildConsultations/{childId}")
    public List<ConsultationsDto> getChildConsultations(@PathVariable long childId) {
        return consultationService.getChildConsultations(childId);
    }

    @PostMapping("/bookConsultation")
    public void bookConsultation(@RequestBody @Valid BookConsultationDto consultationDto) {
        consultationService.bookConsultation(consultationDto);
    }

    @PostMapping(value = "/deleteConsultation")
    public void deleteConsultation(@RequestBody @Valid BookConsultationDto consultationDto) {
        consultationService.deleteConsultation(consultationDto);
    }

}

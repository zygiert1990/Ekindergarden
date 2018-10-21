package ekindergarten.controller;

import ekindergarten.domain.Child;
import ekindergarten.model.BalanceDto;
import ekindergarten.model.MessageDto;
import ekindergarten.service.ChildService;
import org.assertj.core.util.Sets;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/rest/parent")
public class ParentController {
    List<MessageDto> privMassages = Arrays.asList(new MessageDto(
                    1L,
                    "Ponowne wezwanie do zapłaty",
                    "Dyrektor",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin nibh augue, suscipit a, scelerisque sed, lacinia in, mi. Cras vel lorem. Etiam pellentesque aliquet tellus. Phasellus pharetra nulla ac diam. Quisque semper justo at risus. Donec venenatis, turpis vel hendrerit interdum, dui ligula ultricies purus, sed posuere libero dui id orci. Nam congue, pede vitae dapibus aliquet, elit magna vulputate arcu, vel tempus metus leo non est. Etiam sit amet lectus quis est congue mollis. Phasellus congue lacus eget neque. Phasellus ornare, ante vitae consectetuer consequat, purus sapien ultricies dolor, et mollis pede metus eget nisi. Praesent sodales velit quis augue. Cras suscipit, urna at aliquam rhoncus, urna quam viverra nisi, in interdum massa nibh nec erat.",
                    new Date().getTime()),
            new MessageDto(
                    2L,
                    "Wezwanie do zapłaty",
                    "Dyrektor",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin nibh augue, suscipit a, scelerisque sed, lacinia in, mi. Cras vel lorem. Etiam pellentesque aliquet tellus. Phasellus pharetra nulla ac diam. Quisque semper justo at risus. Donec venenatis, turpis vel hendrerit interdum, dui ligula ultricies purus, sed posuere libero dui id orci. Nam congue, pede vitae dapibus aliquet, elit magna vulputate arcu, vel tempus metus leo non est. Etiam sit amet lectus quis est congue mollis. Phasellus congue lacus eget neque. Phasellus ornare, ante vitae consectetuer consequat, purus sapien ultricies dolor, et mollis pede metus eget nisi. Praesent sodales velit quis augue. Cras suscipit, urna at aliquam rhoncus, urna quam viverra nisi, in interdum massa nibh nec erat.",
                    new Date().getTime()),
            new MessageDto(
                    3L,
                    "Witamy",
                    "Dyrektor",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin nibh augue, suscipit a, scelerisque sed, lacinia in, mi. Cras vel lorem. Etiam pellentesque aliquet tellus. Phasellus pharetra nulla ac diam. Quisque semper justo at risus. Donec venenatis, turpis vel hendrerit interdum, dui ligula ultricies purus, sed posuere libero dui id orci. Nam congue, pede vitae dapibus aliquet, elit magna vulputate arcu, vel tempus metus leo non est. Etiam sit amet lectus quis est congue mollis. Phasellus congue lacus eget neque. Phasellus ornare, ante vitae consectetuer consequat, purus sapien ultricies dolor, et mollis pede metus eget nisi. Praesent sodales velit quis augue. Cras suscipit, urna at aliquam rhoncus, urna quam viverra nisi, in interdum massa nibh nec erat.",
                    new Date().getTime())
    );
    List<MessageDto> announcement = Arrays.asList(new MessageDto(
                    1L,
                    "Zbiórka na kredki",
                    "Pani Małgosia",
                    "Zbieramy pieniażki na kredki",
                    new Date().getTime()),
            new MessageDto(
                    2L,
                    "Wyjście do kina",
                    "Pani Zosia",
                    "Wyjście do kina Cinemacity",
                    new Date().getTime()),
            new MessageDto(
                    3L,
                    "Witamy",
                    "Dyrektor",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin nibh augue, suscipit a, scelerisque sed, lacinia in, mi. Cras vel lorem. Etiam pellentesque aliquet tellus. Phasellus pharetra nulla ac diam. Quisque semper justo at risus. Donec venenatis, turpis vel hendrerit interdum, dui ligula ultricies purus, sed posuere libero dui id orci. Nam congue, pede vitae dapibus aliquet, elit magna vulputate arcu, vel tempus metus leo non est. Etiam sit amet lectus quis est congue mollis. Phasellus congue lacus eget neque. Phasellus ornare, ante vitae consectetuer consequat, purus sapien ultricies dolor, et mollis pede metus eget nisi. Praesent sodales velit quis augue. Cras suscipit, urna at aliquam rhoncus, urna quam viverra nisi, in interdum massa nibh nec erat.",
                    new Date().getTime())
    );

    private final ChildService childService;

    public ParentController(ChildService childService) {
        this.childService = childService;
    }

    @GetMapping(value = "/getAll")
    public Set<Child> findAllParentChildren() {
        //TODO
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return childService.findAllParentChildren(authentication.getName());
        return Sets.newLinkedHashSet(new Child(1L, "Michal", "Sawluk", "11111111111", true, null, null, null));
    }

    @GetMapping("/getBalance/{childId}")
    public BalanceDto getUserBalanceStatusForSpecificChild(@PathVariable long childId) {
        //TODO
        return new BalanceDto("-222.11");
    }

    @GetMapping("/getMessages")
    public List<MessageDto> getPrivateMessages() {
        //TODO
        return privMassages;
    }

    @GetMapping("/getMessages/{id}")
    public MessageDto getPrivateMessagesById(@PathVariable int id) {
        //TODO
        return privMassages.get(id);
    }

    @GetMapping("/getAnnouncement")
    public List<MessageDto> getAnnouncement() {
        //TODO

        return privMassages;
    }

    @GetMapping("/getAnnouncement/{id}")
    public MessageDto getAnnouncementById(@PathVariable int id) {
        //TODO
        return privMassages.get(id);
    }

}

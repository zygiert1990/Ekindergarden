package ekindergarten.controller;

import ekindergarten.model.MessageDto;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rest/news")
public class NewsController {
    private static List<MessageDto> privMassages = new ArrayList<>();
    private static List<MessageDto> announcement = new ArrayList<>();
    static {
        privMassages.add(new MessageDto(
                1L,
                "Ponowne wezwanie do zapłaty",
                "Dyrektor",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin nibh augue, suscipit a, scelerisque sed, lacinia in, mi. Cras vel lorem. Etiam pellentesque aliquet tellus. Phasellus pharetra nulla ac diam. Quisque semper justo at risus. Donec venenatis, turpis vel hendrerit interdum, dui ligula ultricies purus, sed posuere libero dui id orci. Nam congue, pede vitae dapibus aliquet, elit magna vulputate arcu, vel tempus metus leo non est. Etiam sit amet lectus quis est congue mollis. Phasellus congue lacus eget neque. Phasellus ornare, ante vitae consectetuer consequat, purus sapien ultricies dolor, et mollis pede metus eget nisi. Praesent sodales velit quis augue. Cras suscipit, urna at aliquam rhoncus, urna quam viverra nisi, in interdum massa nibh nec erat.",
                new Date().getTime(),
                null));
        privMassages.add(new MessageDto(
                2L,
                "Wezwanie do zapłaty",
                "Dyrektor",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin nibh augue, suscipit a, scelerisque sed, lacinia in, mi. Cras vel lorem. Etiam pellentesque aliquet tellus. Phasellus pharetra nulla ac diam. Quisque semper justo at risus. Donec venenatis, turpis vel hendrerit interdum, dui ligula ultricies purus, sed posuere libero dui id orci. Nam congue, pede vitae dapibus aliquet, elit magna vulputate arcu, vel tempus metus leo non est. Etiam sit amet lectus quis est congue mollis. Phasellus congue lacus eget neque. Phasellus ornare, ante vitae consectetuer consequat, purus sapien ultricies dolor, et mollis pede metus eget nisi. Praesent sodales velit quis augue. Cras suscipit, urna at aliquam rhoncus, urna quam viverra nisi, in interdum massa nibh nec erat.",
                new Date().getTime(),
                null));
        privMassages.add(new MessageDto(
                3L,
                "Witamy",
                "Dyrektor",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin nibh augue, suscipit a, scelerisque sed, lacinia in, mi. Cras vel lorem. Etiam pellentesque aliquet tellus. Phasellus pharetra nulla ac diam. Quisque semper justo at risus. Donec venenatis, turpis vel hendrerit interdum, dui ligula ultricies purus, sed posuere libero dui id orci. Nam congue, pede vitae dapibus aliquet, elit magna vulputate arcu, vel tempus metus leo non est. Etiam sit amet lectus quis est congue mollis. Phasellus congue lacus eget neque. Phasellus ornare, ante vitae consectetuer consequat, purus sapien ultricies dolor, et mollis pede metus eget nisi. Praesent sodales velit quis augue. Cras suscipit, urna at aliquam rhoncus, urna quam viverra nisi, in interdum massa nibh nec erat.",
                new Date().getTime()
                ,null));

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
    public List<MessageDto> getAnnouncement() throws IOException {
        if(announcement.size() == 0) {
            announcement.add(
                    new MessageDto(
                            1L,
                            "Zbiórka na kredki",
                            "Pani Małgosia",
                            "Zbieramy pieniażki na kredki",
                            new Date().getTime(),
                            IOUtils.toByteArray(this.getClass().getResourceAsStream("/news_picture/news.png"))));
            announcement.add(
                    new MessageDto(
                            2L,
                            "Wyjście do kina",
                            "Pani Zosia",
                            "Wyjście do kina Cinemacity",
                            new Date().getTime(),
                            IOUtils.toByteArray(this.getClass().getResourceAsStream("/news_picture/news.png")))
            );

            announcement.add(new MessageDto(
                    3L,
                    "Witamy",
                    "Dyrektor",
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin nibh augue, suscipit a, scelerisque sed, lacinia in, mi. Cras vel lorem. Etiam pellentesque aliquet tellus. Phasellus pharetra nulla ac diam. Quisque semper justo at risus. Donec venenatis, turpis vel hendrerit interdum, dui ligula ultricies purus, sed posuere libero dui id orci. Nam congue, pede vitae dapibus aliquet, elit magna vulputate arcu, vel tempus metus leo non est. Etiam sit amet lectus quis est congue mollis. Phasellus congue lacus eget neque. Phasellus ornare, ante vitae consectetuer consequat, purus sapien ultricies dolor, et mollis pede metus eget nisi. Praesent sodales velit quis augue. Cras suscipit, urna at aliquam rhoncus, urna quam viverra nisi, in interdum massa nibh nec erat.",
                    new Date().getTime(),
                    IOUtils.toByteArray(this.getClass().getResourceAsStream("/news_picture/news.png"))));
        }
        return announcement;
    }

    @GetMapping("/getAnnouncement/{id}")
    public MessageDto getAnnouncementById(@PathVariable int id) {
        //TODO
        return privMassages.get(id);
    }

    @GetMapping("/deleteMessage/{id}")
    public MessageDto deleteMessage(@PathVariable int id) {
        //TODO
        return privMassages.remove(id);
    }
}

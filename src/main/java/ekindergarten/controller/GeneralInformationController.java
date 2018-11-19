package ekindergarten.controller;

import ekindergarten.model.MessageDto;
import ekindergarten.model.forum.request.CreateCommentRequest;
import ekindergarten.model.forum.request.CreateTopicRequest;
import ekindergarten.model.forum.response.GetCommentsResonse;
import ekindergarten.model.forum.response.TopicDto;
import ekindergarten.service.NewsService;
import ekindergarten.service.TopicService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rest/news")
public class GeneralInformationController {

    private final TopicService topicService;
    private final NewsService newsService;

    public GeneralInformationController(TopicService topicService, NewsService newsService) {
        this.topicService = topicService;
        this.newsService = newsService;
    }

    private static List<MessageDto> privMassages = new ArrayList<>();

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
                , null));

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
        return newsService.getAllAnnouncement();
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

    @GetMapping("/getTopics")
    public List<TopicDto> getTopics() {
        return topicService.getAll();
    }

    @PostMapping("/addTopic")
    public void addOrUpdate(@RequestBody CreateTopicRequest createTopicRequest) {
        topicService.addOrUpdate(createTopicRequest);
    }

    @GetMapping("/deleteTopic/{topicId}")
    public void deleteTopic(@PathVariable long topicId) {
        topicService.deleteTopic(topicId);
    }

    @GetMapping("/getComments/{topicId}")
    public GetCommentsResonse getComments(@PathVariable long topicId) {
        return topicService.getTopicWithComments(topicId);
    }

    @PostMapping("/addComment")
    public void addComment(@RequestBody CreateCommentRequest request) {
        topicService.addComment(request);
    }

    @GetMapping(value = "/getAllAnnouncementForSpecificGroup/{groupId}")
    public List<MessageDto> getAllAnnouncementForSpecificGroup(@PathVariable long groupId) {
        return newsService.getAllAnnouncementForSpecificGroup(groupId);
    }
}

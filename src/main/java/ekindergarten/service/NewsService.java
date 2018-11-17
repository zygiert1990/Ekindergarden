package ekindergarten.service;

import ekindergarten.domain.ChildGroup;
import ekindergarten.domain.News;
import ekindergarten.domain.User;
import ekindergarten.model.MessageDto;
import ekindergarten.repositories.ChildGroupRepository;
import ekindergarten.repositories.NewsRepository;
import ekindergarten.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Service
public class NewsService {

    private final ChildGroupRepository childGroupRepository;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;

    public NewsService(ChildGroupRepository childGroupRepository, UserRepository userRepository, NewsRepository newsRepository) {
        this.childGroupRepository = childGroupRepository;
        this.userRepository = userRepository;
        this.newsRepository = newsRepository;
    }

    public News addNews(News news, long userId, Long[] groupIds) {
        List<ChildGroup> groupsList = childGroupRepository.findAllById(Arrays.asList(groupIds));
        User user = userRepository.findById(userId);
        return newsRepository.save(newsToPersist(news, user, groupsList));
    }

    public List<MessageDto> getAllAnnouncement() {
        return newsRepository.findAll()
                .stream()
                .map(this::mapNewsToMessageDto)
                .sorted(comparing(MessageDto::getDate))
                .collect(Collectors.toList());
    }

    public List<MessageDto> getAllAnnouncementForSpecificGroup(long id) {
        return newsRepository.findAllByGroupId(id)
                .stream()
                .map(this::mapNewsToMessageDto)
                .sorted(comparing(MessageDto::getDate))
                .collect(Collectors.toList());
    }

    private News newsToPersist(News news, User user, List<ChildGroup> groups) {
        news.setUser(user);
        news.setGroups(new HashSet<>(groups));
        return news;
    }

    private MessageDto mapNewsToMessageDto(News news) {
        return MessageDto.builder()
                .id(news.getId())
                .title(news.getTitle())
                .author(news.getUser().getName() + " " + news.getUser().getSurname())
                .content(news.getContent())
                .date(news.getDate().atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli())
                .image(news.getImage())
                .build();
    }

}

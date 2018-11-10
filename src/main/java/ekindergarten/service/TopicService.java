package ekindergarten.service;

import ekindergarten.domain.User;
import ekindergarten.domain.forum.Topic;
import ekindergarten.model.UserDto;
import ekindergarten.model.forum.request.CreateTopicRequest;
import ekindergarten.model.forum.response.TopicDto;
import ekindergarten.repositories.TopicRepository;
import ekindergarten.utils.CurrentUserProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicService {
    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<TopicDto> getAll() {
        return topicRepository
                .findAll()
                .stream()
                .map(i ->
                        TopicDto
                        .builder()
                        .author(UserDto
                                    .builder()
                                    .name(i.getAuthor().getName())
                                    .surname(i.getAuthor().getSurname())
                                    .id(i.getAuthor().getId())
                                    .build())
                        .content(i.getContent())
                        .creationDate(i.getCreationDate())
                        .title(i.getTitle())
                        .id(i.getId())
                        .build()
                ).collect(Collectors.toList());
    }

    public void addOrUpdate(CreateTopicRequest createTopicRequest) {
        topicRepository.save(
                Topic
                .builder()
                .author(User
                        .builder()
                        .id(CurrentUserProvider.provideUserId())
                        .build())
                .content(createTopicRequest.getContent())
                .title(createTopicRequest.getTitle())
                .creationDate(LocalDate.now())
                .id(createTopicRequest.getId())
                .build());
    }

    public void deleteTopic(long topicId) {
        Long deletedRecords = topicRepository.deleteByIdAndAuthor(
                topicId,
                User.builder().id(CurrentUserProvider.provideUserId()).build());

        if (deletedRecords == 0) {
            throw new RuntimeException("Nie jesteś autorem tego posta, nie możesz go usunąć!");
        }
    }


}

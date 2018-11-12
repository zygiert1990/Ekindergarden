package ekindergarten.service;

import ekindergarten.domain.User;
import ekindergarten.domain.forum.Comment;
import ekindergarten.domain.forum.Topic;
import ekindergarten.model.UserDto;
import ekindergarten.model.forum.request.CreateCommentRequest;
import ekindergarten.model.forum.request.CreateTopicRequest;
import ekindergarten.model.forum.response.CommentDto;
import ekindergarten.model.forum.response.GetCommentsResonse;
import ekindergarten.model.forum.response.TopicDto;
import ekindergarten.repositories.TopicRepository;
import ekindergarten.utils.CurrentUserProvider;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
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
                        .recentlyAddedComments(calculateRecentlyAddedComments(i.getComment()))
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
        int deletedRecords = topicRepository.deleteByIdAndAuthor(
                topicId,
                User.builder().id(CurrentUserProvider.provideUserId()).build());

        if (deletedRecords == 0) {
            throw new RuntimeException("Nie jesteś autorem tego posta, nie możesz go usunąć!");
        }
    }

    private int calculateRecentlyAddedComments(List<Comment> comments) {
        AtomicInteger counter = new AtomicInteger();
        comments.forEach( comment -> {
            if (comment.getCreationDate().isAfter(LocalDate.now().minusDays(2))) counter.incrementAndGet();
        });
        return counter.get();
    }


    public GetCommentsResonse getTopicWithComments(long topicId) {
        Optional<Topic> topic = topicRepository.findById(topicId);
        if(topic.isPresent()) {
            return new GetCommentsResonse(
                    TopicDto
                    .builder()
                    .author(UserDto
                            .builder()
                            .name(topic.get().getAuthor().getName())
                            .surname(topic.get().getAuthor().getSurname())
                            .build())
                    .content(topic.get().getContent())
                    .title(topic.get().getTitle())
                    .creationDate(topic.get().getCreationDate())
                    .build(),
                    topic
                            .get()
                            .getComment()
                            .stream()
                            .map(comment -> CommentDto
                                    .builder()
                                    .author(UserDto
                                            .builder()
                                            .name(comment.getAuthor().getName())
                                            .surname(comment.getAuthor().getSurname())
                                            .build())
                                    .content(comment.getContent())
                                    .creationDate(comment.getCreationDate())
                                    .id(comment.getId())
                                    .build())
                            .collect(Collectors.toList())
            );

        }
        return null;
    }


    public void addComment(CreateCommentRequest request) {
        Optional<Topic> topic = topicRepository.findById(request.getTopicId());

        topic.ifPresent(t -> {

            topic
                    .get()
                    .getComment()
                    .add(Comment
                        .builder()
                        .author(
                                User
                                .builder()
                                .id(CurrentUserProvider.provideUserId())
                                        .build())
                        .content(request.getContent())
                        .creationDate(LocalDate.now())
                        .topic(t)
                        .build());
            topicRepository.flush();
        });
    }
}

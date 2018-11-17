package ekindergarten.service;

import ekindergarten.domain.ChildGroup;
import ekindergarten.domain.News;
import ekindergarten.domain.User;
import ekindergarten.repositories.ChildGroupRepository;
import ekindergarten.repositories.NewsRepository;
import ekindergarten.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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


    private News newsToPersist(News news, User user, List<ChildGroup> groups) {
        news.setUser(user);
        news.setGroups(new HashSet<>(groups));
        return news;
    }

}

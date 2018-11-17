package ekindergarten.test.service;

import ekindergarten.domain.ChildGroup;
import ekindergarten.domain.News;
import ekindergarten.domain.User;
import ekindergarten.model.MessageDto;
import ekindergarten.repositories.ChildGroupRepository;
import ekindergarten.repositories.NewsRepository;
import ekindergarten.repositories.UserRepository;
import ekindergarten.service.NewsService;
import ekindergarten.test.repositories.BaseJpaTestConfig;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ekindergarten.testingUtils.Constans.CIVIL_ID;
import static org.junit.Assert.assertEquals;

public class NewsServiceTest extends BaseJpaTestConfig {

    @Autowired
    private ChildGroupRepository childGroupRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NewsRepository newsRepository;

    private NewsService newsService;

    @Before
    public void setup() {
        newsService = new NewsService(childGroupRepository, userRepository, newsRepository);
    }

    @Test
    public void shouldAddNews() throws IOException {
        // Given
        List<ChildGroup> groups = childGroupRepository.saveAll(Arrays.asList(
                ChildGroup.builder().name("Maluchy").build(),
                ChildGroup.builder().name("Sredniaki").build(),
                ChildGroup.builder().name("Starszaki").build()
        ));
        User user = userRepository.save(User.builder().civilId(CIVIL_ID).build());
        News news = News.builder()
                .title("title")
                .content("content")
                .date(LocalDate.now())
                .image(IOUtils.toByteArray(this.getClass().getResourceAsStream("/news_picture/news.png")))
                .build();
        Long[] groupIds = groups.stream()
                .map(ChildGroup::getId)
                .collect(Collectors.toList())
                .toArray(new Long[3]);
        newsService.addNews(news, user.getId(), groupIds);
        // When
        List<MessageDto> result = newsService.getAllAnnouncement();
        // Then
        assertEquals(result.size(), 1);
    }

    @Test
    public void shouldFindSpecificNews() throws IOException {
        // Given
        List<ChildGroup> groups = childGroupRepository.saveAll(Arrays.asList(
                ChildGroup.builder().name("Maluchy").build(),
                ChildGroup.builder().name("Sredniaki").build(),
                ChildGroup.builder().name("Starszaki").build()
        ));
        User user = userRepository.save(User.builder().civilId(CIVIL_ID).build());
        News news = News.builder()
                .title("title")
                .content("content")
                .date(LocalDate.now())
                .image(IOUtils.toByteArray(this.getClass().getResourceAsStream("/news_picture/news.png")))
                .build();
        Long[] idWithNews = {groups.get(0).getId()};
        newsService.addNews(news, user.getId(), idWithNews);
        // When
        List<MessageDto> firstResult = newsService.getAllAnnouncementForSpecificGroup(groups.get(0).getId());
        List<MessageDto> secondResult = newsService.getAllAnnouncementForSpecificGroup(groups.get(1).getId());
        // Then
        assertEquals(firstResult.size(), 1);
        assertEquals(secondResult.size(), 0);
    }

}

package ekindergarten.service;

import ekindergarten.domain.Child;
import ekindergarten.domain.IncomingEvents;
import ekindergarten.repositories.IncomingEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IncomingEventService {

    private final IncomingEventRepository incomingEventRepository;

    public IncomingEventService(IncomingEventRepository incomingEventRepository) {
        this.incomingEventRepository = incomingEventRepository;
    }

    public List<IncomingEvents> getIncomingEvents(long childId) {
        return incomingEventRepository.findByChild(
                Child
                .builder()
                .id(childId)
                .build());
    }

    public void save(String comment, long childId) {
        incomingEventRepository.save(IncomingEvents
                .builder()
                .child(Child.builder().id(childId).build())
                .date(LocalDate.now())
                .comment(comment)
                .build());
    }
}

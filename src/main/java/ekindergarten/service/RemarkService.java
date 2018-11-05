package ekindergarten.service;

import ekindergarten.domain.Child;
import ekindergarten.domain.Remark;
import ekindergarten.domain.User;
import ekindergarten.model.RemarkDto;
import ekindergarten.repositories.ChildRepository;
import ekindergarten.repositories.RemarkRepository;
import ekindergarten.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Service
public class RemarkService {

    private final RemarkRepository remarkRepository;
    private final UserRepository userRepository;
    private final ChildRepository childRepository;

    public RemarkService(RemarkRepository remarkRepository, UserRepository userRepository, ChildRepository childRepository) {
        this.remarkRepository = remarkRepository;
        this.userRepository = userRepository;
        this.childRepository = childRepository;
    }

    public Remark addRemark(RemarkDto remarkDto, String email, long id) {
        User user = userRepository.findByEmail(email);
        Child child = childRepository.findById(id);
        return remarkRepository.save(
                Remark.builder()
                        .isPositive(remarkDto.isPositive())
                        .subject(remarkDto.getSubject())
                        .comment(remarkDto.getComment())
                        .date(LocalDate.now())
                        .child(child)
                        .user(user)
                        .build()
        );
    }

    public List<RemarkDto> getChildRemarks(long id) {
        return remarkRepository
                .findByChildId(id)
                .stream()
                .map(remark -> new RemarkDto(
                        remark.getId(),
                        remark.isPositive(),
                        remark.getUser().getName(),
                        remark.getComment(),
                        remark.getSubject(),
                        remark.isRead(),
                        remark.getDate()))
                .collect(Collectors.toList());
    }

    public void setAsRead(long id) {
        Remark remark = remarkRepository.findById(id);
        remark.setRead(true);
        remarkRepository.flush();
    }

}

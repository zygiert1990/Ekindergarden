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
                        .comment(remarkDto.getComment())
                        .date(LocalDate.now())
                        .child(child)
                        .user(user)
                        .build()
        );
    }

}

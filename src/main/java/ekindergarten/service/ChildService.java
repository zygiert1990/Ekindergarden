package ekindergarten.service;

import ekindergarten.domain.Child;
import ekindergarten.repositories.ChildRepository;
import org.springframework.stereotype.Service;

@Service
public class ChildService {

    private final ChildRepository childRepository;

    public ChildService(ChildRepository childRepository) {
        this.childRepository = childRepository;
    }

    public Child addChild(final Child child) throws RuntimeException {
        if (childRepository.findByPesel(child.getPesel()) != null)
            throw new RuntimeException("Child with this pesel has already added");
        return childRepository.save(child);
    }
}

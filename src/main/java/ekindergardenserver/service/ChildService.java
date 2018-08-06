package ekindergardenserver.service;

import ekindergardenserver.repositories.ChildRepository;
import org.springframework.stereotype.Service;

@Service
public class ChildService {
    private final ChildRepository childRepository;

    public ChildService(ChildRepository childRepository) {
        this.childRepository = childRepository;
    }
}

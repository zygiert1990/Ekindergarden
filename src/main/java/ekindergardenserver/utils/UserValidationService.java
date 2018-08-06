package ekindergardenserver.utils;

import ekindergardenserver.domain.User;
import ekindergardenserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserValidationService {
    @Autowired
    private final UserRepository userRepository;

    public UserValidationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isEmailUnique(String email) {
        User user = userRepository.findByEmail(email);
        return user == null;
    }

    public boolean isPhoneNumberUnique(String phoneNumber) {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        return user == null;
    }

    public boolean isCivilIdUnique(String civilId) {
        User user = userRepository.findByCivilId(civilId);
        return user == null;
    }

}

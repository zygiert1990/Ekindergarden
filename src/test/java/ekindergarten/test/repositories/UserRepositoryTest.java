package ekindergarten.test.repositories;

import ekindergarten.domain.User;
import ekindergarten.repositories.RoleRepository;
import ekindergarten.repositories.UserRepository;
import ekindergarten.testingUtils.Constans;
import ekindergarten.utils.UserAuthorities;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserRepositoryTest extends BaseJpaTestConfig {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Before
    public void setup() {
        userRepository.save(createUser());
    }

    @Test
    public void shouldFindAllUsers() {
        //when
        List<User> result = userRepository.findAll();
        //then
        assertEquals(result.size(), 1);
    }

    @Test
    public void shouldFindUserByEmail() {
        //when
        User result = userRepository.findByEmail(Constans.EMAIL);
        //then
        assertEquals(result.getName(), Constans.NAME);
    }

    @Test
    public void shouldNotFindUserByEmail() {
        //when
        User result = userRepository.findByEmail("fakeEmail");
        //then
        assertNull(result);
    }

    @Test
    public void shouldFindUserByCivilId() {
        //when
        User result = userRepository.findByCivilId(Constans.CIVIL_ID);
        //then
        assertEquals(result.getName(), Constans.NAME);
    }

    @Test
    public void shouldFindUserByPhoneNumber() {
        //when
        User result = userRepository.findByPhoneNumber(Constans.PHONE_NUMBER);
        //then
        assertEquals(result.getName(), Constans.NAME);
    }

    private User createUser() {
        return User.builder()
                .name(Constans.NAME)
                .surname(Constans.SURNAME)
                .civilId(Constans.CIVIL_ID)
                .email(Constans.EMAIL)
                .phoneNumber(Constans.PHONE_NUMBER)
                .password(Constans.PASSWORD)
                .role(roleRepository.findByRoleName(UserAuthorities.PARENT))
                .build();
    }
}